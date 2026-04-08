package pilaconcolasauxiliares;

class Cola {
    Nodo frente, fondo;
    int tamanio, capacidad;

    Cola(int capacidad) {
        this.capacidad = capacidad;
    }

    boolean estaVacia() { return tamanio == 0; }
    boolean estaLlena() { return tamanio == capacidad; }

    void agregar(int valor) {
        Nodo nuevo = new Nodo(valor);
        if (estaVacia())
            frente = fondo = nuevo;
        else {
            fondo.siguiente = nuevo;
            fondo = nuevo;
        }
        tamanio++;
    }

    int quitar() {
        int valor = frente.dato;
        frente = frente.siguiente;
        if (frente == null) fondo = null;
        tamanio--;
        return valor;
    }
}