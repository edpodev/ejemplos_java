package poo_01;

public class HojaDeEntregas {
    private int examenesEntregados = 0;
    
    
    public synchronized void registrarEntrega(String nombreEstudiante) {
        System.out.println("[ESCRITORIO] " + nombreEstudiante + " tomó el lapicero y está firmando la hoja...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        examenesEntregados++;
        System.out.println("[ESCRITORIO] " + nombreEstudiante + " entregó. Total de exámenes en la mesa: " + examenesEntregados);
    }
}