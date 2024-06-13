package GUI;

import BASE.BaseDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaModificarProducto {

    private JFrame frame;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private VentanaTiendita ventanaTiendita;
    private VentanaVisualizarProductos ventanaVisualizarProductos;
    private int idProductoAModificar;
    private JTable table;

    public VentanaModificarProducto(VentanaTiendita ventanaTiendita, int idProductoAModificar,
                                    VentanaVisualizarProductos ventanaVisualizarProductos) {
        this.ventanaTiendita = ventanaTiendita;
        this.idProductoAModificar = idProductoAModificar;
        this.ventanaVisualizarProductos = ventanaVisualizarProductos;

        frame = new JFrame("Modificar Producto");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(ventanaTiendita.frame);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Modificar Producto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNombre.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPrecio.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPrecio, gbc);

        txtPrecio = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtPrecio, gbc);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStock.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblStock, gbc);

        txtStock = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtStock, gbc);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(60, 179, 113));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setPreferredSize(new Dimension(100, 30));
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnModificar, gbc);

        frame.add(panel, BorderLayout.CENTER);

        cargarDatosProducto();
        frame.setVisible(true);
    }

    private void cargarDatosProducto() {
        try (Connection connection = BaseDatos.getConnection()) {
            String query = "SELECT * FROM productos WHERE id_producto = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idProductoAModificar);
                var resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    txtNombre.setText(resultSet.getString("nombre"));
                    txtPrecio.setText(String.valueOf(resultSet.getDouble("precio")));
                    txtStock.setText(String.valueOf(resultSet.getInt("stock")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al cargar datos del producto a modificar.");
        }
    }
    
    public void actualizarTablaProductos() {
        if (ventanaVisualizarProductos != null) {
            ventanaVisualizarProductos.cargarDatosProductos();
        }
    }

    private void modificarProducto() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            if (precio <= 0 || stock <= 0) {
                JOptionPane.showMessageDialog(frame, "Precio y Stock deben ser mayores a 0");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese valores válidos para el precio y el stock");
            return;
        }

        try (Connection connection = BaseDatos.getConnection()) {
            String query = "UPDATE productos SET nombre = ?, precio = ?, stock = ? WHERE id_producto = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, txtNombre.getText());
                preparedStatement.setDouble(2, Double.parseDouble(txtPrecio.getText()));
                preparedStatement.setInt(3, Integer.parseInt(txtStock.getText()));
                preparedStatement.setInt(4, idProductoAModificar);

                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(frame, "Producto modificado exitosamente");
                    actualizarTablaProductos();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "No se pudo modificar el producto");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al modificar el producto");
        }
    }

    private void establecerColoresTabla() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(173, 216, 230)); // Color único para todas las celdas
                c.setForeground(Color.BLACK); // Color del texto
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaModificarProducto(null, 1, null));
    }
}
