package GUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import BASE.BaseDatos;

public class VentanaReportes {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public VentanaReportes() {
        frame = new JFrame("Reportes de Productos");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        // Diseño de la ventana de reportes
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla para mostrar productos
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Producto");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");

        table = new JTable(tableModel);

        // Configuración de colores y estilos de la tabla
        establecerColoresYEstilosTabla();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Agregar componente al panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Cargar datos en la tabla al iniciar la ventana
        cargarDatosReportes();

        // Mostrar la ventana
        frame.add(panel);
        frame.setVisible(true);
    }

    void cargarDatosReportes() {
        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Obtener datos de los productos desde la base de datos
        for (Object[] rowData : BaseDatos.obtenerProductos()) {
            tableModel.addRow(rowData);
        }
    }

    private void establecerColoresYEstilosTabla() {
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
        SwingUtilities.invokeLater(() -> new VentanaReportes());
    }
}
