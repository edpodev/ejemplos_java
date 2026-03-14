package poo_01;

public class Estudiante extends Persona implements Runnable {
    
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
    
    @Override
    public void run() {
        realizarExamen();
    }

  
    public void realizarExamen() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getNombre() + " está escribiendo la pregunta " + i + " en " + lenguajeAprendizaje + "...");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                System.out.println("El examen de " + getNombre() + " fue interrumpido.");
            }
        }
        System.out.println("¡" + getNombre() + " ha terminado y entregado su examen!");
    }
}