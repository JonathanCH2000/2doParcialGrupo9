package mvc.model;

import java.time.LocalDate;

public class HistorialCambioEstado {

    private LocalDate fechaCambio;
    private String estadoAnterior;
    private String estadoNuevo;
    private TipoEntidad tipoEntidad;
    private String referencia;
    private String usuarioResponsable;

    public HistorialCambioEstado(LocalDate fechaCambio, String estadoAnterior, String estadoNuevo,
                                  TipoEntidad tipoEntidad, String referencia, String usuarioResponsable) {
        this.fechaCambio = fechaCambio;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.tipoEntidad = tipoEntidad;
        this.referencia = referencia;
        this.usuarioResponsable = usuarioResponsable;
    }

    public LocalDate getFechaCambio() { return fechaCambio; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public TipoEntidad getTipoEntidad() { return tipoEntidad; }
    public String getReferencia() { return referencia; }
    public String getUsuarioResponsable() { return usuarioResponsable; }
}
