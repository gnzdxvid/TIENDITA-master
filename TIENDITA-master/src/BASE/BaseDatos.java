package BASE;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String USER = "root";
    private static final String PASSWORD = "jesus322";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validarInicioSesion(String username, String password) {
        String query = "SELECT nombre_usuario, contraseña FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void agregarUsuario(String username, String password) {
        String query = "INSERT INTO usuarios (nombre_usuario, contraseña, estado) VALUES (?, ?, 'activo')";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> obtenerUsuarios() {
        List<String> usuarios = new ArrayList<>();
        String query = "SELECT nombre_usuario FROM usuarios WHERE estado = 'activo'";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                usuarios.add(resultSet.getString("nombre_usuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public static void desactivarUsuario(String username) {
        String query = "UPDATE usuarios SET estado = 'inactivo' WHERE nombre_usuario = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Object[]> obtenerProductos() {
        List<Object[]> productos = new ArrayList<>();
        String query = "SELECT id_producto, nombre, precio, stock FROM productos";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Object[] rowData = {
                    resultSet.getInt("id_producto"),
                    resultSet.getString("nombre"),
                    resultSet.getDouble("precio"),
                    resultSet.getInt("stock")
                };
                productos.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public static int getIdProductoDesdeBD(String nombreProducto) {
        String query = "SELECT id_producto FROM productos WHERE nombre = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombreProducto);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_producto");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Si no se encuentra el producto, retorna -1
    }

    public static int getStockProductoDesdeBD(int idProducto) {
        String query = "SELECT stock FROM productos WHERE id_producto = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProducto);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("stock");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void actualizarStockEnBaseDeDatos(int idProducto, int nuevoStock) {
        String query = "UPDATE productos SET stock = ? WHERE id_producto = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, nuevoStock);
            preparedStatement.setInt(2, idProducto);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getPrecioProductoDesdeBD(String nombreProducto) {
        String query = "SELECT precio FROM productos WHERE nombre = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombreProducto);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("precio");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;  // Si no se encuentra el producto, retorna 0.0
    }
    
    public static List<String> obtenerTickets() {
        List<String> tickets = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_ticket, fecha FROM tickets")) {
            while (resultSet.next()) {
                String idTicket = resultSet.getString("id_ticket");
                String fecha = resultSet.getString("fecha");
                tickets.add(idTicket + " - " + fecha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static String obtenerContenidoTicketDesdeBaseDeDatos(String idTicket) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT contenido FROM tickets WHERE id_ticket = ?")) {
            preparedStatement.setString(1, idTicket);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("contenido");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<Object[]> obtenerProductosBajoStock() {
        List<Object[]> productosBajoStock = new ArrayList<>();
        String query = "SELECT id_producto, nombre, precio, stock FROM productos WHERE stock < 10";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                productosBajoStock.add(new Object[]{idProducto, nombre, precio, stock});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productosBajoStock;
    }

    public static void insertarVenta(String nombreProducto, int cantidad, double gananciaTotal) {
        String query = "INSERT INTO ventas (nombre_producto, cantidad, ganancia_total) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombreProducto);
            preparedStatement.setInt(2, cantidad);
            preparedStatement.setDouble(3, gananciaTotal);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
