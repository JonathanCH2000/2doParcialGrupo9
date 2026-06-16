package mvc.model;

import java.time.LocalDate;

public class AlquilerCorporativo extends Alquiler {

    private double recargoCorporativo;

    public AlquilerCorporativo(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double recargoCorporativo) {
        super(cliente, fechaEvento, cantidadDias, recargoCorporativo);
        this.recargoCorporativo = recargoCorporativo;
    }

    @Override
    public double obtenerPorcentajeRecargo() {
        return recargoCorporativo;
    }
}
