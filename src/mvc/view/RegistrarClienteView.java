package mvc.view;

import dto.ClienteDTO;
import mvc.controller.ClienteController;

import javax.swing.*;
import java.awt.*;

public class RegistrarClienteView extends JFrame {

    private JTextField txtDniCuit;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JButton btnRegistrar;
    private JButton btnLimpiar;

    public RegistrarClienteView() {
        setTitle("Registrar Cliente");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtDniCuit   = new JTextField();
        txtNombre    = new JTextField();
        txtTelefono  = new JTextField();
        txtEmail     = new JTextField();
        txtDireccion = new JTextField();
        btnRegistrar = new JButton("Registrar");
        btnLimpiar   = new JButton("Limpiar");

        panel.add(new JLabel("DNI/CUIT:"));       panel.add(txtDniCuit);
        panel.add(new JLabel("Nombre / Razón Social:")); panel.add(txtNombre);
        panel.add(new JLabel("Teléfono:"));        panel.add(txtTelefono);
        panel.add(new JLabel("Email:"));           panel.add(txtEmail);
        panel.add(new JLabel("Dirección:"));       panel.add(txtDireccion);
        panel.add(btnLimpiar);
        panel.add(btnRegistrar);

        add(panel);

        btnRegistrar.addActionListener(e -> registrarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void registrarCliente() {
        if (txtDniCuit.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI/CUIT y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteDTO dto = new ClienteDTO(
                txtDniCuit.getText().trim(),
                txtNombre.getText().trim(),
                txtTelefono.getText().trim(),
                txtEmail.getText().trim(),
                txtDireccion.getText().trim()
        );

        try {
            // La vista llama directo al controller que le corresponde
            ClienteController.getInstance().registrarCliente(dto, "sistema");
            JOptionPane.showMessageDialog(this, "Cliente registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDniCuit.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
    }
}
