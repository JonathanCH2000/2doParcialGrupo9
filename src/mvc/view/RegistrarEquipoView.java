package mvc.view;

import dto.EquipoDTO;
import mvc.controller.EquipoController;
import mvc.model.TipoEquipo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 10, 20),
                BorderFactory.createTitledBorder("Datos del equipo")
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

        campoCodigo      = new JTextField(20);
        campoNombre      = new JTextField(20);
        campoDescripcion = new JTextField(20);
        campoValorDiario = new JTextField(20);
        campoStock       = new JTextField(20);
        comboTipoEquipo  = new JComboBox<>(TipoEquipo.values());
        checkRequiereInstalacion = new JCheckBox("Requiere instalación");
        checkRequiereInstalacion.setBackground(Color.WHITE);

        gcLabel.gridy = 0; gcCampo.gridy = 0;
        panelFormulario.add(new JLabel("Código:"), gcLabel);
        panelFormulario.add(campoCodigo, gcCampo);

        gcLabel.gridy = 1; gcCampo.gridy = 1;
        panelFormulario.add(new JLabel("Nombre:"), gcLabel);
        panelFormulario.add(campoNombre, gcCampo);

        gcLabel.gridy = 2; gcCampo.gridy = 2;
        panelFormulario.add(new JLabel("Descripción:"), gcLabel);
        panelFormulario.add(campoDescripcion, gcCampo);

        gcLabel.gridy = 3; gcCampo.gridy = 3;
        panelFormulario.add(new JLabel("Tipo equipo:"), gcLabel);
        panelFormulario.add(comboTipoEquipo, gcCampo);

        gcLabel.gridy = 4; gcCampo.gridy = 4;
        panelFormulario.add(new JLabel("Valor diario:"), gcLabel);
        panelFormulario.add(campoValorDiario, gcCampo);

        gcLabel.gridy = 5; gcCampo.gridy = 5;
        panelFormulario.add(new JLabel("Stock inicial:"), gcLabel);
        panelFormulario.add(campoStock, gcCampo);

        gcLabel.gridy = 6; gcCampo.gridy = 6;
        panelFormulario.add(new JLabel("Instalación:"), gcLabel);
        panelFormulario.add(checkRequiereInstalacion, gcCampo);

        // Botones alineados a la derecha
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setPreferredSize(new Dimension(120, 35));
        botonLimpiar.setOpaque(true);
        botonLimpiar.setBorderPainted(false);
        botonLimpiar.setBackground(new Color(200, 200, 200));
        botonLimpiar.setForeground(Color.DARK_GRAY);
        botonLimpiar.setFocusPainted(false);

        botonRegistrarEquipo = new JButton("Registrar equipo");
        botonRegistrarEquipo.setPreferredSize(new Dimension(160, 35));
        botonRegistrarEquipo.setOpaque(true);
        botonRegistrarEquipo.setBorderPainted(false);
        botonRegistrarEquipo.setBackground(new Color(59, 130, 246));
        botonRegistrarEquipo.setForeground(Color.WHITE);
        botonRegistrarEquipo.setFocusPainted(false);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(new EmptyBorder(0, 20, 10, 20));
        panelBoton.add(botonLimpiar);
        panelBoton.add(botonRegistrarEquipo);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(245, 245, 245));
        panelSur.add(panelBoton, BorderLayout.NORTH);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

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