package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VentanaAgregarProducto {

    private JFrame frame;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private VentanaTiendita ventanaTiendita;

    public VentanaAgregarProducto(VentanaTiendita ventanaTiendita) {
        this.ventanaTiendita = ventanaTiendita;

        frame = new JFrame("Agregar Producto");
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(ventanaTiendita.frame);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Agregar Nuevo Producto");
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

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(60, 179, 113));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregar.setPreferredSize(new Dimension(100, 30));
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnAgregar, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void agregarProducto() {
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            if (precio <= 0 || stock <= 0) {
                JOptionPane.showMessageDialog(frame, "Precio y Stock deben ser mayores a 0.");
                return;
            }

            Connection connection = null;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_base_de_datos", "root", "sfabri2003");
                connection.setAutoCommit(false);

                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)")) {
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setDouble(2, precio);
                    preparedStatement.setInt(3, stock);
                    preparedStatement.executeUpdate();
                }

                connection.commit();
                JOptionPane.showMessageDialog(frame, "Producto agregado con éxito.");
                ventanaTiendita.cargarDatosProductos();
                frame.dispose();

            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException rollbackException) {
                        rollbackException.printStackTrace();
                    }
                }
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al agregar producto: " + e.getMessage());
            } finally {
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                        connection.close();
                    } catch (SQLException closeException) {
                        closeException.printStackTrace();
                    }
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese valores válidos para Precio y Stock.");
        }
    }
}
