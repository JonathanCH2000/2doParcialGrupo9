package mvc.controller;

import dto.SolicitudAlquilerDTO;
import mvc.exceptions.ReglaNegocioException;
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

    // Solicitar alquiler: busca cliente, crea alquiler segun tipo
    public Alquiler solicitarAlquiler(SolicitudAlquilerDTO dto, String usuario) {

        ClienteController clienteController = ClienteController.getInstance();
        EquipoController equipoController = EquipoController.getInstance();

        // Buscar cliente
        Cliente cliente = clienteController.buscarPorDniCuit(dto.getDniCuit());

        if (cliente == null) {
            throw new ReglaNegocioException("Cliente no encontrado");
        }

        if (!cliente.estaActivo()) {
            throw new ReglaNegocioException("El cliente no esta activo");
        }

        if (dto.getCantidadDias() <= 0) {
            throw new ReglaNegocioException("La cantidad de dias debe ser mayor a cero");
        }

        if (dto.getItemsSolicitados() == null || dto.getItemsSolicitados().isEmpty()) {
            throw new ReglaNegocioException("Debe seleccionar al menos un equipo");
        }

        // Validar equipos antes de crear el alquiler
        for (Map.Entry<String, Integer> item : dto.getItemsSolicitados().entrySet()) {

            String codigoEquipo = item.getKey();
            int cantidad = item.getValue();

            if (cantidad <= 0) {
                throw new ReglaNegocioException("La cantidad debe ser mayor a cero");
            }

            Equipo equipo = equipoController.buscarPorCodigo(codigoEquipo);

            if (equipo == null) {
                throw new ReglaNegocioException("Equipo " + codigoEquipo + " no encontrado");
            }

            if (!equipo.estaDisponible(cantidad)) {
                throw new ReglaNegocioException("Stock insuficiente para " + equipo.getNombre());
            }
        }

        // Crear alquiler segun tipo
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
                throw new ReglaNegocioException("Tipo de alquiler no valido");
        }

        alquiler.setId(contadorId++);

        // Recorrer equipos solicitados
        for (Map.Entry<String, Integer> item : dto.getItemsSolicitados().entrySet()) {

            String codigoEquipo = item.getKey();
            int cantidad = item.getValue();

            Equipo equipo = equipoController.buscarPorCodigo(codigoEquipo);

            DetalleAlquiler detalle = new DetalleAlquiler(
                    equipo,
                    cantidad,
                    equipo.getValorDiario());

            alquiler.agregarDetalle(detalle);
            equipo.reservarStock(cantidad);
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

    public void registrarSenia(int idAlquiler, double importeSenia, String usuario) {

        Alquiler alquiler = buscarPorId(idAlquiler);

        if (alquiler == null) {
            throw new ReglaNegocioException("Alquiler no encontrado");
        }

        if (importeSenia <= 0) {
            throw new ReglaNegocioException("La senia debe ser mayor a cero");
        }

        alquiler.registrarSenia(importeSenia);
        alquiler.confirmar();

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(),
                "INGRESADO",
                "CONFIRMADO",
                TipoEntidad.ALQUILER,
                String.valueOf(idAlquiler),
                usuario);

        historiales.add(historial);
    }

    // Finaliza el alquiler: libera stock, calcula importes y registra historial
    public double finalizarAlquiler(int idAlquiler, String usuario) {

        Alquiler alquiler = buscarPorId(idAlquiler);

        if (alquiler == null) {
            throw new ReglaNegocioException("Alquiler no encontrado");
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

        String estadoAnterior = alquiler.getEstado().toString();

        alquiler.finalizar();

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(),
                estadoAnterior,
                "FINALIZADO",
                TipoEntidad.ALQUILER,
                String.valueOf(idAlquiler),
                usuario);

        historiales.add(historial);

        return importePendiente;
    }

    // Consulta total recaudado en un periodo
    public double totalRecaudado(LocalDate fechaDesde, LocalDate fechaHasta) {

        double total = 0;

        for (Alquiler alquiler : alquileres) {

            if (alquiler.getEstado() == EstadoAlquiler.FINALIZADO
                    && !alquiler.getFechaEvento().isBefore(fechaDesde)
                    && !alquiler.getFechaEvento().isAfter(fechaHasta)) {

                total += alquiler.getImporteTotal();
            }
        }

        return total;
    }

    // Consulta alquileres confirmados de un cliente
    public List<Alquiler> obtenerAlquileresConfirmados(String dniCuit) {

        List<Alquiler> resultado = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {

            if (alquiler.getCliente().getDniCuit().equals(dniCuit)
                    && alquiler.getEstado() == EstadoAlquiler.CONFIRMADO) {

                resultado.add(alquiler);
            }
        }

        return resultado;
    }

    public Alquiler buscarPorId(int id) {

        for (Alquiler alquiler : alquileres) {

            if (alquiler.coincideId(id)) {
                return alquiler;
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

    public List<HistorialCambioEstado> getHistoriales() {
        return historiales;
    }
}