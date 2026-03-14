package poo_01;

public class Estudiante extends Persona {
    private String lenguajeAprendizaje;
    private Zona zona;

    public Estudiante(String nombre, String lenguajeAprendizaje) {
        super(nombre); 
        this.lenguajeAprendizaje = lenguajeAprendizaje;
        this.zona = new Zona();
    }

    public Zona getZona() {
        return zona;
    }

    @Override
    public void presentarse() {
        System.out.println("Hola, mi nombre es " + getNombre() + 
                           " y estoy aprendiendo " + lenguajeAprendizaje + ".");
    }
    
    public void escribirCodigo() {
        System.out.println(getNombre() + " está compilando código...");
    }
}