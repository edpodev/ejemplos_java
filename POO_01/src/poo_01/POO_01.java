package poo_01;

public class POO_01 {

    public static void main(String[] args) {
        
   
        Estudiante e1 = new Estudiante("Ana", "Java");
        Estudiante e2 = new Estudiante("Carlos", "Python");
        Estudiante e3 = new Estudiante("Lucía", "C++");
        Estudiante e4 = new Estudiante("Mateo", "JavaScript");
        Estudiante e5 = new Estudiante("Elena", "C#");
        Estudiante e6 = new Estudiante("Diego", "Ruby");


        Curso curso = new Curso("Programación I", 10);
        

        curso.matricularEstudiante(e1);
        curso.matricularEstudiante(e2);
        curso.matricularEstudiante(e3);
        curso.matricularEstudiante(e4);
        curso.matricularEstudiante(e5);
        curso.matricularEstudiante(e6);


        curso.realizarExamenSecuencial();
    }
}