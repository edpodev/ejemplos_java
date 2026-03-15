package poo_01;

public class Curso {
    private String nombre;
    private Estudiante[] estudiantes;
    private int inscritos;
    private Temario temario;

    public Curso(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.estudiantes = new Estudiante[capacidadMaxima];
        this.inscritos = 0;
        this.temario = new Temario("Introducción a la POO, Agregación y Composición");
    }

    public void matricularEstudiante(Estudiante estudiante) {
        if (inscritos < estudiantes.length) {
            estudiantes[inscritos] = estudiante;
            inscritos++;
        } else {
            System.out.println("El curso está lleno.");
        }
    }

    public void mostrarZonas() {
        System.out.println("\n--- Reporte de Zonas: " + nombre + " ---");
        for (int i = 0; i < inscritos; i++) {
            System.out.println("Estudiante: " + estudiantes[i].getNombre() + 
                               " | Zona Total: " + estudiantes[i].getZona().getTotalZona());
        }
    }

    public void iniciarClase() {
        System.out.println("--- Inicio del curso: " + nombre + " ---");
        temario.mostrarTemas();
        System.out.println("\n--- Presentación del grupo ---");
        for (int i = 0; i < inscritos; i++) {
            estudiantes[i].presentarse();
        }
    }
    
    public void realizarExamenSimultaneo() {
        System.out.println("\n--- ¡Empieza el examen! Todos programan a la vez ---");
        for (int i = 0; i < inscritos; i++) {
            Thread hiloEstudiante = new Thread(estudiantes[i]);
            hiloEstudiante.start(); 
        }
    }
    
    public void realizarExamenSecuencial() {
        System.out.println("\n--- 🚶‍♂️ INICIA EL EXAMEN SECUENCIAL (Uno por uno) ---");
        for (int i = 0; i < inscritos; i++) {
            System.out.println(">> Turno de " + estudiantes[i].getNombre() + " <<");
            estudiantes[i].realizarExamen(); 
        }
        System.out.println("--- Todos los exámenes secuenciales han terminado ---\n");
    }
}