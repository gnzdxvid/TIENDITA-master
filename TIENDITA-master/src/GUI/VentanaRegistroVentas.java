package GUI;

import CONTROL.Ticket;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

public class VentanaRegistroVentas extends JFrame {
    private DefaultListModel<String> carritoModel;
    private JList<String> carritoList;
    private JTable productosTable;
    private JButton btnAgregar, btnRestar, btnVaciar, btnComprar, btnImprimir;
    private JTextField txtBuscar;
    private boolean compraRealizada = false;


     
    private String obtenerDetallesCompra() {
        StringBuilder detalles = new StringBuilder();

        DefaultListModel<String> carritoModel = (DefaultListModel<String>) carritoList.getModel();
        int totalProductosEnCarrito = carritoModel.size();

        for (int i = 0; i < totalProductosEnCarrito; i++) {
            detalles.append(carritoModel.getElementAt(i)).append("\n");
        }

        return detalles.toString();
    }


    private Connection connection;

    public VentanaRegistroVentas() {
        // Configuración de la ventana
        setTitle("Registro de Ventas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Conectar a la base de datos (Ajusta la URL, el usuario y la contraseña según tu configuración)
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_base_de_datos", "root", "jesus322");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


                // Crear modelo para el carrito
        carritoModel = new DefaultListModel<>();
        carritoList = new JList<>(carritoModel);
        
        // Establecer una fuente moderna y agradable
        Font fuenteModerna = new Font("Arial", Font.PLAIN, 14);

        // Configurar la lista de carrito con la nueva fuente
        carritoList.setFont(fuenteModerna);
        
        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(150, 25));
        txtBuscar.setToolTipText("Buscar producto");
        txtBuscar.setFont(fuenteModerna);

        // Crear modelo para la tabla de productos
        DefaultTableModel productosTableModel = new DefaultTableModel();
        productosTableModel.addColumn("ID");
        productosTableModel.addColumn("Nombre");
        productosTableModel.addColumn("Precio");
        productosTableModel.addColumn("Stock");

        // Crear tabla de productos
        productosTable = new JTable(productosTableModel);
        productosTable.setRowHeight(25);
        productosTable.setFont(fuenteModerna);
        productosTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Crear botones con estilos personalizados
        btnAgregar = new JButton("Agregar al Carrito");
        btnRestar = new JButton("Restar del Carrito");
        btnVaciar = new JButton("Vaciar Carrito");
        btnComprar = new JButton("Comprar");
        btnImprimir = new JButton("Imprimir Ticket");

        // Asignar colores a los botones
        btnAgregar.setBackground(Color.GREEN);
        btnRestar.setBackground(Color.RED);
        btnVaciar.setBackground(Color.YELLOW);
        btnComprar.setBackground(Color.ORANGE);
        btnImprimir.setBackground(Color.BLUE);

        // Asignar fuentes a los botones
        btnAgregar.setFont(fuenteModerna);
        btnRestar.setFont(fuenteModerna);
        btnVaciar.setFont(fuenteModerna);
        btnComprar.setFont(fuenteModerna);
        btnImprimir.setFont(fuenteModerna);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lblBuscar = new JLabel("Buscar: ");
        lblBuscar.setFont(fuenteModerna);
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);

        // Panel izquierdo (tabla de productos)
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelIzquierdo.add(panelBusqueda, BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(productosTable), BorderLayout.CENTER);
        panelIzquierdo.add(btnAgregar, BorderLayout.SOUTH);

        // Panel derecho (carrito de compras)
        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelDerecho.add(new JScrollPane(carritoList), BorderLayout.CENTER);
        JPanel panelBotonesDerechos = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotonesDerechos.add(btnRestar);
        panelBotonesDerechos.add(btnVaciar);
        panelBotonesDerechos.add(btnComprar);
        panelDerecho.add(panelBotonesDerechos, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(panelIzquierdo, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
        add(btnImprimir, BorderLayout.SOUTH);
        
        
        
        

        // Configurar acciones de los botones
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAlCarrito();
            }
        });
        
        btnImprimir.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        imprimirTicket();
    }
});
        
        btnRestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restarDelCarrito();
            }
        });

        btnVaciar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        vaciarCarrito();
    }
});
        
        btnComprar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtén los detalles de la compra y el total
        double totalCompra = comprar();
        String detallesCompra = obtenerDetallesCompra() + "\n\nTotal de la compra: $" + totalCompra;

        // Pasa los detalles y el total al Ticket
        Ticket ticket = new Ticket(detallesCompra);
        ticket.setVisible(true);
    }
});

        
        
        
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) productosTable.getModel());
        productosTable.setRowSorter(sorter);
        txtBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = txtBuscar.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        

        // Inicializar la tabla de productos y el carrito
        actualizarTablaProductos();
        actualizarCarrito();
    }
    
    
    
    

    private void actualizarTablaProductos() {
        DefaultTableModel productosTableModel = (DefaultTableModel) productosTable.getModel();
        productosTableModel.setRowCount(0);

        try {
            // Obtener productos de la tabla carnes
            String queryCarnes = "SELECT id_producto, nombre, precio, stock FROM productos";
            PreparedStatement preparedStatementCarnes = connection.prepareStatement(queryCarnes);
            ResultSet resultSetCarnes = preparedStatementCarnes.executeQuery();

            // Agregar productos de carnes a la tabla
            while (resultSetCarnes.next()) {
                Object[] fila = {
                        resultSetCarnes.getInt("id_producto"),
                        resultSetCarnes.getString("nombre"),
                        resultSetCarnes.getDouble("precio"),
                        resultSetCarnes.getInt("stock")
                };
                productosTableModel.addRow(fila);
            }
        } catch (SQLException e) {
            // Manejo de errores
        }
    }

    private void insertarVenta(String nombreProducto, int cantidad, double gananciaTotal) {
    try {
        // Verificar si la venta ya existe
        if (ventaExiste(nombreProducto)) {
            // Si existe, realizar la actualización de cantidad y ganancias
            actualizarVentaExistente(nombreProducto, cantidad, gananciaTotal);
        } else {
            // Si no existe, realizar la inserción completa
            String query = "INSERT INTO ventas (nombre_producto, cantidad, ganancias) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nombreProducto);
            preparedStatement.setInt(2, cantidad);
            preparedStatement.setDouble(3, gananciaTotal);
            preparedStatement.executeUpdate();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al insertar/actualizar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    private void vaciarCarrito() {
    // Iterar sobre los productos en el carrito y devolver la cantidad al stock
    for (int i = 0; i < carritoModel.size(); i++) {
        String infoProducto = carritoModel.getElementAt(i);
        String[] partes = infoProducto.split(" - Cantidad: ");
        String nombreProducto = partes[0];
        int cantidadEnCarrito = Integer.parseInt(partes[1]);

        // Devolver la cantidad al stock de productos
        int idProducto = getIdProductoDesdeBD(nombreProducto);
        int stockActual = getStockProductoDesdeBD(idProducto);
        int nuevoStock = stockActual + cantidadEnCarrito;
        actualizarStockEnBaseDeDatos(idProducto, nuevoStock);
    }

    // Limpiar el modelo del carrito
    carritoModel.removeAllElements();

    // Actualizar la tabla de productos
    actualizarTablaProductos();
}
    private void imprimirTicket() {
    if (!compraRealizada) {
        // Obtener detalles de la compra y abrir la ventana de Ticket
        String detallesCompra = obtenerDetallesCompra();
        Ticket ticket = new Ticket(detallesCompra);
        ticket.setVisible(true);

        // Actualizar la base de datos y la GUI después de imprimir el ticket
        for (int i = 0; i < carritoModel.size(); i++) {
            String infoProducto = carritoModel.getElementAt(i);
            String[] partes = infoProducto.split(" - Cantidad: ");
            String nombreProducto = partes[0];
            int cantidadEnCarrito = Integer.parseInt(partes[1]);

            // Actualizar la base de datos con la venta
            insertarVenta(nombreProducto, cantidadEnCarrito, getPrecioProductoDesdeBD(nombreProducto) * cantidadEnCarrito);
        }

        // Vaciar el carrito después de imprimir el ticket
        vaciarCarrito();
        actualizarTablaProductos();

        // Reactivar los botones después de imprimir el ticket
        btnRestar.setEnabled(true);
        btnVaciar.setEnabled(true);
        btnComprar.setEnabled(true);

        // Marcar que la compra ya se realizó
        compraRealizada = true;
    } else {
        JOptionPane.showMessageDialog(this, "La compra ya se ha registrado.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    

    
    private boolean ventaExiste(String nombreProducto) {
    try {
        String query = "SELECT * FROM ventas WHERE nombre_producto = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nombreProducto);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();  // Devuelve true si existe al menos una fila
    } catch (SQLException e) {
        e.printStackTrace();
        return false;  // Manejo básico de excepciones, puedes ajustarlo según tus necesidades
    }
}
    
    private void actualizarVentaExistente(String nombreProducto, int cantidad, double gananciaTotal) {
    try {
        String query = "UPDATE ventas SET cantidad = cantidad + ?, ganancias = ganancias + ? WHERE nombre_producto = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cantidad);
        preparedStatement.setDouble(2, gananciaTotal);
        preparedStatement.setString(3, nombreProducto);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    private void restarDelCarrito() {
    int indiceSeleccionado = carritoList.getSelectedIndex();

    if (indiceSeleccionado == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un producto del carrito para restar", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String infoProducto = carritoModel.getElementAt(indiceSeleccionado);
    String[] partes = infoProducto.split(" - Cantidad: ");
    String nombreProducto = partes[0];
    int cantidadEnCarrito = Integer.parseInt(partes[1]);

    // Preguntar al usuario cuántos productos desea restar
    String cantidadString = JOptionPane.showInputDialog(this, "Ingrese la cantidad a restar del carrito:");

    if (cantidadString == null || cantidadString.trim().isEmpty()) {
        return;
    }

    try {
        int cantidadARestar = Integer.parseInt(cantidadString);

        if (cantidadARestar <= 0 || cantidadARestar > cantidadEnCarrito) {
            JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Restar la cantidad del carrito
        cantidadEnCarrito -= cantidadARestar;

        // Actualizar el modelo del carrito
        if (cantidadEnCarrito > 0) {
            carritoModel.setElementAt(nombreProducto + " - Cantidad: " + cantidadEnCarrito, indiceSeleccionado);
        } else {
            // Si la cantidad llega a cero, eliminar el producto del carrito
            carritoModel.removeElementAt(indiceSeleccionado);
        }

        // Devolver la cantidad restada al stock de productos
        int idProducto = getIdProductoDesdeBD(nombreProducto);
        int stockActual = getStockProductoDesdeBD(idProducto);
        int nuevoStock = stockActual + cantidadARestar;
        actualizarStockEnBaseDeDatos(idProducto, nuevoStock);

        // Actualizar la tabla de productos y cualquier otra lógica necesaria
        actualizarTablaProductos();
        // Otras operaciones si es necesario

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private int getStockProductoDesdeBD(int idProducto) {
    try {
        String query = "SELECT stock FROM productos WHERE id_producto = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idProducto);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("stock");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0; // Puedes manejar esto de acuerdo a tus necesidades
}

    
    private int getIdProductoDesdeBD(String nombreProducto) {
    try {
        String query = "SELECT id_producto FROM productos WHERE nombre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nombreProducto);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id_producto");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0; // Puedes manejar esto de acuerdo a tus necesidades
}
    
    private void agregarNuevoAlCarrito(String nombreProducto, double precioProducto, int stockProducto) {
    String cantidadString = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar al carrito:");

    if (cantidadString == null || cantidadString.trim().isEmpty()) {
        return;
    }

    try {
        int cantidad = Integer.parseInt(cantidadString);

        if (cantidad <= 0 || cantidad > stockProducto) {
            JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        carritoModel.addElement(nombreProducto + " - Cantidad: " + cantidad);

        // Restar la cantidad de la tabla de productos
        actualizarStockEnBaseDeDatos(getIdProductoDesdeBD(nombreProducto), cantidad);

        // Actualizar la tabla de productos y cualquier otra lógica necesaria
        actualizarTablaProductos();
        // Otras operaciones si es necesario

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void actualizarCantidadEnCarrito(int indice, String nombreProducto, int stockProducto) {
        String cantidadString = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar al carrito:");

        if (cantidadString == null || cantidadString.trim().isEmpty()) {
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadString);

            if (cantidad <= 0 || cantidad > stockProducto) {
                JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar la cantidad en el carrito
            carritoModel.setElementAt(nombreProducto + " - Cantidad: " + cantidad, indice);

            // Restar la cantidad de la tabla de productos
            actualizarStockEnBaseDeDatos(getIdProductoDesdeBD(nombreProducto), cantidad);

            // Actualizar la tabla de productos y cualquier otra lógica necesaria
            actualizarTablaProductos();
            // Otras operaciones si es necesario

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void agregarAlCarrito() {
        int filaSeleccionada = productosTable.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para agregar al carrito", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idProducto = (int) productosTable.getValueAt(filaSeleccionada, 0);
        String nombreProducto = (String) productosTable.getValueAt(filaSeleccionada, 1);
        double precioProducto = (double) productosTable.getValueAt(filaSeleccionada, 2);
        int stockProducto = (int) productosTable.getValueAt(filaSeleccionada, 3);

        // Preguntar por la cantidad
        String cantidadString = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar al carrito:");

        if (cantidadString == null || cantidadString.trim().isEmpty()) {
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadString);

            if (cantidad <= 0 || cantidad > stockProducto) {
                JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el producto ya está en el carrito
            boolean productoEnCarrito = false;
            for (int i = 0; i < carritoModel.size(); i++) {
                String infoProducto = carritoModel.getElementAt(i);
                if (infoProducto.startsWith(nombreProducto)) {
                    // El producto ya está en el carrito, actualizar la cantidad
                    productoEnCarrito = true;
                    int cantidadExistente = getCantidadEnCarrito(infoProducto);
                    carritoModel.setElementAt(nombreProducto + " - Cantidad: " + (cantidadExistente + cantidad), i);
                    break;
                }
            }

            // Si el producto no está en el carrito, agregarlo con la cantidad ingresada
            if (!productoEnCarrito) {
                carritoModel.addElement(nombreProducto + " - Cantidad: " + cantidad);
            }

            // Restar la cantidad de la tabla de productos
            actualizarStockEnBaseDeDatos(idProducto, stockProducto - cantidad);

            // Actualizar la tabla de productos y cualquier otra lógica necesaria
            actualizarTablaProductos();
            // Otras operaciones si es necesario

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int getCantidadEnCarrito(String infoProducto) {
        String[] partes = infoProducto.split(" - Cantidad: ");
        return Integer.parseInt(partes[1]);
    }

    
    private void actualizarStockEnBaseDeDatos(int idProducto, int nuevoStock) {
        try {
            String query = "UPDATE productos SET stock = ? WHERE id_producto = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nuevoStock);
            preparedStatement.setInt(2, idProducto);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double comprar() {
    DefaultListModel<String> carritoModel = (DefaultListModel<String>) carritoList.getModel();
    int totalProductosEnCarrito = carritoModel.size();
    double totalCompra = 0;

    if (totalProductosEnCarrito == 0) {
        JOptionPane.showMessageDialog(this, "El carrito está vacío. Agregue productos antes de comprar.", "Error", JOptionPane.ERROR_MESSAGE);
        return 0;
    }

    try {
        for (int i = 0; i < totalProductosEnCarrito; i++) {
            String[] infoProducto = carritoModel.getElementAt(i).split(" - Cantidad: ");
            String nombreProducto = infoProducto[0];
            int cantidad = Integer.parseInt(infoProducto[1]);

            // Obtener el precio del producto desde la tabla de productos
            double precio = getPrecioProductoDesdeBD(nombreProducto);

            // Sumar al total de la compra
            totalCompra += (precio * cantidad);

            // Insertar la venta para este producto
            insertarVenta(nombreProducto, cantidad, precio);

            // Restar la cantidad del stock de productos
            int idProducto = getIdProductoDesdeBD(nombreProducto);
            int stockActual = getStockProductoDesdeBD(idProducto);
            int nuevoStock = stockActual - cantidad;
            actualizarStockEnBaseDeDatos(idProducto, nuevoStock);
        }

        // Se generó una compra
        JOptionPane.showMessageDialog(this, "Compra realizada con éxito. Presione 'Imprimir Ticket' para finalizar la compra.", "Compra", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al realizar la compra", "Error", JOptionPane.ERROR_MESSAGE);
        return 0;
    }

    // Restablecer la variable de compra realizada a false
    compraRealizada = false;

    // Devolver el total de la compra
    return totalCompra;
}





// Método para obtener el precio del producto desde la base de datos
private double getPrecioProductoDesdeBD(String nombreProducto) {
    try {
        String query = "SELECT precio FROM productos WHERE nombre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nombreProducto);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("precio");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0; // Puedes manejar esto de acuerdo a tus necesidades
}

    private void actualizarCarrito() {
        // Lógica para actualizar el carrito
        // ...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaRegistroVentas().setVisible(true);
            }
        });
    }
}
