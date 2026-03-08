import java.io.*;

public class RepositorioUsuarios {
    private String nombreArchivo = "usuarios.txt";

    public RepositorioUsuarios() {
        asegurarArchivoExiste();
    }
    
    public void asegurarArchivoExiste() {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        }
    }

    // Retorna una matriz [n][2] donde [i][0] es usuario y [i][1] es contraseña
    public String[][] obtenerTodosLosUsuarios() {
        int cantidadLineas = contarLineas();
        String[][] matrizUsuarios = new String[cantidadLineas][2];

        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            int indice = 0;
            while ((linea = lector.readLine()) != null) {
                if (linea.contains(",")) {
                    String[] partes = linea.split(",");
                    matrizUsuarios[indice][0] = partes[0];
                    matrizUsuarios[indice][1] = partes[1];
                    indice++;
                }
            }
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrizUsuarios;
    }

    private int contarLineas() {
        int lineas = 0;
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
            while (lector.readLine() != null) {
                lineas++;
            }
            lector.close();
        } catch (IOException e) {
            return 0;
        }
        return lineas;
    }

    public boolean usuarioExiste(String usuario) {
        String[][] usuarios = obtenerTodosLosUsuarios();
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i][0] != null && usuarios[i][0].equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        String[][] usuarios = obtenerTodosLosUsuarios();
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i][0] != null && usuarios[i][0].equals(usuario) && 
                usuarios[i][1].equals(contrasena)) {
                return true;
            }
        }
        return false;
    }

    public boolean registrarUsuario(String usuario, String contrasena) {
        if (usuarioExiste(usuario)) {
            return false;
        }
        boolean esRegistrado = false;
        PrintWriter escritor = null;
        try {
            escritor = new PrintWriter(new FileWriter(nombreArchivo, true));
            escritor.println(usuario + "," + contrasena);
            esRegistrado = true;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            esRegistrado = false;
        } finally {
            if (escritor != null) {
                escritor.close();
            }
        }

        return esRegistrado;
    }
}