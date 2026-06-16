package mvc.view;

import dto.ClienteDTO;
import mvc.controller.ClienteController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistrarClienteView extends JPanel {

    private JTextField campoDniCuit;
    private JTextField campoNombre;
    private JTextField campoTelefono;
    private JTextField campoEmail;
    private JTextField campoDireccion;
    private JButton botonRegistrar;
    private JButton botonLimpiar;

    public RegistrarClienteView() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 10, 20),
                BorderFactory.createTitledBorder("Datos del cliente")
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

        campoDniCuit  = new JTextField(20);
        campoNombre   = new JTextField(20);
        campoTelefono = new JTextField(20);
        campoEmail    = new JTextField(20);
        campoDireccion = new JTextField(20);

        gcLabel.gridy = 0; gcCampo.gridy = 0;
        panelFormulario.add(new JLabel("DNI/CUIT:"), gcLabel);
        panelFormulario.add(campoDniCuit, gcCampo);

        gcLabel.gridy = 1; gcCampo.gridy = 1;
        panelFormulario.add(new JLabel("Nombre / Razón Social:"), gcLabel);
        panelFormulario.add(campoNombre, gcCampo);

        gcLabel.gridy = 2; gcCampo.gridy = 2;
        panelFormulario.add(new JLabel("Teléfono:"), gcLabel);
        panelFormulario.add(campoTelefono, gcCampo);

        gcLabel.gridy = 3; gcCampo.gridy = 3;
        panelFormulario.add(new JLabel("Email:"), gcLabel);
        panelFormulario.add(campoEmail, gcCampo);

        gcLabel.gridy = 4; gcCampo.gridy = 4;
        panelFormulario.add(new JLabel("Dirección:"), gcLabel);
        panelFormulario.add(campoDireccion, gcCampo);

        // Botones
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setPreferredSize(new Dimension(120, 35));
        botonLimpiar.setOpaque(true);
        botonLimpiar.setBorderPainted(false);
        botonLimpiar.setBackground(new Color(200, 200, 200));
        botonLimpiar.setForeground(Color.DARK_GRAY);
        botonLimpiar.setFocusPainted(false);

        botonRegistrar = new JButton("Registrar");
        botonRegistrar.setPreferredSize(new Dimension(120, 35));
        botonRegistrar.setOpaque(true);
        botonRegistrar.setBorderPainted(false);
        botonRegistrar.setBackground(new Color(59, 130, 246));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(new EmptyBorder(0, 20, 10, 20));
        panelBoton.add(botonLimpiar);
        panelBoton.add(botonRegistrar);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(245, 245, 245));
        panelSur.add(panelBoton, BorderLayout.NORTH);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        botonRegistrar.addActionListener(e -> registrarCliente());
        botonLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void registrarCliente() {
        if (campoDniCuit.getText().trim().isEmpty() || campoNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI/CUIT y nombre son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteDTO datosCliente = new ClienteDTO(
                campoDniCuit.getText().trim(),
                campoNombre.getText().trim(),
                campoTelefono.getText().trim(),
                campoEmail.getText().trim(),
                campoDireccion.getText().trim()
        );

        try {
            ClienteController.getInstance().registrarCliente(datosCliente, "sistema");
            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");
            limpiarFormulario();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        campoDniCuit.setText("");
        campoNombre.setText("");
        campoTelefono.setText("");
        campoEmail.setText("");
        campoDireccion.setText("");
    }
}