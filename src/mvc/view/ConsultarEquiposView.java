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

public class ConsultarEquiposView extends JFrame {

    private JTextField txtFechaEvento;
    private JTextField txtCantidadDias;
    private JComboBox<TipoEquipo> cmbTipoEquipo;
    private JButton btnConsultar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    public ConsultarEquiposView() {
        setTitle("Consultar Equipos Disponibles");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelFiltros = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        txtFechaEvento  = new JTextField("AAAA-MM-DD");
        txtCantidadDias = new JTextField();
        cmbTipoEquipo   = new JComboBox<>(TipoEquipo.values());
        btnConsultar    = new JButton("Consultar");

        panelFiltros.add(new JLabel("Fecha Evento (AAAA-MM-DD):")); panelFiltros.add(txtFechaEvento);
        panelFiltros.add(new JLabel("Cantidad de Días:"));          panelFiltros.add(txtCantidadDias);
        panelFiltros.add(new JLabel("Tipo de Equipo:"));            panelFiltros.add(cmbTipoEquipo);
        panelFiltros.add(new JLabel(""));
        panelFiltros.add(btnConsultar);

        String[] columnas = {"Código", "Nombre", "Tipo", "Valor Diario", "Stock", "Instalación"};
        modeloTabla     = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);

        setLayout(new BorderLayout(10, 10));
        add(panelFiltros, BorderLayout.NORTH);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        btnConsultar.addActionListener(e -> consultarEquipos());
    }

    private void consultarEquipos() {
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFechaEvento.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use AAAA-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(txtCantidadDias.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad de días debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoEquipo tipo = (TipoEquipo) cmbTipoEquipo.getSelectedItem();

        // La vista llama directo al controller que le corresponde
        List<Equipo> disponibles = EquipoController.getInstance()
                .consultarEquiposDisponibles(fecha, dias, tipo);

        modeloTabla.setRowCount(0);
        for (Equipo e : disponibles) {
            modeloTabla.addRow(new Object[]{
                    e.getCodigo(), e.getNombre(), e.getTipoEquipo(),
                    e.getValorDiario(), e.getStockDisponible(),
                    e.isRequiereInstalacion() ? "Sí" : "No"
            });
        }

        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay equipos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
