package main;

import dto.ClienteDTO;
import dto.EquipoDTO;
import mvc.controller.ClienteController;
import mvc.controller.EquipoController;
import mvc.model.TipoEquipo;
import mvc.view.PrincipalView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        cargarEquiposPrueba();
        cargarClientesPrueba();

        SwingUtilities.invokeLater(() -> {
            PrincipalView ventanaPrincipal = new PrincipalView();
            ventanaPrincipal.setVisible(true);
        });
    }

    private static void cargarEquiposPrueba() {

        EquipoController controlador = EquipoController.getInstance();

        try {

            controlador.registrarEquipo(
                    new EquipoDTO(
                            "EQ001",
                            "Parlante JBL",
                            "Parlante profesional",
                            TipoEquipo.SONIDO,
                            15000,
                            5,
                            false),
                    "admin");

            controlador.registrarEquipo(
                    new EquipoDTO(
                            "EQ002",
                            "Micrófono Shure",
                            "Micrófono inalámbrico",
                            TipoEquipo.SONIDO,
                            5000,
                            8,
                            false),
                    "admin");

            controlador.registrarEquipo(
                    new EquipoDTO(
                            "EQ003",
                            "Luces LED",
                            "Kit de iluminación",
                            TipoEquipo.ILUMINACION,
                            10000,
                            10,
                            true),
                    "admin");

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cargarClientesPrueba() {

        ClienteController controlador = ClienteController.getInstance();

        try {

            controlador.registrarCliente(
                    new ClienteDTO(
                            "30111222",
                            "Juan Pérez",
                            "1122334455",
                            "juan@gmail.com",
                            "Av. Siempre Viva 123"),
                    "admin");

            controlador.registrarCliente(
                    new ClienteDTO(
                            "27888999",
                            "María Gómez",
                            "1166778899",
                            "maria@gmail.com",
                            "Calle Falsa 456"),
                    "admin");

            controlador.registrarCliente(
                    new ClienteDTO(
                            "30999888",
                            "Empresa Eventos S.A.",
                            "1144556677",
                            "contacto@eventos.com",
                            "Av. Corrientes 789"),
                    "admin");

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}