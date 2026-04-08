package album;

public class NodoMatriz {

    Carta dato;
    NodoMatriz arriba;
    NodoMatriz abajo;
    NodoMatriz izquierda;
    NodoMatriz derecha;

    int fila;
    int columna;

    public NodoMatriz(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.dato = null;
        this.arriba = null;
        this.abajo = null;
        this.izquierda = null;
        this.derecha = null;
    }

    public boolean estaVacia() {
        return dato == null;
    }

    @Override
    public String toString() {
        return dato != null ? dato.getNoCarta() : "vaco";
    }
}
