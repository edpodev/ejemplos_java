import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private RepositorioUsuarios repositorio;

    public SignupFrame(RepositorioUsuarios repositorio) {
        this.repositorio = repositorio;
        setTitle("Registro de Usuario");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel(" Nuevo Usuario:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel(" Nueva Contraseña:"));
        campoContrasena = new JPasswordField();
        add(campoContrasena);

        JButton botonCrear = new JButton("Crear cuenta");
        JButton botonVolver = new JButton("Volver");

        add(botonCrear);
        add(botonVolver);

        botonCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = campoUsuario.getText();
                String pass = new String(campoContrasena.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No deje campos vacíos");
                } else {
                    if (repositorio.registrarUsuario(user, pass)) {
                        JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
                        new LoginFrame(repositorio).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "El usuario ya existe");
                    }
                }
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(repositorio).setVisible(true);
                dispose();
            }
        });
    }
}