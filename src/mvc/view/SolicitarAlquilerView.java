package mvc.view;

import dto.SolicitudAlquilerDTO;
import mvc.controller.AlquilerController;
import mvc.model.Alquiler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 10, 20),
                BorderFactory.createTitledBorder("Datos del alquiler")
        ));

        GridBagConstraints gcLabel = new GridBagConstraints();
        gcLabel.anchor = GridBagConstraints.WEST;
        gcLabel.insets = new Insets(8, 10, 8, 10);
        gcLabel.gridx = 0;

        GridBagConstraints gcCampo = new GridBagConstraints();
        gcCampo.fill = GridBagConstraints.HORIZONTAL;
        gcCampo.weightx = 1.0;
        gcCampo.insets = new Insets(8, 0, 8, 10);
        gcCampo.gridx = 1;

        campoDniCuit      = new JTextField(20);
        campoCodigoEquipo = new JTextField(20);
        campoCantidad     = new JTextField(20);
        campoFechaEvento  = new JTextField("2026-06-20", 20);
        campoCantidadDias = new JTextField(20);
        comboTipoAlquiler = new JComboBox<>(new String[]{"COMÚN", "CORPORATIVO", "MASIVO"});

        gcLabel.gridy = 0; gcCampo.gridy = 0;
        panelFormulario.add(new JLabel("DNI/CUIT cliente:"), gcLabel);
        panelFormulario.add(campoDniCuit, gcCampo);

        gcLabel.gridy = 1; gcCampo.gridy = 1;
        panelFormulario.add(new JLabel("Código equipo:"), gcLabel);
        panelFormulario.add(campoCodigoEquipo, gcCampo);

        gcLabel.gridy = 2; gcCampo.gridy = 2;
        panelFormulario.add(new JLabel("Cantidad:"), gcLabel);
        panelFormulario.add(campoCantidad, gcCampo);

        gcLabel.gridy = 3; gcCampo.gridy = 3;
        panelFormulario.add(new JLabel("Fecha evento:"), gcLabel);
        panelFormulario.add(campoFechaEvento, gcCampo);

        gcLabel.gridy = 4; gcCampo.gridy = 4;
        panelFormulario.add(new JLabel("Cantidad de días:"), gcLabel);
        panelFormulario.add(campoCantidadDias, gcCampo);

        gcLabel.gridy = 5; gcCampo.gridy = 5;
        panelFormulario.add(new JLabel("Tipo de alquiler:"), gcLabel);
        panelFormulario.add(comboTipoAlquiler, gcCampo);

        // Botón alineado a la derecha
        botonSolicitarAlquiler = new JButton("Solicitar alquiler");
        botonSolicitarAlquiler.setPreferredSize(new Dimension(180, 35));
        botonSolicitarAlquiler.setOpaque(true);
        botonSolicitarAlquiler.setBorderPainted(false);
        botonSolicitarAlquiler.setBackground(new Color(59, 130, 246));
        botonSolicitarAlquiler.setForeground(Color.WHITE);
        botonSolicitarAlquiler.setFocusPainted(false);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(new EmptyBorder(0, 20, 10, 20));
        panelBoton.add(botonSolicitarAlquiler);

        // Etiqueta de resultado
        etiquetaResultado = new JLabel(" ");
        etiquetaResultado.setBorder(new EmptyBorder(10, 25, 10, 25));
        etiquetaResultado.setFont(etiquetaResultado.getFont().deriveFont(Font.BOLD));

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(245, 245, 245));
        panelSur.add(panelBoton, BorderLayout.NORTH);
        panelSur.add(etiquetaResultado, BorderLayout.CENTER);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

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