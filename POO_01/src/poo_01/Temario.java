package poo_01;

public class Temario {
    private String contenido;

    public Temario(String contenido) {
        this.contenido = contenido;
    }

    public void mostrarTemas() {
        System.out.println("Temario de hoy: " + contenido);
    }
}