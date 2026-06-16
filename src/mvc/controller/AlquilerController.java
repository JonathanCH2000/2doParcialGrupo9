package mvc.controller;

import dto.SolicitudAlquilerDTO;
import mvc.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exceptions.AlquilerNoValidoException;
import exceptions.ClienteNoEncontradoException;


public class AlquilerController {

    private static AlquilerController instancia;

    private List<Alquiler> alquileres;
    private List<HistorialCambioEstado> historiales;

    // Porcentajes de recargo parametrizables
    private double recargoCorporativo = 10.0;
    private double recargoMasivo = 20.0;

    private int contadorId = 1;

    private AlquilerController() {
        this.alquileres = new ArrayList<>();
        this.historiales = new ArrayList<>();
    }

    public static synchronized AlquilerController getInstance() {
        if (instancia == null) {
            instancia = new AlquilerController();
        }
        return instancia;
    }

    // UC3 - Solicitar alquiler: busca cliente, crea alquiler según tipo, valida stock y registra
    public Alquiler solicitarAlquiler(SolicitudAlquilerDTO dto, String usuario) throws ClienteNoEncontradoException, AlquilerNoValidoException{
        ClienteController clienteController = ClienteController.getInstance();
        EquipoController equipoController = EquipoController.getInstance();

        // loop: recorrer clientes para encontrar cliente
        Cliente cliente = clienteController.buscarPorDniCuit(dto.getDniCuit());
        if (cliente == null) throw new ClienteNoEncontradoException("Cliente no encontrado");

        // Crear alquiler según tipo
        Alquiler alquiler;
        switch (dto.getTipoAlquiler()) {
            case "COMUN":
                alquiler = new AlquilerComun(cliente, dto.getFechaEvento(), dto.getCantidadDias(), 0);
                break;
            case "CORPORATIVO":
                alquiler = new AlquilerCorporativo(cliente, dto.getFechaEvento(), dto.getCantidadDias(), recargoCorporativo);
                break;
            case "MASIVO":
                alquiler = new AlquilerMasivo(cliente, dto.getFechaEvento(), dto.getCantidadDias(), recargoMasivo);
                break;
            default:
                throw new AlquilerNoValidoException("Tipo de alquiler no válido");
        }
        alquiler.setId(contadorId++);

        // loop: recorrer items solicitados y encontrar cada equipo
        for (Map.Entry<String, Integer> item : dto.getItemsSolicitados().entrySet()) {
            String codigoEquipo = item.getKey();
            int cantidad = item.getValue();

            Equipo equipo = equipoController.buscarPorCodigo(codigoEquipo);
            if (equipo != null && equipo.estaDisponible(cantidad)) {
                DetalleAlquiler detalle = new DetalleAlquiler(equipo, cantidad, equipo.getValorDiario());
                alquiler.agregarDetalle(detalle);
                equipo.reservarStock(cantidad);
            }
        }

        alquileres.add(alquiler);

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(), "-", "INGRESADO", TipoEntidad.ALQUILER,
                String.valueOf(alquiler.getId()), usuario);
        historiales.add(historial);

        return alquiler;
    }

    // UC4 - Finalizar alquiler: libera stock, calcula importes y registra historial
    public double finalizarAlquiler(int idAlquiler, String usuario) throws AlquilerNoEncontradoException{
        Alquiler alquiler = buscarPorId(idAlquiler);
        if (alquiler == null) throw new AlquilerNoEncontradoException("Alquiler no encontrado");

        Cliente cliente = alquiler.getCliente();

        // loop: recorrer detalles para liberar stock
        for (DetalleAlquiler detalle : alquiler.getDetalles()) {
            detalle.getEquipo().liberarStock(detalle.getCantidad());
        }

        double porcentajeDescuento = cliente.obtenerDescuentoVigente(alquiler.getFechaEvento());
        double subtotal = alquiler.calcularSubtotal();
        double importeTotal = alquiler.calcularImporteTotal(porcentajeDescuento);
        double importePendiente = alquiler.calcularImportePendiente(porcentajeDescuento);

        alquiler.finalizar();

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(), "ENTREGADO", "FINALIZADO", TipoEntidad.ALQUILER,
                String.valueOf(idAlquiler), usuario);
        historiales.add(historial);

        return importePendiente;
    }

    // Consulta total recaudado en un período
    public double totalRecaudado(LocalDate fechaDesde, LocalDate fechaHasta) {
        double total = 0;
        for (Alquiler a : alquileres) {
            if (a.getEstado() == EstadoAlquiler.FINALIZADO
                    && !a.getFechaEvento().isBefore(fechaDesde)
                    && !a.getFechaEvento().isAfter(fechaHasta)) {
                total += a.getImporteTotal();
            }
        }
        return total;
    }

    // Consulta alquileres confirmados de un cliente
    public List<Alquiler> obtenerAlquileresConfirmados(String dniCuit) {
        List<Alquiler> resultado = new ArrayList<>();
        for (Alquiler a : alquileres) {
            if (a.getCliente().getDniCuit().equals(dniCuit)
                    && a.getEstado() == EstadoAlquiler.CONFIRMADO) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public Alquiler buscarPorId(int id) {
        for (Alquiler a : alquileres) {
            if (a.coincideId(id)) return a;
        }
        return null;
    }

    public double getRecargoCorporativo() { return recargoCorporativo; }
    public void setRecargoCorporativo(double recargoCorporativo) { this.recargoCorporativo = recargoCorporativo; }
    public double getRecargoMasivo() { return recargoMasivo; }
    public void setRecargoMasivo(double recargoMasivo) { this.recargoMasivo = recargoMasivo; }
    public List<Alquiler> getAlquileres() { return alquileres; }
}
