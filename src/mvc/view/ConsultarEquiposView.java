package mvc.view;

import mvc.controller.EquipoController;
import mvc.model.Equipo;
import mvc.model.TipoEquipo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ConsultarEquiposView extends JPanel {

    private JTextField campoFechaEvento;
    private JTextField campoCantidadDias;
    private JComboBox<TipoEquipo> comboTipoEquipo;
    private JButton botonConsultar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    public ConsultarEquiposView() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        // Panel de filtros con GridBagLayout para mejor control visual
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 10, 20),
                BorderFactory.createTitledBorder("Filtros")
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

        campoFechaEvento  = new JTextField("2026-06-20", 20);
        campoCantidadDias = new JTextField(20);
        comboTipoEquipo   = new JComboBox<>(TipoEquipo.values());

        gcLabel.gridy = 0; gcCampo.gridy = 0;
        panelFiltros.add(new JLabel("Fecha evento (AAAA-MM-DD):"), gcLabel);
        panelFiltros.add(campoFechaEvento, gcCampo);

        gcLabel.gridy = 1; gcCampo.gridy = 1;
        panelFiltros.add(new JLabel("Cantidad de días:"), gcLabel);
        panelFiltros.add(campoCantidadDias, gcCampo);

        gcLabel.gridy = 2; gcCampo.gridy = 2;
        panelFiltros.add(new JLabel("Tipo de equipo:"), gcLabel);
        panelFiltros.add(comboTipoEquipo, gcCampo);

        // Botón en su propio panel alineado a la derecha
        botonConsultar = new JButton("Consultar");
        botonConsultar.setPreferredSize(new Dimension(150, 35));
        botonConsultar.setOpaque(true);
        botonConsultar.setBorderPainted(false);
        botonConsultar.setBackground(new Color(59, 130, 246));
        botonConsultar.setForeground(Color.WHITE);
        botonConsultar.setFocusPainted(false);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(new EmptyBorder(0, 20, 10, 20));
        panelBoton.add(botonConsultar);

        // Tabla con estilo
        String[] columnas = {"Código", "Nombre", "Tipo", "Valor diario", "Stock", "Instalación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setRowHeight(28);
        tablaResultados.getTableHeader().setBackground(new Color(59, 130, 246));
        tablaResultados.getTableHeader().setForeground(Color.WHITE);
        tablaResultados.setSelectionBackground(new Color(219, 234, 254));

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(245, 245, 245));
        panelNorte.add(panelFiltros, BorderLayout.CENTER);
        panelNorte.add(panelBoton, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        botonConsultar.addActionListener(e -> consultarEquipos());
    }

    private void consultarEquipos() {
        LocalDate fechaEvento;

        try {
            fechaEvento = LocalDate.parse(campoFechaEvento.getText().trim());
        } catch (DateTimeParseException error) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use AAAA-MM-DD");
            return;
        }

        int cantidadDias;

        try {
            cantidadDias = Integer.parseInt(campoCantidadDias.getText().trim());
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "La cantidad de días debe ser un número");
            return;
        }

        TipoEquipo tipoEquipo = (TipoEquipo) comboTipoEquipo.getSelectedItem();

        List<Equipo> equiposDisponibles = EquipoController.getInstance()
                .consultarEquiposDisponibles(fechaEvento, cantidadDias, tipoEquipo);

        modeloTabla.setRowCount(0);

        for (Equipo equipo : equiposDisponibles) {
            modeloTabla.addRow(new Object[]{
                    equipo.getCodigo(),
                    equipo.getNombre(),
                    equipo.getTipoEquipo(),
                    equipo.getValorDiario(),
                    equipo.getStockDisponible(),
                    equipo.isRequiereInstalacion() ? "Sí" : "No"
            });
        }

        if (equiposDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay equipos disponibles");
        }
    }
}