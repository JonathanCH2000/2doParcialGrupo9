package mvc.model;

import java.time.LocalDate;

public class AlquilerCorporativo extends Alquiler {
    // Extiende de Alquiler. Tiene un recargo del 10%

    private double recargoCorporativo; // recargoCorporativo = 10.0 (seteado en AlquilerController)

    public AlquilerCorporativo(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double recargoCorporativo) {
        super(cliente, fechaEvento, cantidadDias, recargoCorporativo);
        this.recargoCorporativo = recargoCorporativo;
    }

    @Override
    public double obtenerPorcentajeRecargo() {
        return recargoCorporativo;
    }
}
