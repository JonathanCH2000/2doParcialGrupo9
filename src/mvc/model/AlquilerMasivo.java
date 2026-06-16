package mvc.model;

import java.time.LocalDate;

public class AlquilerMasivo extends Alquiler {
    // Extiende de Alquiler. Tiene un recargo del 20%

    private double recargoMasivo; // recargoMasivo = 20.0 (seteado en AlquilerController)

    public AlquilerMasivo(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double recargoMasivo) {
        super(cliente, fechaEvento, cantidadDias, recargoMasivo);
        this.recargoMasivo = recargoMasivo;
    }

    @Override
    public double obtenerPorcentajeRecargo() {
        return recargoMasivo;
    }
}
