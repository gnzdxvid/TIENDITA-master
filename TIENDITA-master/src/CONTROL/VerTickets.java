package CONTROL;

import BASE.BaseDatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VerTickets extends JFrame {

    private JComboBox<String> ticketComboBox;
    private JTextArea ticketTextArea;

    public VerTickets() {
        setTitle("Ver Tickets");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en el centro de la pantalla

        // Panel principal con BorderLayout para mejor disposición de componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 230, 204)); // Color de fondo melocotón claro

        JLabel label = new JLabel("Seleccione un ticket:");
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente y tamaño del título

        ticketComboBox = new JComboBox<>();
        cargarTicketsDesdeBaseDeDatos();  // Carga los tickets desde la base de datos
        ticketComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarContenidoTicketSeleccionado();
            }
        });

        ticketTextArea = new JTextArea(10, 30);
        ticketTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente y tamaño del texto en el área de texto
        JScrollPane scrollPane = new JScrollPane(ticketTextArea);

        JButton btnReimprimir = new JButton("Reimprimir Ticket");
        btnReimprimir.setBackground(Color.BLUE); // Cambia el color del botón a azul
        btnReimprimir.setForeground(Color.WHITE); // Texto en color blanco
        btnReimprimir.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente y tamaño del texto del botón
        btnReimprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reimprimirTicketSeleccionado();
            }
        });

        // Panel para los controles de selección y botón
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(255, 230, 204)); // Mismo color de fondo melocotón claro
        controlPanel.add(label);
        controlPanel.add(ticketComboBox);
        controlPanel.add(btnReimprimir);

        // Agregar componentes al panel principal
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Agregar panel principal al JFrame
        add(panel);

        setVisible(true);
    }

    private void reimprimirTicketSeleccionado() {
        String selectedTicket = (String) ticketComboBox.getSelectedItem();
        if (selectedTicket != null) {
            String idTicket = selectedTicket.split(" - ")[0];  // Obtén el ID del ticket
            String contenido = BaseDatos.obtenerContenidoTicketDesdeBaseDeDatos(idTicket);

            // Lógica para reimprimir el ticket (puedes utilizar la clase Ticket existente)
            Ticket ticketReimpreso = new Ticket(contenido);
            ticketReimpreso.setVisible(true);
        }
    }

    private void mostrarContenidoTicketSeleccionado() {
        String selectedTicket = (String) ticketComboBox.getSelectedItem();
        if (selectedTicket != null) {
            String idTicket = selectedTicket.split(" - ")[0];  // Obtén el ID del ticket
            String contenido = BaseDatos.obtenerContenidoTicketDesdeBaseDeDatos(idTicket);
            ticketTextArea.setText(contenido);
        }
    }

    private void cargarTicketsDesdeBaseDeDatos() {
        List<String> tickets = BaseDatos.obtenerTickets();
        for (String ticket : tickets) {
            ticketComboBox.addItem(ticket);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VerTickets();
            }
        });
    }
}
