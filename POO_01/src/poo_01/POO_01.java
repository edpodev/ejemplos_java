package poo_01;

public class POO_01 {

    public static void main(String[] args) {
       
        Estudiante estudiante1 = new Estudiante("Ana", "Java");
        Estudiante estudiante2 = new Estudiante("Carlos", "Java");


        Curso cursoJava = new Curso("Programación Avanzada", 25);
     
        // inscritos 0
        
        
        cursoJava.matricularEstudiante(estudiante1);
        cursoJava.matricularEstudiante(estudiante2);
        // inscritos 2
        cursoJava.reasignarNombre(0, "Juan" );
       

        cursoJava.iniciarClase();
        
         System.out.println(estudiante1.getNombre());
    }
    
}
