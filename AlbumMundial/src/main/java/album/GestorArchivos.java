package album;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorArchivos {

    private static final String RUTA_ALBUM   = "data/album.txt";
    private static final String RUTA_EQUIPO  = "data/album_equipo.txt";
    private static final String RUTA_USUARIO = "data/album_usuario.txt";
    private static final String SEP          = "\\|";

    private int contarLineas(String ruta) {
        int n = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (!l.isEmpty() && !l.startsWith("#") && !l.startsWith("usuario|")) n++;
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer: " + ruta);
        }
        return n;
    }


    public Pais[] cargarPaises() {
        int n = contarLineas(RUTA_ALBUM);
        Pais[] paises = new Pais[n];
        int idx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ALBUM))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty() || l.startsWith("#")) continue;
                String[] p = l.split(SEP);
                if (p.length < 6) continue;
                paises[idx++] = new Pais(
                        p[0].trim(), p[1].trim(),
                        Integer.parseInt(p[2].trim()),
                        p[3].trim(),
                        Integer.parseInt(p[4].trim()),
                        Integer.parseInt(p[5].trim())
                );
            }
        } catch (IOException e) {
            System.out.println("album.txt: " + e.getMessage());
        }
        return paises;
    }

    public Carta[] cargarCartas() {
        int n = contarLineas(RUTA_EQUIPO);
        Carta[] cartas = new Carta[n];
        int idx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_EQUIPO))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty() || l.startsWith("#")) continue;
                String[] p = l.split(SEP);
                if (p.length < 6) continue;
                cartas[idx++] = new Carta(
                        p[0].trim(), p[1].trim(), p[2].trim(),
                        Integer.parseInt(p[3].trim()),
                        p[4].trim(), p[5].trim()
                );
            }
        } catch (IOException e) {
            System.out.println("album_equipo.txt: " + e.getMessage());
        }
        return cartas;
    }

    public CartaUsuario[] cargarCartasUsuario() {
        int total = contarLineas(RUTA_USUARIO);
        CartaUsuario[] reg = new CartaUsuario[total];
        int idx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_USUARIO))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty() || l.startsWith("#") || l.startsWith("usuario|")) continue;
                String[] p = l.split(SEP);
                if (p.length < 7) continue;
                reg[idx++] = new CartaUsuario(
                        p[0].trim(), p[1].trim(),
                        Integer.parseInt(p[2].trim()),
                        p[3].trim().equals("1"),
                        Integer.parseInt(p[4].trim()),
                        Integer.parseInt(p[5].trim()),
                        Integer.parseInt(p[6].trim())
                );
            }
        } catch (IOException e) {
            System.out.println("album_usuario.txt: " + e.getMessage());
        }
        CartaUsuario[] resultado = new CartaUsuario[idx];
        for (int i = 0; i < idx; i++) resultado[i] = reg[i];
        return resultado;
    }


    public void guardarCartasUsuario(CartaUsuario[] registros, int usados) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIO))) {
            bw.write("usuario|no_carta|cantidad|pegada|pagina|fila|columna");
            bw.newLine();
            for (int i = 0; i < usados; i++) {
                if (registros[i] != null) {
                    bw.write(registros[i].toLinea());
                    bw.newLine();
                }
            }
            System.out.println("album_usuario.txt guardado ("
                    + usados + " registros).");
        } catch (IOException e) {
            System.out.println("No se pudo guardar: " + e.getMessage());
        }
    }
}