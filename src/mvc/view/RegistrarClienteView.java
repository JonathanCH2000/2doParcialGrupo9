package mvc.view;

import dto.ClienteDTO;
import mvc.controller.ClienteController;

import javax.swing.*;
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
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        campoDniCuit = new JTextField();
        campoNombre = new JTextField();
        campoTelefono = new JTextField();
        campoEmail = new JTextField();
        campoDireccion = new JTextField();

        botonRegistrar = new JButton("Registrar");
        botonLimpiar = new JButton("Limpiar");

        panel.add(new JLabel("DNI/CUIT:"));
        panel.add(campoDniCuit);

        panel.add(new JLabel("Nombre / razon social:"));
        panel.add(campoNombre);

        panel.add(new JLabel("Telefono:"));
        panel.add(campoTelefono);

        panel.add(new JLabel("Email:"));
        panel.add(campoEmail);

        panel.add(new JLabel("Direccion:"));
        panel.add(campoDireccion);

        panel.add(botonLimpiar);
        panel.add(botonRegistrar);

        add(panel, BorderLayout.NORTH);

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