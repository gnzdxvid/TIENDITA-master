package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import BASE.BaseDatos;
import java.awt.*;
import java.util.List;

public class VentanaProductosBajoStock extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public VentanaProductosBajoStock() {
        setTitle("Productos con Bajo Stock");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de tabla y configurar JTable
        tableModel = new DefaultTableModel(new Object[]{"ID Producto", "Nombre", "Precio", "Stock"}, 0);
        table = new JTable(tableModel);
        table.setBackground(new Color(255, 255, 204)); // Color de fondo amarillo claro para la tabla
        table.setSelectionBackground(new Color(255, 204, 102)); // Color de fondo para la selección de filas
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente y tamaño de fuente para el contenido de la tabla
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón de actualización con estilo
        JButton refreshButton = new JButton("Actualizar");
        refreshButton.setBackground(Color.BLUE); // Cambio de color
        refreshButton.setForeground(Color.WHITE); // Texto en color blanco
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente y tamaño de fuente

        // Panel principal con diseño BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE); // Fondo blanco para resaltar los componentes
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        // Agregar panel al JFrame
        add(panel);

        // Mostrar productos al iniciar la ventana
        mostrarProductos();
    }

    private void mostrarProductos() {
        tableModel.setRowCount(0);

        // Obtener productos con bajo stock desde la base de datos
        List<Object[]> productosBajoStock = BaseDatos.obtenerProductosBajoStock();
        for (Object[] producto : productosBajoStock) {
            tableModel.addRow(producto);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear y mostrar la ventana de productos bajo stock
            VentanaProductosBajoStock frame = new VentanaProductosBajoStock();
            frame.setVisible(true);
        });
    }
}
