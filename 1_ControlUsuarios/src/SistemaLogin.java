import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SistemaLogin {
    
    // Instanciamos el repositorio una sola vez para toda la aplicación
    private RepositorioUsuarios repositorio;

    public SistemaLogin() {
        repositorio = new RepositorioUsuarios();
        // Al arrancar el sistema, mostramos la pantalla de Login
        mostrarLogin();
    }

    // --- PANTALLA DE LOGIN ---
    public void mostrarLogin() {
        JFrame frameLogin = new JFrame("Login de Sistema");
        frameLogin.setSize(300, 200);
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setLayout(new GridLayout(4, 2, 5, 5));

        frameLogin.add(new JLabel(" Usuario:"));
        JTextField campoUsuario = new JTextField();
        frameLogin.add(campoUsuario);

        frameLogin.add(new JLabel(" Contraseña:"));
        JPasswordField campoContrasena = new JPasswordField();
        frameLogin.add(campoContrasena);

        JButton botonLogin = new JButton("Ingresar");
        JButton botonSignup = new JButton("Registrarse");

        frameLogin.add(botonLogin);
        frameLogin.add(botonSignup);

        // Acción para el botón Ingresar
        botonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String pass = new String(campoContrasena.getPassword());

                if (usuario.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(frameLogin, "Complete todos los campos");
                } else if (repositorio.validarCredenciales(usuario, pass)) {
                    mostrarHome(usuario); // Vamos al Home
                    frameLogin.dispose(); // Cerramos el Login
                } else {
                    JOptionPane.showMessageDialog(frameLogin, "Usuario o contraseña incorrectos");
                }
            }
        });

        botonSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarSignup(); // Vamos al registro
                frameLogin.dispose(); // Cerramos el Login
            }
        });

        frameLogin.setVisible(true);
    }


    public void mostrarSignup() {
        JFrame frameSignup = new JFrame("Registro de Usuario");
        frameSignup.setSize(300, 200);
        frameSignup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameSignup.setLocationRelativeTo(null);
        frameSignup.setLayout(new GridLayout(4, 2, 5, 5));

        frameSignup.add(new JLabel(" Nuevo Usuario:"));
        JTextField campoUsuario = new JTextField();
        frameSignup.add(campoUsuario);

        frameSignup.add(new JLabel(" Nueva Contraseña:"));
        JPasswordField campoContrasena = new JPasswordField();
        frameSignup.add(campoContrasena);

        JButton botonCrear = new JButton("Crear cuenta");
        JButton botonVolver = new JButton("Volver");

        frameSignup.add(botonCrear);
        frameSignup.add(botonVolver);

        botonCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = campoUsuario.getText();
                String pass = new String(campoContrasena.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(frameSignup, "No deje campos vacíos");
                } else {
                    if (repositorio.registrarUsuario(user, pass)) {
                        JOptionPane.showMessageDialog(frameSignup, "¡Registro exitoso!");
                        mostrarLogin(); // Volvemos al Login
                        frameSignup.dispose(); // Cerramos el Registro
                    } else {
                        JOptionPane.showMessageDialog(frameSignup, "El usuario ya existe");
                    }
                }
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarLogin(); // Volvemos al Login
                frameSignup.dispose(); // Cerramos el Registro
            }
        });

        frameSignup.setVisible(true);
    }


    public void mostrarHome(String nombreUsuario) {
        JFrame frameHome = new JFrame("Inicio");
        frameHome.setSize(300, 150);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.setLocationRelativeTo(null);
        frameHome.setLayout(new BorderLayout());

        JLabel etiquetaBienvenida = new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER);
        etiquetaBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        frameHome.add(etiquetaBienvenida, BorderLayout.CENTER);

        JButton botonCerrarSesion = new JButton("Cerrar Sesión");
        frameHome.add(botonCerrarSesion, BorderLayout.SOUTH);

        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarLogin(); 
                frameHome.dispose();
            }
        });

        frameHome.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SistemaLogin();
            }
        });
    }
}