package mvc.controller;

import dto.SolicitudAlquilerDTO;
import mvc.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlquilerController {

    private static AlquilerController instancia;

    private List<Alquiler> alquileres;
    private List<HistorialCambioEstado> historiales;

    // Porcentajes de recargo
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

    // Solicitar alquiler: busca cliente, crea alquiler según tipo
    public Alquiler solicitarAlquiler(SolicitudAlquilerDTO dto, String usuario) {

        ClienteController clienteController = ClienteController.getInstance();
        EquipoController equipoController = EquipoController.getInstance();

        // Buscar cliente
        Cliente cliente = clienteController.buscarPorDniCuit(dto.getDniCuit());

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        // Crear alquiler según tipo
        Alquiler alquiler;

        switch (dto.getTipoAlquiler()) {

            case "COMUN":
                alquiler = new AlquilerComun(
                        cliente,
                        dto.getFechaEvento(),
                        dto.getCantidadDias(),
                        0);
                break;

            case "CORPORATIVO":
                alquiler = new AlquilerCorporativo(
                        cliente,
                        dto.getFechaEvento(),
                        dto.getCantidadDias(),
                        recargoCorporativo);
                break;

            case "MASIVO":
                alquiler = new AlquilerMasivo(
                        cliente,
                        dto.getFechaEvento(),
                        dto.getCantidadDias(),
                        recargoMasivo);
                break;

            default:
                throw new RuntimeException("Tipo de alquiler no válido");
        }

        alquiler.setId(contadorId++);

        // Recorrer equipos solicitados
        for (Map.Entry<String, Integer> item : dto.getItemsSolicitados().entrySet()) {

            String codigoEquipo = item.getKey();
            int cantidad = item.getValue();

            Equipo equipo = equipoController.buscarPorCodigo(codigoEquipo);

            if (equipo == null) {
                throw new RuntimeException(
                        "Equipo " + codigoEquipo + " no encontrado");
            }

            if (!equipo.estaDisponible(cantidad)) {
                throw new RuntimeException(
                        "Stock insuficiente para " + equipo.getNombre());
            }

            DetalleAlquiler detalle = new DetalleAlquiler(
                    equipo,
                    cantidad,
                    equipo.getValorDiario());

            alquiler.agregarDetalle(detalle);

            equipo.reservarStock(cantidad);
        }

        // Validar que el alquiler tenga al menos un equipo
        if (alquiler.getDetalles().isEmpty()) {
            throw new RuntimeException(
                    "El alquiler debe contener al menos un equipo");
        }

        alquileres.add(alquiler);

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(),
                "-",
                "INGRESADO",
                TipoEntidad.ALQUILER,
                String.valueOf(alquiler.getId()),
                usuario);

        historiales.add(historial);

        return alquiler;
    }

    // Finaliza el alquiler: libera stock, calcula importes y registra historial
    public double finalizarAlquiler(int idAlquiler, String usuario) {

        Alquiler alquiler = buscarPorId(idAlquiler);

        if (alquiler == null) {
            throw new RuntimeException("Alquiler no encontrado");
        }

        Cliente cliente = alquiler.getCliente();

        // Liberar stock
        for (DetalleAlquiler detalle : alquiler.getDetalles()) {
            detalle.getEquipo().liberarStock(detalle.getCantidad());
        }

        double porcentajeDescuento =
                cliente.obtenerDescuentoVigente(alquiler.getFechaEvento());

        double importePendiente =
                alquiler.calcularImportePendiente(porcentajeDescuento);

        alquiler.finalizar();

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(),
                "ENTREGADO",
                "FINALIZADO",
                TipoEntidad.ALQUILER,
                String.valueOf(idAlquiler),
                usuario);

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

            if (a.coincideId(id)) {
                return a;
            }
        }

        return null;
    }

    public double getRecargoCorporativo() {
        return recargoCorporativo;
    }

    public void setRecargoCorporativo(double recargoCorporativo) {
        this.recargoCorporativo = recargoCorporativo;
    }

    public double getRecargoMasivo() {
        return recargoMasivo;
    }

    public void setRecargoMasivo(double recargoMasivo) {
        this.recargoMasivo = recargoMasivo;
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }
}