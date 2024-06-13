package GUI;

import BASE.BaseDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VentanaEliminarProductos {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private VentanaTiendita ventanaTiendita;

    public VentanaEliminarProductos(VentanaTiendita ventanaTiendita) {
        this.ventanaTiendita = ventanaTiendita;

        frame = new JFrame("Eliminar Producto");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLocationRelativeTo(ventanaTiendita.frame);

        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        // Aplicar color a la tabla
        establecerColoresTabla();

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.setBackground(new Color(220, 20, 60)); // Color de fondo
        btnEliminar.setForeground(Color.WHITE); // Color del texto
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14)); // Tipo de letra
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnEliminar);

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

    private void cargarDatosProductos() {
        tableModel.setRowCount(0);

        try (Connection connection = BaseDatos.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");

            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("id_producto"),
                        resultSet.getString("nombre"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("stock")
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al cargar datos de productos.");
        }
    }

    private void eliminarProducto() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idProductoAEliminar = (int) table.getValueAt(filaSeleccionada, 0);
            int confirmacion = JOptionPane.showConfirmDialog(frame,
                    "¿Estás seguro de que deseas eliminar el producto con ID " + idProductoAEliminar + "?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try (Connection connection = BaseDatos.getConnection()) {
                    String query = "DELETE FROM productos WHERE id_producto = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, idProductoAEliminar);

                        int filasEliminadas = preparedStatement.executeUpdate();

                        if (filasEliminadas > 0) {
                            JOptionPane.showMessageDialog(frame, "Producto eliminado con éxito.");
                            ventanaTiendita.cargarDatosProductos();
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "No se pudo encontrar el producto a eliminar.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al eliminar producto.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Seleccione un producto para eliminar.");
        }
    }

    private void establecerColoresTabla() {
        table.setBackground(new Color(224, 255, 255)); // Color de fondo de la tabla
        table.setForeground(Color.BLACK); // Color del texto
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Tipo de letra

        table.getTableHeader().setBackground(new Color(95, 158, 160)); // Color de fondo del encabezado
        table.getTableHeader().setForeground(Color.WHITE); // Color del texto del encabezado
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Tipo de letra del encabezado

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto en celdas
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaEliminarProductos(null));
    }
}

