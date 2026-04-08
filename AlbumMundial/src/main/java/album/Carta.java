package album;

public class Carta {

    private String noCarta;
    private String codigoPais;
    private String nombreJugador;
    private int numeroCamisola;
    private String posicion;
    private String rareza;

    public Carta(String noCarta, String codigoPais, String nombreJugador,
            int numeroCamisola, String posicion, String rareza) {
        this.noCarta = noCarta;
        this.codigoPais = codigoPais;
        this.nombreJugador = nombreJugador;
        this.numeroCamisola = numeroCamisola;
        this.posicion = posicion;
        this.rareza = rareza;
    }

    public String getNoCarta() {
        return noCarta;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getNumeroCamisola() {
        return numeroCamisola;
    }

    public String getPosicion() {
        return posicion;
    }

    public String getRareza() {
        return rareza;
    }

    public int getPesoRareza() {
        switch (rareza) {
            case "Comun":
                return 50;
            case "Poco Comun":
                return 25;
            case "Rara":
                return 15;
            case "Ultra Rara":
                return 8;
            case "Legendaria":
                return 2;
            default:
                return 10;
        }
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-2s | %-25s | #%-2d | %-15s | %s",
                noCarta, codigoPais, nombreJugador,
                numeroCamisola, posicion, rareza);
    }
}
