package GUI;

import BASE.BaseDatos;
import CONTROL.VerTickets;
import CONTROL.ProductosMenosVendidosVentana;
import CONTROL.ProductosMasVendidosVentana;
import CONTROL.GananciasTotales;
import CONTROL.EliminarTickets;
import GUI.VentanaRegistroVentas;
import GUI.VentanaEliminarProductos;
import GUI.VentanaAgregarProducto;
import GUI.VentanaLogin;
import GUI.VentanaProductosBajoStock;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class VentanaTiendita {

    JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public VentanaTiendita() {
        frame = new JFrame("Ventana de la Tiendita");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        // Colores personalizados
        Color colorFondo = new Color(255, 193, 7);  // Amarillo
        Color colorProductos = new Color(76, 175, 80);  // Verde
        Color colorTienda = new Color(33, 150, 243);  // Azul
        Color colorVentas = new Color(255, 87, 34);  // Naranja

        frame.getContentPane().setBackground(colorFondo);
        frame.setLayout(new BorderLayout(10, 10));

        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuProductos = new JMenu("Productos");
        JMenuItem itemAgregar = new JMenuItem("Agregar Producto");
        JMenuItem itemModificar = new JMenuItem("Modificar Producto");
        JMenuItem itemEliminar = new JMenuItem("Eliminar Producto");
        JMenuItem itemReportes = new JMenuItem("Reportes");

        menuProductos.add(itemAgregar);
        menuProductos.add(itemModificar);
        menuProductos.add(itemEliminar);
        menuProductos.add(itemReportes);
        menuProductos.setBackground(colorProductos);
        menuBar.add(menuProductos);

        JMenu menuTienda = new JMenu("Gestión Tienda");
        JMenuItem itemMasVendidos = new JMenuItem("Productos más vendidos");
        JMenuItem itemMenosVendidos = new JMenuItem("Productos menos vendidos");
        JMenuItem itemGananciasTotales = new JMenuItem("Ganancias totales");
        JMenuItem itemProductosBajoStock = new JMenuItem("Productos Con Bajo Stock");
        menuTienda.add(itemMasVendidos);
        menuTienda.add(itemMenosVendidos);
        menuTienda.add(itemGananciasTotales);
        menuTienda.add(itemProductosBajoStock);
        menuTienda.setBackground(colorTienda);
        menuBar.add(menuTienda);

        JMenu menuVentas = new JMenu("Ventas");
        JMenuItem itemRegistroVentas = new JMenuItem("Registrar Venta");
        menuVentas.add(itemRegistroVentas);
        menuVentas.setBackground(colorVentas);
        menuBar.add(menuVentas);

        frame.setJMenuBar(menuBar);

        JMenu menuTickets = new JMenu("Tickets");
        JMenuItem itemVerTickets = new JMenuItem("Ver Tickets");
        JMenuItem itemEliminarTickets = new JMenuItem("Eliminar Tickets");

        menuTickets.add(itemVerTickets);
        menuTickets.add(itemEliminarTickets);
        menuTickets.setBackground(colorTienda);
        menuBar.add(menuTickets);

        // Botón para cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(colorVentas);
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new VentanaLogin();
            }
        });

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(colorFondo);
        panelBotones.add(btnCerrarSesion);

        // Tabla para mostrar productos
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Establecer colores para la tabla
        table.setBackground(colorVentas);
        table.setForeground(Color.WHITE);

        // Agregar componentes al frame
        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Cargar datos en la tabla al iniciar la ventana
        cargarDatosProductos();

        // Manejar acciones de los elementos del menú
        itemAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        itemModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaVisualizarProductos();
            }
        });

        itemGananciasTotales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaGananciasTotales();
            }
        });
        
        

        itemEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaEliminarProductos();
            }
        });

        itemReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaReportes();
            }
        });

        itemRegistroVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaRegistroVentas();
            }
        });

        itemVerTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaVerTickets();
            }
        });

        itemEliminarTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaEliminarTickets();
            }
        });
        
        itemProductosBajoStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirProductosBajoStock();
            }
        });

        itemMasVendidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductosMasVendidosVentana();
            }
        });

        itemMenosVendidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductosMenosVendidosVentana();
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }

    void cargarDatosProductos() {
        tableModel.setRowCount(0);
        List<Object[]> productos = BaseDatos.obtenerProductos();
        for (Object[] producto : productos) {
            tableModel.addRow(new Object[]{producto[0], producto[1], producto[2], producto[3]});
        }
    }

    private void agregarProducto() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaAgregarProducto(VentanaTiendita.this);
            }
        });
    }
    
    private void abrirProductosBajoStock() {
        VentanaProductosBajoStock VentanaProductosBajoStock = new VentanaProductosBajoStock();
        VentanaProductosBajoStock.setVisible(true);
    }

    private void abrirVentanaVisualizarProductos() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaVisualizarProductos(VentanaTiendita.this);
            }
        });
    }

    private void abrirVentanaGananciasTotales() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GananciasTotales();
            }
        });
    }

    private void abrirVentanaEliminarProductos() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaEliminarProductos(VentanaTiendita.this);
            }
        });
    }

    private void abrirVentanaReportes() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaReportes();
            }
        });
    }

    private void abrirVentanaRegistroVentas() {
        VentanaRegistroVentas ventanaRegistroVentas = new VentanaRegistroVentas();
        ventanaRegistroVentas.setVisible(true);
    }

    private void abrirVentanaVerTickets() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VerTickets();
            }
        });
    }

    private void abrirVentanaEliminarTickets() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EliminarTickets();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaTiendita();
            }
        });
    }
}

       
