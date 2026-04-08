package album;

public class MallaOrtogonal {

    private NodoMatriz origen;
    private int filas;
    private int columnas;
    private int numeroPagina;

    private Pais[] paisesFila;

    public MallaOrtogonal(int filas, int columnas, int numeroPagina) {
        this.filas = filas;
        this.columnas = columnas;
        this.numeroPagina = numeroPagina;
        this.paisesFila = new Pais[filas];
        construir();
    }

    private void construir() {
        NodoMatriz[][] temp = new NodoMatriz[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                temp[f][c] = new NodoMatriz(f, c);
            }
        }
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (f > 0) {
                    temp[f][c].arriba = temp[f - 1][c];
                }
                if (f < filas - 1) {
                    temp[f][c].abajo = temp[f + 1][c];
                }
                if (c > 0) {
                    temp[f][c].izquierda = temp[f][c - 1];
                }
                if (c < columnas - 1) {
                    temp[f][c].derecha = temp[f][c + 1];
                }
            }
        }
        origen = temp[0][0];
    }

    public void asignarPais(int fila, Pais pais) {
        if (fila >= 0 && fila < filas) {
            paisesFila[fila] = pais;
        }
    }

    public Pais getPaisFila(int fila) {
        return (fila >= 0 && fila < filas) ? paisesFila[fila] : null;
    }

    public NodoMatriz getNodo(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return null;
        }
        NodoMatriz nodo = origen;
        for (int f = 0; f < fila; f++) {
            nodo = nodo.abajo;
        }
        for (int c = 0; c < columna; c++) {
            nodo = nodo.derecha;
        }
        return nodo;
    }

    public boolean pegarCarta(int fila, int columna, Carta carta) {
        NodoMatriz nodo = getNodo(fila, columna);
        if (nodo == null || !nodo.estaVacia()) {
            return false;
        }
        nodo.dato = carta;
        return true;
    }

    public NodoMatriz buscarPorCodigo(String noCarta) {
        NodoMatriz fila = origen;
        while (fila != null) {
            NodoMatriz nodo = fila;
            while (nodo != null) {
                if (nodo.dato != null && nodo.dato.getNoCarta().equals(noCarta)) {
                    return nodo;
                }
                nodo = nodo.derecha;
            }
            fila = fila.abajo;
        }
        return null;
    }

    public NodoMatriz primerVacioEnFila(int fila) {
        NodoMatriz nodo = getNodo(fila, 0);
        while (nodo != null) {
            if (nodo.estaVacia()) {
                return nodo;
            }
            nodo = nodo.derecha;
        }
        return null;
    }

    public boolean filaCompleta(int fila) {
        NodoMatriz nodo = getNodo(fila, 0);
        while (nodo != null) {
            if (nodo.estaVacia()) {
                return false;
            }
            nodo = nodo.derecha;
        }
        return true;
    }

    public int contarPegadas() {
        int count = 0;
        NodoMatriz filaNode = origen;
        while (filaNode != null) {
            NodoMatriz nodo = filaNode;
            while (nodo != null) {
                if (!nodo.estaVacia()) {
                    count++;
                }
                nodo = nodo.derecha;
            }
            filaNode = filaNode.abajo;
        }
        return count;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public NodoMatriz getOrigen() {
        return origen;
    }

    public void imprimir() {
        System.out.println("  Pagina " + numeroPagina
                + " (" + (columnas * filas) + " celdas, "
                + contarPegadas() + " pegadas)");
        System.out.println("  " + "=".repeat(columnas * 12 + 2));

        NodoMatriz filaNode = origen;
        int f = 0;
        while (filaNode != null) {
            String nombrePais = (paisesFila[f] != null)
                    ? paisesFila[f].getNombre() : "???";
            System.out.printf("  %-12s |", nombrePais);
            NodoMatriz nodo = filaNode;
            while (nodo != null) {
                if (nodo.estaVacia()) {
                    System.out.printf(" %10s |", "[ vacia ]");
                } else {
                    System.out.printf(" %-10s |", nodo.dato.getNoCarta());
                }
                nodo = nodo.derecha;
            }
            System.out.println();
            System.out.printf("  %-12s |", "");
            nodo = filaNode;
            while (nodo != null) {
                if (!nodo.estaVacia()) {
                    String nombre = nodo.dato.getNombreJugador();
                    if (nombre.length() > 10) {
                        nombre = nombre.substring(0, 9) + ".";
                    }
                    System.out.printf(" %-10s |", nombre);
                } else {
                    System.out.printf(" %-10s |", "");
                }
                nodo = nodo.derecha;
            }
            System.out.println();
            System.out.println("  " + "-".repeat(columnas * 12 + 14));

            filaNode = filaNode.abajo;
            f++;
        }
    }
}
