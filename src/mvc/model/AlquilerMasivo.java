package mvc.model;

import java.time.LocalDate;

public class AlquilerMasivo extends Alquiler {

    private double recargoMasivo;

    public AlquilerMasivo(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double recargoMasivo) {
        super(cliente, fechaEvento, cantidadDias, recargoMasivo);
        this.recargoMasivo = recargoMasivo;
    }

    @Override
    public double obtenerPorcentajeRecargo() {
        return recargoMasivo;
    }
}
