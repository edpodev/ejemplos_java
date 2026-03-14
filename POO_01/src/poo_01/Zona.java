package poo_01;

public class Zona {
    private double puntosTareas;
    private double puntosParciales;

    public Zona() {
        this.puntosTareas = 0.0;
        this.puntosParciales = 0.0;
    }

    public double getPuntosTareas() {
        return puntosTareas;
    }

    public void setPuntosTareas(double puntosTareas) {
        this.puntosTareas = puntosTareas;
    }

    public double getPuntosParciales() {
        return puntosParciales;
    }

    public void setPuntosParciales(double puntosParciales) {
        this.puntosParciales = puntosParciales;
    }

    public double getTotalZona() {
        return puntosTareas + puntosParciales;
    }
}