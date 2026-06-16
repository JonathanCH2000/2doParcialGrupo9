package mvc.view;

import dto.SolicitudAlquilerDTO;
import mvc.controller.AlquilerController;
import mvc.model.Alquiler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SolicitarAlquilerView extends JPanel {

    private JTextField campoDniCuit;
    private JTextField campoCodigoEquipo;
    private JTextField campoCantidad;
    private JTextField campoFechaEvento;
    private JTextField campoCantidadDias;
    private JComboBox<String> comboTipoAlquiler;
    private JButton botonSolicitarAlquiler;
    private JLabel etiquetaResultado;

    public SolicitarAlquilerView() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        campoDniCuit = new JTextField();
        campoCodigoEquipo = new JTextField();
        campoCantidad = new JTextField();
        campoFechaEvento = new JTextField("2026-06-20");
        campoCantidadDias = new JTextField();

        comboTipoAlquiler = new JComboBox<>(new String[]{"COMUN", "CORPORATIVO", "MASIVO"});
        botonSolicitarAlquiler = new JButton("Solicitar alquiler");
        etiquetaResultado = new JLabel("");

        panel.add(new JLabel("DNI/CUIT cliente:"));
        panel.add(campoDniCuit);

        panel.add(new JLabel("Codigo equipo:"));
        panel.add(campoCodigoEquipo);

        panel.add(new JLabel("Cantidad:"));
        panel.add(campoCantidad);

        panel.add(new JLabel("Fecha evento:"));
        panel.add(campoFechaEvento);

        panel.add(new JLabel("Cantidad dias:"));
        panel.add(campoCantidadDias);

        panel.add(new JLabel("Tipo alquiler:"));
        panel.add(comboTipoAlquiler);

        panel.add(new JLabel(""));
        panel.add(botonSolicitarAlquiler);

        panel.add(new JLabel("Resultado:"));
        panel.add(etiquetaResultado);

        add(panel, BorderLayout.NORTH);

        botonSolicitarAlquiler.addActionListener(e -> solicitarAlquiler());
    }

    private void solicitarAlquiler() {
        try {
            String dniCuit = campoDniCuit.getText().trim();
            String codigoEquipo = campoCodigoEquipo.getText().trim();
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            LocalDate fechaEvento = LocalDate.parse(campoFechaEvento.getText().trim());
            int cantidadDias = Integer.parseInt(campoCantidadDias.getText().trim());
            String tipoAlquiler = comboTipoAlquiler.getSelectedItem().toString();

            Map<String, Integer> itemsSolicitados = new HashMap<>();
            itemsSolicitados.put(codigoEquipo, cantidad);

            SolicitudAlquilerDTO datosAlquiler = new SolicitudAlquilerDTO(
                    dniCuit,
                    itemsSolicitados,
                    fechaEvento,
                    cantidadDias,
                    tipoAlquiler
            );

            Alquiler alquiler = AlquilerController.getInstance().solicitarAlquiler(datosAlquiler, "sistema");

            etiquetaResultado.setText("Alquiler ingresado. ID: " + alquiler.getId());

        } catch (Exception error) {
            etiquetaResultado.setText(error.getMessage());
        }
    }
}