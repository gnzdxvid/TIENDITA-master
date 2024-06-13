package CONTROL;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class Ticket extends JFrame {

    private JTextArea textArea;

    public Ticket(String contenidoTicket) {
        initComponents(contenidoTicket);
        setLocationRelativeTo(null);
    }

    private void initComponents(String contenidoTicket) {
        setTitle("Visor de Archivo .dat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(contenidoTicket);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(evt -> btnSalirActionPerformed(evt));

        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(evt -> btnImprimirActionPerformed(evt));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalir);
        buttonPanel.add(btnImprimir);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {
        String contenidoTicket = textArea.getText();
        imprimirYGuardarTicket(contenidoTicket);
    }
    
    

    private void imprimirYGuardarTicket(String contenidoTicket) {
        String fechaHora = obtenerFechaHoraActual("yyyyMMdd_HHmmss");
        String fileName = "ticket_" + fechaHora + ".pdf";

        try {
            // Crear el archivo PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Agregar información adicional al PDF
            agregarInformacion(document);

            // Agregar contenido al PDF
            document.add(new Paragraph(contenidoTicket));

            // Cerrar el documento
            document.close();

            // Abrir el archivo PDF
            Desktop.getDesktop().open(new File(fileName));

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al imprimir el ticket", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar en la base de datos
        guardarEnBaseDeDatos(contenidoTicket, fechaHora);

        JOptionPane.showMessageDialog(this, "Ticket impreso y guardado en la base de datos", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void agregarInformacion(Document document) throws DocumentException {
        // Agregar información adicional al inicio del PDF
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        String fechaActual = dateFormat.format(new Date());

        document.add(new Paragraph("Nombre de la tienda: Mi Tienda"));
        document.add(new Paragraph("Fecha y hora de la compra: " + fechaActual));
        // Agregar más información según tus necesidades
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Detalle de la compra:"));
    }

    private void guardarEnBaseDeDatos(String contenidoTicket, String fechaHora) {
        try {
            // Establecer la conexión con la base de datos
            String url = "jdbc:mysql://localhost:3306/tu_base_de_datos";
            String usuario = "root";
            String contraseña = "jesus322";

            try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
                // Preparar la consulta SQL para insertar en la tabla 'tickets'
                String insertQuery = "INSERT INTO tickets (fecha, contenido) VALUES (?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    // Establecer la fecha actual y el contenido del ticket
                    Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
                    preparedStatement.setTimestamp(1, fechaActual);
                    preparedStatement.setString(2, contenidoTicket);

                    // Ejecutar la consulta de inserción
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private String obtenerFechaHoraActual(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        // Establecer el timezone a México
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));

        // Ejemplo de uso
        String contenido = "Producto 1 - Cantidad: 2\nProducto 2 - Cantidad: 1";
        Ticket ticket = new Ticket(contenido);
        ticket.setVisible(true);
    }
}
