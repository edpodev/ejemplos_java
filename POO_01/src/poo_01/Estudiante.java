package poo_01;

/**
 *
 * @author danielporon
 */

public class Estudiante extends Persona {
    private String lenguajeAprendizaje;

    public Estudiante(String nombre, String lenguajeAprendizaje) {
        super(nombre); 
        this.lenguajeAprendizaje = lenguajeAprendizaje;
    }

    @Override
    public void presentarse() {
        System.out.println("Hola, mi nombre es " + getNombre() + 
                           " y estoy aprendiendo a programar en " + lenguajeAprendizaje + ".");
    }
    
    public void escribirCodigo() {
        System.out.println(getNombre() + " está compilando código...");
    }
}