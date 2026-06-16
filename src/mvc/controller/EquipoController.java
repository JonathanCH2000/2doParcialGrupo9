package mvc.controller;

import dto.EquipoDTO;
import mvc.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoController {

    private static EquipoController instancia;

    private List<Equipo> equipos;
    private List<HistorialCambioEstado> historiales;

    private EquipoController() {
        this.equipos = new ArrayList<>();
        this.historiales = new ArrayList<>();
    }

    public static synchronized EquipoController getInstance() {
        if (instancia == null) {
            instancia = new EquipoController();
        }
        return instancia;
    }

    // UC2 - Registrar equipo: valida código duplicado, crea y registra historial
    public void registrarEquipo(EquipoDTO dto, String usuario) {
        for (Equipo e : equipos) {
            if (e.getCodigo().equals(dto.getCodigo())) {
                throw new RuntimeException("Ya existe un equipo con ese código");
            }
        }
        Equipo equipo = new Equipo(dto.getCodigo(), dto.getNombre(), dto.getDescripcion(),
                dto.getTipoEquipo(), dto.getValorDiario(), dto.getStockInicial(), dto.isRequiereInstalacion());
        equipos.add(equipo);

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(), "-", "DISPONIBLE", TipoEntidad.EQUIPO, dto.getCodigo(), usuario);
        historiales.add(historial);
    }

    // UC5 - Consultar equipos disponibles para una fecha y tipo de evento
    public List<Equipo> consultarEquiposDisponibles(LocalDate fechaEvento, int cantidadDias, TipoEquipo tipoEvento) {
        List<Equipo> disponibles = new ArrayList<>();
        for (Equipo equipo : equipos) {
            if (equipo.coincideTipoEvento(tipoEvento) && equipo.estaDisponible(1)) {
                disponibles.add(equipo);
            }
        }
        return disponibles;
    }

    public Equipo buscarPorCodigo(String codigo) {
        for (Equipo e : equipos) {
            if (e.getCodigo().equals(codigo)) return e;
        }
        return null;
    }

    public List<Equipo> getEquipos() { return equipos; }
}
