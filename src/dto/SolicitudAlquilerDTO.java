package dto;

import java.time.LocalDate;
import java.util.Map;

// DTO para solicitar alquiler (múltiples parámetros + mapa de items)
public class SolicitudAlquilerDTO {

    private String dniCuit;
    private Map<String, Integer> itemsSolicitados; // codigoEquipo -> cantidad
    private LocalDate fechaEvento;
    private int cantidadDias;
    private String tipoAlquiler; // "COMUN", "CORPORATIVO", "MASIVO"

    public SolicitudAlquilerDTO(String dniCuit, Map<String, Integer> itemsSolicitados,
                                 LocalDate fechaEvento, int cantidadDias, String tipoAlquiler) {
        this.dniCuit = dniCuit;
        this.itemsSolicitados = itemsSolicitados;
        this.fechaEvento = fechaEvento;
        this.cantidadDias = cantidadDias;
        this.tipoAlquiler = tipoAlquiler;
    }

    public String getDniCuit() { return dniCuit; }
    public Map<String, Integer> getItemsSolicitados() { return itemsSolicitados; }
    public LocalDate getFechaEvento() { return fechaEvento; }
    public int getCantidadDias() { return cantidadDias; }
    public String getTipoAlquiler() { return tipoAlquiler; }
}
