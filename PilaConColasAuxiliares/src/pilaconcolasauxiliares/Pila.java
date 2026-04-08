package pilaconcolasauxiliares;

class Pila {
    Cola colaA, colaB;
    int capacidad;

    Pila(int capacidad) {
        this.capacidad = capacidad;
        colaA = new Cola(capacidad);
        colaB = new Cola(capacidad);
    }

    boolean pilaVacia() { return colaA.estaVacia(); }
    boolean pilaLlena() { return colaA.tamanio == capacidad; }

    void push(int valor) {
        if (pilaLlena()) {
            System.out.println("Pila llena");
            return;
        }
        colaB.agregar(valor);
        while (!colaA.estaVacia())
            colaB.agregar(colaA.quitar());

        Cola temp = colaA;
        colaA = colaB;
        colaB = temp;
    }

    int pop() {
        if (pilaVacia()) {
            System.out.println("vacia");
            return -1;
        }
        return colaA.quitar();
    }

    void mostrar() {
        Nodo actual = colaA.frente;
        System.out.print("Pila: ");
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }
}