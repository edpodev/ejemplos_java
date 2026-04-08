package album;

public class CartaUsuario {

    private String usuario;
    private String noCarta;
    private int cantidad;
    private boolean pegada;
    private int pagina;
    private int fila;
    private int columna;

    public CartaUsuario(String usuario, String noCarta, int cantidad,
            boolean pegada, int pagina, int fila, int columna) {
        this.usuario = usuario;
        this.noCarta = noCarta;
        this.cantidad = cantidad;
        this.pegada = pegada;
        this.pagina = pagina;
        this.fila = fila;
        this.columna = columna;
    }

    public CartaUsuario(String usuario, String noCarta) {
        this(usuario, noCarta, 1, false, -1, -1, -1);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNoCarta() {
        return noCarta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isPegada() {
        return pegada;
    }

    public int getPagina() {
        return pagina;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void incrementar(int n) {
        cantidad += n;
    }

    public void pegar(int pagina, int fila, int columna) {
        this.pegada = true;
        this.pagina = pagina;
        this.fila = fila;
        this.columna = columna;
    }

    public String toLinea() {
        return usuario + "|" + noCarta + "|" + cantidad + "|"
                + (pegada ? 1 : 0) + "|" + pagina + "|" + fila + "|" + columna;
    }

    @Override
    public String toString() {
        String estado = pegada
                ? String.format("Pegada[pag=%d fil=%d col=%d]", pagina, fila, columna)
                : "En inventario";
        return String.format("Usuario: %-12s | Carta: %-8s | x%d | %s",
                usuario, noCarta, cantidad, estado);
    }
}
