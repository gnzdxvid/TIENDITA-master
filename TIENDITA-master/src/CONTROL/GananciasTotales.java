package CONTROL;

import BASE.BaseDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GananciasTotales extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel lblGananciasTotales;

    public GananciasTotales() {
        setTitle("Ganancias Totales");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Establecer el color de fondo de la ventana
        getContentPane().setBackground(new Color(230, 230, 250));

        // Crear modelo para la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Producto");
        tableModel.addColumn("Ganancia");

        // Crear tabla
        table = new JTable(tableModel);

        // Configurar colores y estilos de la tabla
        configurarEstiloTabla(table);

        // Crear etiqueta para mostrar las ganancias totales
        lblGananciasTotales = new JLabel("Ganancias Totales: $0.00");
        lblGananciasTotales.setFont(new Font("Arial", Font.BOLD, 16));
        lblGananciasTotales.setHorizontalAlignment(SwingConstants.CENTER);
        lblGananciasTotales.setOpaque(true);
        lblGananciasTotales.setBackground(new Color(100, 149, 237));
        lblGananciasTotales.setForeground(Color.WHITE);
        lblGananciasTotales.setPreferredSize(new Dimension(600, 50));

        // Agregar componentes al frame
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(lblGananciasTotales, BorderLayout.SOUTH);

        // Cargar datos al iniciar la ventana
        cargarDatosGananciasTotales();

        // Hacer visible la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarDatosGananciasTotales() {
        double gananciasTotales = 0.0;

        // Realizar la consulta a la base de datos y obtener los resultados
        try {
            Connection connection = BaseDatos.getConnection();
            String query = "SELECT nombre_producto, ganancias FROM ventas";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Limpiar el modelo de la tabla antes de cargar nuevos datos
            tableModel.setRowCount(0);

            // Iterar a través de los resultados y agregar filas a la tabla
            while (resultSet.next()) {
                String nombreProducto = resultSet.getString("nombre_producto");
                double ganancia = resultSet.getDouble("ganancias");

                Object[] rowData = {nombreProducto, ganancia};
                tableModel.addRow(rowData);

                // Sumar la ganancia a las ganancias totales
                gananciasTotales += ganancia;
            }

            // Cerrar la conexión
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos de ganancias totales.");
        }

        // Actualizar la etiqueta con las ganancias totales
        lblGananciasTotales.setText("Ganancias Totales: $" + gananciasTotales);
    }

    private void configurarEstiloTabla(JTable table) {
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
        SwingUtilities.invokeLater(GananciasTotales::new);
    }
}
