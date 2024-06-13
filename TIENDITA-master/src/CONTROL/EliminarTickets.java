package CONTROL;

import BASE.BaseDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EliminarTickets extends JFrame {

    private JComboBox<String> ticketComboBox;
    private JTextArea contenidoTicketTextArea;

    public EliminarTickets() {
        setTitle("Eliminar Tickets");
        setSize(500, 400); // Aumentamos el tamaño para dar espacio al área de texto
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 204, 204)); // Color de fondo rosa claro

        // Panel superior con el ComboBox
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Alineación y espaciado
        JLabel label = new JLabel("Seleccione el ticket a eliminar:");
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente y tamaño del título
        ticketComboBox = new JComboBox<>();
        // Llena el ComboBox con los tickets desde la base de datos
        cargarTicketsDesdeBD();
        panelSuperior.add(label);
        panelSuperior.add(ticketComboBox);

        // Panel central con el área de texto para mostrar el contenido del ticket
        JPanel panelCentral = new JPanel(new BorderLayout());
        contenidoTicketTextArea = new JTextArea();
        contenidoTicketTextArea.setEditable(false);
        contenidoTicketTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente y tamaño del texto en el área de texto
        JScrollPane scrollPane = new JScrollPane(contenidoTicketTextArea);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con el botón para eliminar
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Alineación y espaciado
        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBackground(Color.RED); // Color de fondo rojo
        eliminarButton.setForeground(Color.WHITE); // Texto en color blanco
        eliminarButton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente y tamaño del texto del botón
        panelInferior.add(eliminarButton);

        // ActionListener para el botón Eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el ticket seleccionado
                String ticketSeleccionado = (String) ticketComboBox.getSelectedItem();

                // Verificar si se seleccionó un ticket
                if (ticketSeleccionado != null) {
                    // Confirmar antes de eliminar
                    int confirmacion = JOptionPane.showConfirmDialog(EliminarTickets.this, "¿Seguro que quieres eliminar el ticket?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        // Lógica para eliminar el ticket de la base de datos
                        if (eliminarTicketDeBD(ticketSeleccionado)) {
                            JOptionPane.showMessageDialog(EliminarTickets.this, "Ticket eliminado: " + ticketSeleccionado);
                            // Actualizar ComboBox y área de texto después de eliminar
                            cargarTicketsDesdeBD();
                            contenidoTicketTextArea.setText("");
                        } else {
                            JOptionPane.showMessageDialog(EliminarTickets.this, "Error al eliminar el ticket.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(EliminarTickets.this, "Seleccione un ticket antes de eliminar.");
                }
            }
        });

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelCentral, BorderLayout.CENTER);
        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }

    private void cargarTicketsDesdeBD() {
        // Limpia el ComboBox antes de cargar nuevos tickets
        ticketComboBox.removeAllItems();

        // Lógica para cargar los tickets desde la base de datos
        try {
            Connection connection = BaseDatos.getConnection();
            String sql = "SELECT id_ticket FROM tickets"; // Modifica 'id_ticket' según el nombre real de la columna
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ticketComboBox.addItem(resultSet.getString("id_ticket"));
                }
            }
            // Cerrar la conexión
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean eliminarTicketDeBD(String ticket) {
        // Lógica para eliminar el ticket de la base de datos
        Connection connection = null;
        try {
            connection = BaseDatos.getConnection();
            String sql = "DELETE FROM tickets WHERE id_ticket = ?"; // Cambia 'id_ticket' según la estructura de tu tabla
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, ticket);
                int filasAfectadas = preparedStatement.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            // Cerrar la conexión en el bloque finally para asegurar que se cierre correctamente
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EliminarTickets::new);
    }
}
