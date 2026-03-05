import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private RepositorioUsuarios repositorio;

    public LoginFrame(RepositorioUsuarios repositorio) {
        this.repositorio = repositorio;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Login de Sistema");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));
    }

    private void inicializarComponentes() {
        add(new JLabel(" Usuario:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel(" Contraseña:"));
        campoContrasena = new JPasswordField();
        add(campoContrasena);

        JButton botonLogin = new JButton("Ingresar");
        JButton botonSignup = new JButton("Registrarse");

        add(botonLogin);
        add(botonSignup);

        botonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String pass = new String(campoContrasena.getPassword());

                if (usuario.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                } else if (repositorio.validarCredenciales(usuario, pass)) {
                    new HomeFrame(usuario, repositorio).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });

        botonSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignupFrame(repositorio).setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RepositorioUsuarios repo = new RepositorioUsuarios();
                new LoginFrame(repo).setVisible(true);
            }
        });
    }
}