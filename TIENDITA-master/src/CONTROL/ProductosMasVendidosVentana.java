package CONTROL;

import BASE.BaseDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ProductosMasVendidosVentana extends JFrame {
    private DefaultTableModel tableModel;

    public ProductosMasVendidosVentana() {
        setTitle("Productos más vendidos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Establecer el color de fondo de la ventana
        getContentPane().setBackground(new Color(245, 245, 245));

        // Lógica específica de la ventana de Productos más vendidos
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Producto");
        tableModel.addColumn("Cantidad");

        JTable table = new JTable(tableModel);

        // Configuración de colores y estilos de la tabla
        establecerColoresYEstilosTabla(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        // Agregar componente al panel
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Cargar datos de productos más vendidos
        cargarProductosMasVendidos();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarProductosMasVendidos() {
        try {
            // Consulta SQL para obtener los productos más vendidos con cantidad mayor o igual a 30
            String consulta = "SELECT nombre_producto, SUM(cantidad) as total FROM ventas GROUP BY nombre_producto HAVING total >= 30 ORDER BY total DESC LIMIT 5";
            // Obtener conexión a la base de datos
            Connection connection = BaseDatos.getConnection();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(consulta)) {

                // Limpiar tabla antes de cargar nuevos datos
                tableModel.setRowCount(0);

                // Agregar filas a la tabla con la información de los productos más vendidos
                while (resultSet.next()) {
                    String nombreProducto = resultSet.getString("nombre_producto");
                    int cantidad = resultSet.getInt("total");
                    tableModel.addRow(new Object[]{nombreProducto, cantidad});
                }
            }

            // Cerrar conexión
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener la información de productos más vendidos.");
        }
    }

    private void establecerColoresYEstilosTabla(JTable table) {
        table.setBackground(new Color(255, 255, 255)); // Color de fondo de la tabla
        table.setForeground(Color.BLACK); // Color del texto
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Tipo de letra

        table.getTableHeader().setBackground(new Color(100, 149, 237)); // Color de fondo del encabezado
        table.getTableHeader().setForeground(Color.WHITE); // Color del texto del encabezado
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Tipo de letra del encabezado

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(new Color(230, 230, 250)); // Color de fondo de las celdas
        cellRenderer.setForeground(Color.BLACK); // Color del texto en las celdas
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto en celdas

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductosMasVendidosVentana());
    }
}
