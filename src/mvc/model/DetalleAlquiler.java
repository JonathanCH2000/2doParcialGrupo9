package mvc.model;

public class DetalleAlquiler {

    private int cantidad;
    private double valorDiarioAplicado;
    private Equipo equipo;

    public DetalleAlquiler(Equipo equipo, int cantidad, double valorDiarioAplicado) {
        this.equipo = equipo;
        this.cantidad = cantidad;
        this.valorDiarioAplicado = valorDiarioAplicado; // precio unitario del producto x dia
    }

    public double calcularSubtotal(int cantidadDias) {
        return cantidad * valorDiarioAplicado * cantidadDias;
    }

    // Getters
    public int getCantidad() { return cantidad; }
    public double getValorDiarioAplicado() { return valorDiarioAplicado; }
    public Equipo getEquipo() { return equipo; }
}
