package poo_01;

public class POO_01 {

    public static void main(String[] args) {
      
        Estudiante e1 = new Estudiante("Ana", "Java");
        Estudiante e2 = new Estudiante("Carlos", "Python");

        Curso curso = new Curso("Programación I", 10);
        curso.matricularEstudiante(e1);
        curso.matricularEstudiante(e2);

        e1.getZona().setPuntosTareas(15.5);
        e1.getZona().setPuntosParciales(30.0);
        
        e2.getZona().setPuntosTareas(20.0);
        e2.getZona().setPuntosParciales(25.0);

        curso.iniciarClase();
        curso.mostrarZonas();
    }
}