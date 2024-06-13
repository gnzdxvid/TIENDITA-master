package GUI;

import BASE.BaseDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaVisualizarProductos {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private VentanaTiendita ventanaTiendita;

    public VentanaVisualizarProductos(VentanaTiendita ventanaTiendita) {
        this.ventanaTiendita = ventanaTiendita;

        frame = new JFrame("Visualizar Productos");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLocationRelativeTo(ventanaTiendita.frame);

        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnModificar = new JButton("Modificar Producto");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnModificar);

        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.add(panel);

        inicializarTabla();

        frame.setVisible(true);
    }

    private void inicializarTabla() {
        tableModel.addColumn("ID Producto");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");

        cargarDatosProductos();
    }

    void cargarDatosProductos() {
        tableModel.setRowCount(0);

        List<Object[]> productos = BaseDatos.obtenerProductos();
        for (Object[] producto : productos) {
            tableModel.addRow(producto);
        }
    }

    private void modificarProducto() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idProductoAModificar = (int) table.getValueAt(filaSeleccionada, 0);
            VentanaModificarProducto ventanaModificar = new VentanaModificarProducto(ventanaTiendita, idProductoAModificar, this);
            ventanaModificar.actualizarTablaProductos();
        } else {
            JOptionPane.showMessageDialog(frame, "Seleccione un producto para modificar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaVisualizarProductos(null));
    }
}
