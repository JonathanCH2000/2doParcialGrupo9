package mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String dniCuit;
    private String nombreRazonSocial;
    private String telefono;
    private String email;
    private String direccion;
    private EstadoCliente estado;
    private double creditoAFavor;
    private List<DescuentoCliente> descuentos;

    public Cliente(String dniCuit, String nombreRazonSocial, String telefono,
                   String email, String direccion) {
        this.dniCuit = dniCuit;
        this.nombreRazonSocial = nombreRazonSocial;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.estado = EstadoCliente.INACTIVO;
        this.creditoAFavor = 0;
        this.descuentos = new ArrayList<>();
    }

    public void activar() { this.estado = EstadoCliente.ACTIVO; }
    public void inactivar() { this.estado = EstadoCliente.INACTIVO; }
    public void agregarCredito(double importe) { this.creditoAFavor += importe; }

    public double obtenerDescuentoVigente(java.time.LocalDate fecha) {
        for (DescuentoCliente d : descuentos) {
            if (d.estaVigente(fecha)) return d.getPorcentaje();
        }
        return 0;
    }

    public boolean estaActivo() { return estado == EstadoCliente.ACTIVO; }

    public String getDniCuit() { return dniCuit; }
    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }
    public EstadoCliente getEstado() { return estado; }
    public double getCreditoAFavor() { return creditoAFavor; }
    public List<DescuentoCliente> getDescuentos() { return descuentos; }
}
