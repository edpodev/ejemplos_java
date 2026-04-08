package album;

public class ListaSimple<T> {

    private NodoSimple<T> cabeza;
    private int           tamanio;

    public ListaSimple() {
        cabeza   = null;
        tamanio  = 0;
    }

    public void insertarAlFinal(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoSimple<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    public void insertarAlInicio(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);
        nuevo.siguiente = cabeza;
        cabeza          = nuevo;
        tamanio++;
    }

    public boolean estaVacia() { return cabeza == null; }
    public int     getTamanio() { return tamanio; }
    public NodoSimple<T> getCabeza() { return cabeza; }

    public T get(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Indice " + indice + " fuera de rango [0," + (tamanio-1) + "]");
        }
        NodoSimple<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    public T eliminarPrimero() {
        if (estaVacia()) {
            throw new RuntimeException("Lista vacia — no se puede eliminar");
        }
        T dato = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamanio--;
        return dato;
    }

    public void imprimir() {
        NodoSimple<T> actual = cabeza;
        int i = 1;
        while (actual != null) {
            System.out.println("  " + i + ". " + actual.dato);
            actual = actual.siguiente;
            i++;
        }
    }
}