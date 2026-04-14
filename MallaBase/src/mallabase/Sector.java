package mallabase;

/**
 *
 * @author danielporon
 */
public class Sector {

    private int fila;
    private int columna;

    public Sector norte;
    public Sector sur;
    public Sector este;
    public Sector oeste;

    public Sector(int fila, int columna) {
        this.fila    = fila;
        this.columna = columna;
        this.norte   = null;
        this.sur     = null;
        this.este    = null;
        this.oeste   = null;
    }

    public boolean esVecino(Sector otro) {
        return otro != null
                && (this.norte == otro
                ||  this.sur   == otro
                ||  this.este  == otro
                ||  this.oeste == otro);
    }

    public int getFila()    { return fila; }
    public int getColumna() { return columna; }

    @Override
    public String toString() {
        return "(" + fila + "," + columna + ")";
    }
}