package poo_01;

public class POO_01 {

    public static void main(String[] args) {
        
        HojaDeEntregas hojaUnica = new HojaDeEntregas();
        
        Estudiante e1 = new Estudiante("Ana", "Java", hojaUnica);
        Estudiante e2 = new Estudiante("Carlos", "Python", hojaUnica);
        Estudiante e3 = new Estudiante("Lucía", "C++", hojaUnica);

        Curso curso = new Curso("Programación I", 10);
        curso.matricularEstudiante(e1);
        curso.matricularEstudiante(e2);
        curso.matricularEstudiante(e3);

        System.out.println("--- ¡INICIA EL EXAMEN FINAL! ---");

        curso.realizarExamenSimultaneo(); 
    }
}