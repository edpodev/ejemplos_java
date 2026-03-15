package poo_01;

public class Estudiante extends Persona implements Runnable {
    
    private String lenguajeAprendizaje;
    private Zona zona; 
    private HojaDeEntregas hojaCompartida;
    
    public Estudiante(String nombre, String lenguajeAprendizaje, HojaDeEntregas hoja) {
        super(nombre); 
        this.lenguajeAprendizaje = lenguajeAprendizaje;
        this.zona = new Zona(); 
        this.hojaCompartida = hoja;
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
        hojaCompartida.registrarEntrega(getNombre());
    }
  
    public void realizarExamen() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getNombre() + " está escribiendo la pregunta " + i + "...");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                System.out.println("Error.");
            }
        }
        System.out.println( getNombre() + " se levanta de su asiento!");
    }
}