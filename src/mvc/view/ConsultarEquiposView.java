package mvc.view;

import mvc.controller.EquipoController;
import mvc.model.Equipo;
import mvc.model.TipoEquipo;

import javax.swing.*;
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

        JPanel panelFiltros = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        campoFechaEvento = new JTextField("2026-06-20");
        campoCantidadDias = new JTextField();
        comboTipoEquipo = new JComboBox<>(TipoEquipo.values());
        botonConsultar = new JButton("Consultar");

        panelFiltros.add(new JLabel("Fecha evento (AAAA-MM-DD):"));
        panelFiltros.add(campoFechaEvento);

        panelFiltros.add(new JLabel("Cantidad de dias:"));
        panelFiltros.add(campoCantidadDias);

        panelFiltros.add(new JLabel("Tipo de equipo:"));
        panelFiltros.add(comboTipoEquipo);

        panelFiltros.add(new JLabel(""));
        panelFiltros.add(botonConsultar);

        String[] columnas = {"Codigo", "Nombre", "Tipo", "Valor diario", "Stock", "Instalacion"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);

        add(panelFiltros, BorderLayout.NORTH);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        botonConsultar.addActionListener(e -> consultarEquipos());
    }

    private void consultarEquipos() {
        LocalDate fechaEvento;

        try {
            fechaEvento = LocalDate.parse(campoFechaEvento.getText().trim());
        } catch (DateTimeParseException error) {
            JOptionPane.showMessageDialog(this, "Formato de fecha invalido. Use AAAA-MM-DD");
            return;
        }

        int cantidadDias;

        try {
            cantidadDias = Integer.parseInt(campoCantidadDias.getText().trim());
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "La cantidad de dias debe ser un numero");
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
                    equipo.isRequiereInstalacion() ? "Si" : "No"
            });
        }

        if (equiposDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay equipos disponibles");
        }
    }
}