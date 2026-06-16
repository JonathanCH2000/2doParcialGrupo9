package mvc.controller;

import dto.ClienteDTO;
import mvc.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {

    private static ClienteController instancia;

    private List<Cliente> clientes;
    private List<HistorialCambioEstado> historiales;

    private ClienteController() {
        this.clientes = new ArrayList<>();
        this.historiales = new ArrayList<>();
    }

    public static synchronized ClienteController getInstance() {  //singleton
        if (instancia == null) {
            instancia = new ClienteController();
        }
        return instancia;
    }

    // Registrar cliente
    public void registrarCliente(ClienteDTO dto, String usuario) {

        if (buscarPorDniCuit(dto.getDniCuit()) != null) {
            throw new RuntimeException(
                    "Ya existe un cliente con ese DNI/CUIT");
        }

        Cliente cliente = new Cliente(
                dto.getDniCuit(),
                dto.getNombreRazonSocial(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getDireccion());

        cliente.activar();

        clientes.add(cliente);

        HistorialCambioEstado historial = new HistorialCambioEstado(
                LocalDate.now(),
                "-",
                "ACTIVO",
                TipoEntidad.CLIENTE,
                dto.getDniCuit(),
                usuario);

        historiales.add(historial);
    }

    // Busca a un cliente por Dni o Cuit
    public Cliente buscarPorDniCuit(String dniCuit) {

        for (Cliente c : clientes) {  ///busca cliente por dni o cuit
            if (c.getDniCuit().equals(dniCuit)) 
            	return c;  //lo encontro
        }
        return null; //no estaba
    }
    public List<Cliente> getClientes() {
    	return clientes;   //agrega el cliente
    	}
    public List<HistorialCambioEstado> getHistoriales() { 
    	return historiales; }  //agrega el historial
}




