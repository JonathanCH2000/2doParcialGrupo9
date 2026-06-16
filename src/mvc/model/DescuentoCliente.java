package mvc.model;

import java.time.LocalDate;

public class DescuentoCliente {

    private double porcentaje;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public DescuentoCliente(double porcentaje, LocalDate fechaDesde, LocalDate fechaHasta) {
        this.porcentaje = porcentaje;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public boolean estaVigente(LocalDate fecha) {
        return !fecha.isBefore(fechaDesde) && !fecha.isAfter(fechaHasta);
    }

    public double getPorcentaje() { return porcentaje; }
    public LocalDate getFechaDesde() { return fechaDesde; }
    public LocalDate getFechaHasta() { return fechaHasta; }
}
