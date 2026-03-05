import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    public HomeFrame(String nombreUsuario, RepositorioUsuarios repositorio) {
        setTitle("Inicio");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel etiquetaBienvenida = new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER);
        etiquetaBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        add(etiquetaBienvenida, BorderLayout.CENTER);

        JButton botonCerrarSesion = new JButton("Cerrar Sesión");
        add(botonCerrarSesion, BorderLayout.SOUTH);

        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(repositorio).setVisible(true);
                dispose();
            }
        });
    }
}