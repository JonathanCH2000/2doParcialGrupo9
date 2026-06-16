package mvc.view;

import dto.EquipoDTO;
import mvc.controller.EquipoController;
import mvc.model.TipoEquipo;

import javax.swing.*;
import java.awt.*;

public class RegistrarEquipoView extends JPanel {

    private JTextField campoCodigo;
    private JTextField campoNombre;
    private JTextField campoDescripcion;
    private JTextField campoValorDiario;
    private JTextField campoStock;
    private JComboBox<TipoEquipo> comboTipoEquipo;
    private JCheckBox checkRequiereInstalacion;
    private JButton botonRegistrarEquipo;
    private JButton botonLimpiar;

    public RegistrarEquipoView() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        campoCodigo = new JTextField();
        campoNombre = new JTextField();
        campoDescripcion = new JTextField();
        campoValorDiario = new JTextField();
        campoStock = new JTextField();

        comboTipoEquipo = new JComboBox<>(TipoEquipo.values());
        checkRequiereInstalacion = new JCheckBox("Requiere instalacion");

        botonRegistrarEquipo = new JButton("Registrar equipo");
        botonLimpiar = new JButton("Limpiar");

        panel.add(new JLabel("Codigo:"));
        panel.add(campoCodigo);

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);

        panel.add(new JLabel("Descripcion:"));
        panel.add(campoDescripcion);

        panel.add(new JLabel("Tipo equipo:"));
        panel.add(comboTipoEquipo);

        panel.add(new JLabel("Valor diario:"));
        panel.add(campoValorDiario);

        panel.add(new JLabel("Stock inicial:"));
        panel.add(campoStock);

        panel.add(new JLabel("Instalacion:"));
        panel.add(checkRequiereInstalacion);

        panel.add(botonLimpiar);
        panel.add(botonRegistrarEquipo);

        add(panel, BorderLayout.NORTH);

        botonRegistrarEquipo.addActionListener(e -> registrarEquipo());
        botonLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void registrarEquipo() {
        try {
            EquipoDTO datosEquipo = new EquipoDTO(
                    campoCodigo.getText().trim(),
                    campoNombre.getText().trim(),
                    campoDescripcion.getText().trim(),
                    (TipoEquipo) comboTipoEquipo.getSelectedItem(),
                    Double.parseDouble(campoValorDiario.getText().trim()),
                    Integer.parseInt(campoStock.getText().trim()),
                    checkRequiereInstalacion.isSelected()
            );

            EquipoController.getInstance().registrarEquipo(datosEquipo, "sistema");

            JOptionPane.showMessageDialog(this, "Equipo registrado correctamente");
            limpiarFormulario();

        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        campoCodigo.setText("");
        campoNombre.setText("");
        campoDescripcion.setText("");
        campoValorDiario.setText("");
        campoStock.setText("");
        checkRequiereInstalacion.setSelected(false);
    }
}