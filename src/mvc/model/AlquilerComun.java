package mvc.model;

import java.time.LocalDate;

public class AlquilerComun extends Alquiler {

    // Extiende de Alquiler. No tiene recargo. PorcentajeRecargo = 0
    public AlquilerComun(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double porcentajeRecargo) {
        super(cliente, fechaEvento, cantidadDias, porcentajeRecargo);
    }

    @Override
    public double obtenerPorcentajeRecargo() {
        return 0;
    }
}
