package mvc.view;

import mvc.controller.AlquilerController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FinalizarAlquilerView extends JPanel {

    private JTextField campoIdAlquiler;
    private JButton botonFinalizarAlquiler;
    private JLabel etiquetaResultado;

    public FinalizarAlquilerView() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 10, 20),
                BorderFactory.createTitledBorder("Finalizar alquiler")
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

        campoIdAlquiler = new JTextField(20);

        gcLabel.gridy = 0; gcCampo.gridy = 0;
        panelFormulario.add(new JLabel("ID alquiler:"), gcLabel);
        panelFormulario.add(campoIdAlquiler, gcCampo);

        // Botón
        botonFinalizarAlquiler = new JButton("Finalizar alquiler");
        botonFinalizarAlquiler.setPreferredSize(new Dimension(180, 35));
        botonFinalizarAlquiler.setOpaque(true);
        botonFinalizarAlquiler.setBorderPainted(false);
        botonFinalizarAlquiler.setBackground(new Color(59, 130, 246));
        botonFinalizarAlquiler.setForeground(Color.WHITE);
        botonFinalizarAlquiler.setFocusPainted(false);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(new EmptyBorder(0, 20, 10, 20));
        panelBoton.add(botonFinalizarAlquiler);

        // Etiqueta resultado
        etiquetaResultado = new JLabel(" ");
        etiquetaResultado.setBorder(new EmptyBorder(10, 25, 10, 25));
        etiquetaResultado.setFont(etiquetaResultado.getFont().deriveFont(Font.BOLD));

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(245, 245, 245));
        panelSur.add(panelBoton, BorderLayout.NORTH);
        panelSur.add(etiquetaResultado, BorderLayout.CENTER);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        botonFinalizarAlquiler.addActionListener(e -> finalizarAlquiler());
    }

    private void finalizarAlquiler() {
        try {
            int idAlquiler = Integer.parseInt(campoIdAlquiler.getText().trim());

            double saldoPendiente = AlquilerController.getInstance()
                    .finalizarAlquiler(idAlquiler, "sistema");

            etiquetaResultado.setText("✓  Saldo pendiente: $" + saldoPendiente);
            etiquetaResultado.setForeground(new Color(22, 163, 74));

        } catch (Exception error) {
            etiquetaResultado.setText("✗  " + error.getMessage());
            etiquetaResultado.setForeground(new Color(220, 38, 38));
        }
    }
}