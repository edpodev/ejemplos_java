package mallabase;

/**
 *
 * @author danielporon
 */
public class MallaBase {

    private Sector[][] sectores;
    private int filas;
    private int columnas;

    public MallaBase(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.sectores = new Sector[filas][columnas];
        construir();
    }

    private void construir() {

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                sectores[f][c] = new Sector(f, c);
            }
        }

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (f > 0) {
                    sectores[f][c].norte = sectores[f - 1][c];
                }
                if (f < filas - 1) {
                    sectores[f][c].sur = sectores[f + 1][c];
                }
                if (c < columnas - 1) {
                    sectores[f][c].este = sectores[f][c + 1];
                }
                if (c > 0) {
                    sectores[f][c].oeste = sectores[f][c - 1];
                }
            }
        }
    }

    public Sector getSector(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return null;
        }
        return sectores[fila][columna];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void imprimir() {
        for (int f = 0; f < filas; f++) {
            System.out.print("  ");
            for (int c = 0; c < columnas; c++) {
                System.out.printf("%-10s", sectores[f][c].toString());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        MallaBase malla = new MallaBase(3, 3);
        malla.imprimir();

        Sector centro = malla.getSector(1, 1);
        System.out.println("\nVecinos de " + centro + ":");
        System.out.println("  norte: " + centro.norte);
        System.out.println("  sur:   " + centro.sur);
        System.out.println("  este:  " + centro.este);
        System.out.println("  oeste: " + centro.oeste);

        Sector arriba = malla.getSector(0, 1);
        Sector lejos = malla.getSector(2, 2);
        System.out.println("\nesVecino(" + arriba + "): " + centro.esVecino(arriba));
        System.out.println("esVecino(" + lejos + "): " + centro.esVecino(lejos));
    }

}
