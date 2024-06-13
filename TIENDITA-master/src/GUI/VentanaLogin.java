package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import BASE.BaseDatos;

public class VentanaLogin extends JFrame {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField verifyPasswordField;

    public VentanaLogin() {
        frame = new JFrame("Ventana de Inicio de Sesión");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.getContentPane().setBackground(new Color(173, 216, 230));
        frame.setLayout(new BorderLayout(10, 10));

        ImageIcon iconoUsuario = new ImageIcon("Usuario.jpg");
        Image imageUsuario = iconoUsuario.getImage();
        Image newImageUsuario = imageUsuario.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon newIconUsuario = new ImageIcon(newImageUsuario);
        JLabel imagenLabelUsuario = new JLabel(newIconUsuario);

        ImageIcon iconoCandado = new ImageIcon("candado.png");
        Image imageCandado = iconoCandado.getImage();
        Image newImageCandado = imageCandado.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon newIconCandado = new ImageIcon(newImageCandado);
        JLabel imagenLabelCandado = new JLabel(newIconCandado);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(173, 216, 230));
        panelPrincipal.setPreferredSize(new Dimension(600, 300));

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBackground(new Color(173, 216, 230));

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.add(lblUsername, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        usernamePanel.add(imagenLabelUsuario, BorderLayout.WEST);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(lblPassword, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(imagenLabelCandado, BorderLayout.WEST);

        JLabel lblVerifyPassword = new JLabel("Verificar Contraseña:");
        lblVerifyPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        verifyPasswordField = new JPasswordField();
        verifyPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel verifyPasswordPanel = new JPanel(new BorderLayout());
        verifyPasswordPanel.add(lblVerifyPassword, BorderLayout.NORTH);
        verifyPasswordPanel.add(verifyPasswordField, BorderLayout.CENTER);
        verifyPasswordPanel.add(new JLabel(), BorderLayout.WEST);

        panelCampos.add(usernamePanel);
        panelCampos.add(passwordPanel);
        panelCampos.add(verifyPasswordPanel);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(173, 216, 230));

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnCrearUsuario = new JButton("Crear Usuario");
        JButton btnVerUsuarios = new JButton("Ver Usuarios");
        JButton btnBorrarUsuario = new JButton("Borrar Usuario");

        btnLogin.setBackground(new Color(50, 205, 50));
        btnLogin.setForeground(Color.WHITE);
        btnCrearUsuario.setBackground(new Color(0, 191, 255));
        btnCrearUsuario.setForeground(Color.WHITE);
        btnVerUsuarios.setBackground(new Color(255, 69, 0));
        btnVerUsuarios.setForeground(Color.WHITE);
        btnBorrarUsuario.setBackground(new Color(255, 0, 0));
        btnBorrarUsuario.setForeground(Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        btnLogin.setPreferredSize(buttonSize);
        btnCrearUsuario.setPreferredSize(buttonSize);
        btnVerUsuarios.setPreferredSize(buttonSize);
        btnBorrarUsuario.setPreferredSize(buttonSize);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btnCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuario();
            }
        });

        btnVerUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarios();
            }
        });

        btnBorrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarUsuario();
            }
        });

        panelBotones.add(btnLogin);
        panelBotones.add(btnCrearUsuario);
        panelBotones.add(btnVerUsuarios);
        panelBotones.add(btnBorrarUsuario);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        frame.add(panelPrincipal);

        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        char[] verifyPasswordChars = verifyPasswordField.getPassword();
        String verifyPassword = new String(verifyPasswordChars);

        if (username.isEmpty() || password.isEmpty() || verifyPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingresa un nombre de usuario y una contraseña.");
            return;
        }

        if (BaseDatos.validarInicioSesion(username, encriptarContrasena(password)) && password.equals(verifyPassword)) {
            frame.dispose();
            new VentanaTiendita();
        } else {
            JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.");
        }
    }

    private void crearUsuario() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        char[] verifyPasswordChars = verifyPasswordField.getPassword();
        String verifyPassword = new String(verifyPasswordChars);

        if (username.isEmpty() || password.isEmpty() || verifyPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, completa todos los campos.");
            return;
        }

        if (!password.equals(verifyPassword)) {
            JOptionPane.showMessageDialog(frame, "La contraseña y la verificación de contraseña no coinciden.");
            return;
        }

        BaseDatos.agregarUsuario(username, encriptarContrasena(password));
        JOptionPane.showMessageDialog(frame, "Usuario creado con éxito.");
    }

    private void verUsuarios() {
        List<String> usuariosBD = BaseDatos.obtenerUsuarios();

        if (usuariosBD.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay usuarios registrados.");
            return;
        }

        StringBuilder usuariosStr = new StringBuilder("Usuarios:\n");
        for (String usuario : usuariosBD) {
            usuariosStr.append(usuario).append("\n");
        }

        JTextArea textArea = new JTextArea(usuariosStr.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        JOptionPane.showMessageDialog(frame, scrollPane, "Lista de Usuarios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void borrarUsuario() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        if (password.equals("123")) {
            BaseDatos.desactivarUsuario(username);
            JOptionPane.showMessageDialog(frame, "Usuario desactivado con éxito.");
        } else {
            JOptionPane.showMessageDialog(frame, "Contraseña incorrecta. No se pudo desactivar el usuario.");
        }
    }

    private String encriptarContrasena(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaLogin();
            }
        });
    }
}
