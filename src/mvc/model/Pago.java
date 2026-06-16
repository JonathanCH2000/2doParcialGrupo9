package mvc.model;

import java.time.LocalDate;

public class Pago {

    private int id;
    private LocalDate fecha;
    private double importe;
    private MedioPago medioPago;
    private EstadoPago estado;
    private String usuarioRegistro;

    public Pago(int id, LocalDate fecha, double importe, MedioPago medioPago, String usuarioRegistro) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.medioPago = medioPago;
        this.usuarioRegistro = usuarioRegistro;
        this.estado = EstadoPago.REGISTRADO;
    }

    public void confirmar() { this.estado = EstadoPago.CONFIRMADO; }
    public void anular() { this.estado = EstadoPago.ANULADO; }

    public int getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public double getImporte() { return importe; }
    public MedioPago getMedioPago() { return medioPago; }
    public EstadoPago getEstado() { return estado; }
    public String getUsuarioRegistro() { return usuarioRegistro; }
}
