package album;

public class Pais {

    private String codigo;
    private String nombre;
    private int totalCartas;
    private String colores;
    private int pagina;
    private int filaPagina;

    public Pais(String codigo, String nombre, int totalCartas,
            String colores, int pagina, int filaPagina) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.totalCartas = totalCartas;
        this.colores = colores;
        this.pagina = pagina;
        this.filaPagina = filaPagina;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotalCartas() {
        return totalCartas;
    }

    public String getColores() {
        return colores;
    }

    public int getPagina() {
        return pagina;
    }

    public int getFilaPagina() {
        return filaPagina;
    }

    @Override
    public String toString() {
        return String.format("%-3s | %-12s | %d cartas | Pag.%d Fila%d | %s",
                codigo, nombre, totalCartas, pagina, filaPagina, colores);
    }
}
