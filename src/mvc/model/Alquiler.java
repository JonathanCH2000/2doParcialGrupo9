package mvc.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class Alquiler {
    // Clase abstracta, uso de herencia. Alquiler es abstracta porque nunca vas a tener un alquiler genérico
    // Siempre vas a tener un alquiler de alguno de sus tipos: AlquilerComun, AlquilerCorporativo o AlquilerMasivo

    private int id;
    private LocalDate fechaSolicitud;
    private LocalDate fechaEvento;
    private int cantidadDias;
    private EstadoAlquiler estado;
    private double porcentajeRecargoAplicado;
    private double seniaAbonada;
    private List<DetalleAlquiler> detalles;
    private List<Pago> pagos;
    private double importeTotal;
    private double importePendiente;
    private Cliente cliente;

    public Alquiler(Cliente cliente, LocalDate fechaEvento, int cantidadDias, double porcentajeRecargoAplicado) {
        this.cliente = cliente;
        this.fechaEvento = fechaEvento;
        this.cantidadDias = cantidadDias;
        this.porcentajeRecargoAplicado = porcentajeRecargoAplicado;
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoAlquiler.INGRESADO;
        this.seniaAbonada = 0;
        this.importeTotal = 0;
        this.importePendiente = 0;
        this.detalles = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public abstract double obtenerPorcentajeRecargo(); // metodo que implementa cada sub-clase

    public void agregarDetalle(DetalleAlquiler detalle) {
        detalles.add(detalle);
    }

    public void registrarPago(Pago pago) {
        pagos.add(pago);
    }

    public void registrarSenia(double importe) {
        this.seniaAbonada = importe;
    }

    public void confirmar() {
        this.estado = EstadoAlquiler.CONFIRMADO;
    }

    public void cancelar() {
        this.estado = EstadoAlquiler.CANCELADO;
    }

    public void pasarAEnPreparacion() {
        this.estado = EstadoAlquiler.EN_PREPARACION;
    }

    public void entregar() {
        this.estado = EstadoAlquiler.ENTREGADO;
    }

    public void finalizar() {
        this.estado = EstadoAlquiler.FINALIZADO;
    }

    public boolean coincideId(int idAlquiler) {
        return this.id == idAlquiler;
    }


    public double calcularSubtotal() {
        // recorre el DetalleAlquiler por cada item y suma el subtotal
        double subtotal = 0;
        for (DetalleAlquiler d : detalles) {
            subtotal += d.calcularSubtotal(cantidadDias);
        }
        return subtotal;
    }

    public double calcularDescuentoCliente(double porcentajeDescuento) {
        return calcularSubtotal() * porcentajeDescuento / 100;
    }

    public double calcularRecargoAlquiler() {
        return calcularSubtotal() * porcentajeRecargoAplicado / 100;
    }

    public double calcularImporteTotal(double porcentajeDescuento) {
        // Segun los metodos que armamos anteriormente, ahora calculamos el total
        double subtotal = calcularSubtotal();
        double recargo = calcularRecargoAlquiler();
        double descuento = calcularDescuentoCliente(porcentajeDescuento);
        this.importeTotal = subtotal + recargo - descuento;
        return importeTotal;
    }

    public double calcularImportePendiente(double porcentajeDescuento) {
        this.importePendiente = calcularImporteTotal(porcentajeDescuento) - seniaAbonada;
        return importePendiente;
    }

    public long calcularHorasAnticipacion(LocalDate fechaCancelacion) {
        // Calcula cuantas horas hay entre la fecha de cancelacion y la del evento
        // Si se superan las 72hs la seña se devuelve como credito y si no, se pierde
        return ChronoUnit.HOURS.between(fechaCancelacion.atStartOfDay(), fechaEvento.atStartOfDay());
    }


    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public LocalDate getFechaEvento() { return fechaEvento; }
    public int getCantidadDias() { return cantidadDias; }
    public EstadoAlquiler getEstado() { return estado; }
    public double getPorcentajeRecargoAplicado() { return porcentajeRecargoAplicado; }
    public double getSeniaAbonada() { return seniaAbonada; }
    public List<DetalleAlquiler> getDetalles() { return detalles; }
    public List<Pago> getPagos() { return pagos; }
    public double getImporteTotal() { return importeTotal; }
    public double getImportePendiente() { return importePendiente; }
    public Cliente getCliente() { return cliente; }
}
