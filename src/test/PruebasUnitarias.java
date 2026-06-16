package test;

import dto.ClienteDTO;
import dto.EquipoDTO;
import dto.SolicitudAlquilerDTO;
import mvc.controller.AlquilerController;
import mvc.controller.ClienteController;
import mvc.controller.EquipoController;
import mvc.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pruebas unitarias del sistema de alquiler de equipamiento.
 * Independientes de la interfaz gráfica — se ejecutan desde main().
 */
public class PruebasUnitarias {

    private static int pruebas = 0;
    private static int exitosas = 0;

    public static void main(String[] args) {
        prueba1_RegistrarCliente();
        prueba2_ClienteDuplicadoLanzaExcepcion();
        prueba3_RegistrarEquipoYConsultarDisponibilidad();
        prueba4_SolicitarAlquilerCorporativoCalculaRecargo();
        prueba5_CancelacionAnticipacionDevuelveCrediito();

        System.out.println("\n=== RESULTADO: " + exitosas + "/" + pruebas + " pruebas exitosas ===");
    }

    // Prueba 1: registrar un cliente nuevo lo agrega a la lista
    static void prueba1_RegistrarCliente() {
        pruebas++;
        try {
            ClienteController ctrl = ClienteController.getInstance();
            ClienteDTO dto = new ClienteDTO("20111111111", "Juan Pérez", "1111-1111", "juan@mail.com", "Calle 1");
            ctrl.registrarCliente(dto, "admin");
            Cliente encontrado = ctrl.buscarPorDniCuit("20111111111");
            assert encontrado != null : "Cliente no encontrado";
            assert encontrado.getNombreRazonSocial().equals("Juan Pérez") : "Nombre incorrecto";
            assert encontrado.estaActivo() : "Cliente debería estar activo";
            System.out.println("[OK] Prueba 1: Registrar cliente");
            exitosas++;
        } catch (Exception e) {
            System.out.println("[FAIL] Prueba 1: " + e.getMessage());
        }
    }

    // Prueba 2: registrar un cliente con DNI/CUIT duplicado lanza excepción
    static void prueba2_ClienteDuplicadoLanzaExcepcion() {
        pruebas++;
        try {
            ClienteController ctrl = ClienteController.getInstance();
            ClienteDTO dto = new ClienteDTO("20111111111", "Otro Nombre", "2222", "otro@mail.com", "Calle 2");
            ctrl.registrarCliente(dto, "admin"); // debe lanzar excepción
            System.out.println("[FAIL] Prueba 2: Debería haber lanzado excepción por duplicado");
        } catch (RuntimeException e) {
            System.out.println("[OK] Prueba 2: Cliente duplicado lanza excepción correctamente");
            exitosas++;
        }
    }

    // Prueba 3: registrar equipo y verificar disponibilidad
    static void prueba3_RegistrarEquipoYConsultarDisponibilidad() {
        pruebas++;
        try {
            EquipoController ctrl = EquipoController.getInstance();
            EquipoDTO dto = new EquipoDTO("SON001", "Parlante JBL", "Sistema de sonido", TipoEquipo.SONIDO, 5000, 3, true);
            ctrl.registrarEquipo(dto, "admin");
            Equipo equipo = ctrl.buscarPorCodigo("SON001");
            assert equipo != null : "Equipo no encontrado";
            assert equipo.estaDisponible(2) : "Debería estar disponible para cantidad 2";
            assert !equipo.estaDisponible(5) : "No debería estar disponible para cantidad 5 (stock=3)";
            System.out.println("[OK] Prueba 3: Registrar equipo y consultar disponibilidad");
            exitosas++;
        } catch (Exception e) {
            System.out.println("[FAIL] Prueba 3: " + e.getMessage());
        }
    }

    // Prueba 4: alquiler corporativo aplica recargo del 10%
    static void prueba4_SolicitarAlquilerCorporativoCalculaRecargo() {
        pruebas++;
        try {
            AlquilerController ctrl = AlquilerController.getInstance();
            // Necesitamos cliente y equipo ya registrados (prueba 1 y 3)
            Map<String, Integer> items = new HashMap<>();
            items.put("SON001", 1);
            SolicitudAlquilerDTO dto = new SolicitudAlquilerDTO(
                    "20111111111", items, LocalDate.now().plusDays(10), 2, "CORPORATIVO");
            Alquiler alquiler = ctrl.solicitarAlquiler(dto, "admin");
            assert alquiler instanceof AlquilerCorporativo : "Debería ser AlquilerCorporativo";
            assert alquiler.getPorcentajeRecargoAplicado() == 10.0 : "Recargo corporativo debería ser 10%";
            System.out.println("[OK] Prueba 4: Alquiler corporativo con recargo 10%");
            exitosas++;
        } catch (Exception e) {
            System.out.println("[FAIL] Prueba 4: " + e.getMessage());
        }
    }

    // Prueba 5: cancelación con más de 72 horas → seña queda como crédito
    static void prueba5_CancelacionAnticipacionDevuelveCrediito() {
        pruebas++;
        try {
            // Alquiler con fecha de evento en 5 días, cancelar hoy → más de 72hs
            AlquilerComun alquiler = new AlquilerComun(
                    new Cliente("99999999999", "Test", "0", "t@t.com", "X"),
                    LocalDate.now().plusDays(5), 1, 0);
            alquiler.registrarSenia(1000.0);

            long horas = alquiler.calcularHorasAnticipacion(LocalDate.now());
            boolean devuelveCredito = horas > 72;

            assert devuelveCredito : "Con más de 72hs de anticipación debería devolver crédito";
            System.out.println("[OK] Prueba 5: Cancelación con >72hs devuelve crédito (horas=" + horas + ")");
            exitosas++;
        } catch (Exception e) {
            System.out.println("[FAIL] Prueba 5: " + e.getMessage());
        }
    }
}
