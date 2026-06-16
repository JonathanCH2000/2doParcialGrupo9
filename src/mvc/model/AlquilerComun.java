package mvc.model;

import java.time.LocalDate;

public class AlquilerComun extends Alquiler {

    // Sin recargo adicional: usa el valor diario base de cada equipo
    public AlquilerComun(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double porcentajeRecargo) {
        super(cliente, fechaEvento, cantidadDias, porcentajeRecargo);
    }

    @Override
    public double obtenerPorcentajeRecargo() {
        return 0;
    }
}
