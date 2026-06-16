package mvc.controller;

import dto.ClienteDTO;
import mvc.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.ClienteYaExisteException;

public class ClienteController {

    private static ClienteController instancia;

    private List<Cliente> clientes;
    private List<HistorialCambioEstado> historiales;

    private ClienteController() {
        this.clientes = new ArrayList<>();
        this.historiales = new ArrayList<>();
    }

    public static synchronized ClienteController getInstance() {
        if (instancia == null) {
            instancia = new ClienteController();
        }
        return instancia;
    }

    // UC1 - Registrar cliente: valida duplicado, crea, activa y registra historial
    public void registrarCliente(ClienteDTO dto, String usuario) throws ClienteYaExisteException{
        for (Cliente c : clientes) {
            if (c.getDniCuit().equals(dto.getDniCuit())) {
                throw new ClienteYaExisteException("Ya existe un cliente con ese DNI/CUIT");
            }
        }
        Cliente cliente = new Cliente(dto.getDniCuit(), dto.getNombreRazonSocial(),
                dto.getTelefono(), dto.getEmail(), dto.getDireccion());
        cliente.activar();
        clientes.add(cliente);

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(), "-", "ACTIVO", TipoEntidad.CLIENTE, dto.getDniCuit(), usuario);
        historiales.add(historial);
    }

    public Cliente buscarPorDniCuit(String dniCuit) {
        for (Cliente c : clientes) {
            if (c.getDniCuit().equals(dniCuit)) return c;
        }
        return null;
    }

    public List<Cliente> getClientes() { return clientes; }
    public List<HistorialCambioEstado> getHistoriales() { return historiales; }
}
