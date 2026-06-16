package mvc.view;

import mvc.controller.AlquilerController;

import javax.swing.*;
import java.awt.*;

public class FinalizarAlquilerView extends JPanel {

    private JTextField campoIdAlquiler;
    private JButton botonFinalizarAlquiler;
    private JLabel etiquetaResultado;

    public FinalizarAlquilerView() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        campoIdAlquiler = new JTextField();
        botonFinalizarAlquiler = new JButton("Finalizar alquiler");
        etiquetaResultado = new JLabel("");

        panel.add(new JLabel("ID alquiler:"));
        panel.add(campoIdAlquiler);

        panel.add(new JLabel(""));
        panel.add(botonFinalizarAlquiler);

        panel.add(new JLabel("Resultado:"));
        panel.add(etiquetaResultado);

        add(panel, BorderLayout.NORTH);

        botonFinalizarAlquiler.addActionListener(e -> finalizarAlquiler());
    }

    private void finalizarAlquiler() {
        try {
            int idAlquiler = Integer.parseInt(campoIdAlquiler.getText().trim());

            double saldoPendiente = AlquilerController.getInstance()
                    .finalizarAlquiler(idAlquiler, "sistema");

            etiquetaResultado.setText("Saldo pendiente: $" + saldoPendiente);

        } catch (Exception error) {
            etiquetaResultado.setText(error.getMessage());
        }
    }
}