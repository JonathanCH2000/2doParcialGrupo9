package mvc.view;

import javax.swing.*;
import java.awt.*;

public class PrincipalView extends JFrame {

    private JPanel panelContenido;

    public PrincipalView() {
        setTitle("Sistema de Alquiler de Equipos");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelMenu = new JPanel(new GridLayout(1, 5));

        JButton botonRegistrarCliente = new JButton("Registrar cliente");
        JButton botonRegistrarEquipo = new JButton("Registrar equipo");
        JButton botonSolicitarAlquiler = new JButton("Solicitar alquiler");
        JButton botonConsultarEquipos = new JButton("Consultar equipos");
        JButton botonFinalizarAlquiler = new JButton("Finalizar alquiler");

        panelMenu.add(botonRegistrarCliente);
        panelMenu.add(botonRegistrarEquipo);
        panelMenu.add(botonSolicitarAlquiler);
        panelMenu.add(botonConsultarEquipos);
        panelMenu.add(botonFinalizarAlquiler);

        panelContenido = new JPanel(new BorderLayout());

        add(panelMenu, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);

        botonRegistrarCliente.addActionListener(e -> mostrarPanel(new RegistrarClienteView()));
        botonRegistrarEquipo.addActionListener(e -> mostrarPanel(new RegistrarEquipoView()));
        botonSolicitarAlquiler.addActionListener(e -> mostrarPanel(new SolicitarAlquilerView()));
        botonConsultarEquipos.addActionListener(e -> mostrarPanel(new ConsultarEquiposView()));
        botonFinalizarAlquiler.addActionListener(e -> mostrarPanel(new FinalizarAlquilerView()));

        mostrarPanel(new RegistrarClienteView());
    }

    private void mostrarPanel(JPanel panel) {
        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}