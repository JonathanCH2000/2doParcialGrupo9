package mvc.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PrincipalView extends JFrame {

    private JPanel panelContenido;
    private JButton botonActivo;

    public PrincipalView() {
        setTitle("Sistema de Alquiler de Equipos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Panel de menú superior
        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelMenu.setBackground(new Color(30, 64, 175));
        panelMenu.setBorder(new EmptyBorder(8, 10, 8, 10));

        JButton botonRegistrarCliente  = crearBotonMenu("Registrar cliente");
        JButton botonRegistrarEquipo   = crearBotonMenu("Registrar equipo");
        JButton botonSolicitarAlquiler = crearBotonMenu("Solicitar alquiler");
        JButton botonConsultarEquipos  = crearBotonMenu("Consultar equipos");
        JButton botonFinalizarAlquiler = crearBotonMenu("Finalizar alquiler");

        panelMenu.add(botonRegistrarCliente);
        panelMenu.add(botonRegistrarEquipo);
        panelMenu.add(botonSolicitarAlquiler);
        panelMenu.add(botonConsultarEquipos);
        panelMenu.add(botonFinalizarAlquiler);

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(new Color(245, 245, 245));

        add(panelMenu, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);

        botonRegistrarCliente.addActionListener(e -> { mostrarPanel(new RegistrarClienteView()); marcarActivo(botonRegistrarCliente); });
        botonRegistrarEquipo.addActionListener(e -> { mostrarPanel(new RegistrarEquipoView()); marcarActivo(botonRegistrarEquipo); });
        botonSolicitarAlquiler.addActionListener(e -> { mostrarPanel(new SolicitarAlquilerView()); marcarActivo(botonSolicitarAlquiler); });
        botonConsultarEquipos.addActionListener(e -> { mostrarPanel(new ConsultarEquiposView()); marcarActivo(botonConsultarEquipos); });
        botonFinalizarAlquiler.addActionListener(e -> { mostrarPanel(new FinalizarAlquilerView()); marcarActivo(botonFinalizarAlquiler); });

        mostrarPanel(new RegistrarClienteView());
        marcarActivo(botonRegistrarCliente);
    }

    // Crea un botón con el estilo base del menú
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(30, 64, 175));
        boton.setForeground(Color.WHITE);
        boton.setFont(boton.getFont().deriveFont(13f));
        boton.setBorder(new EmptyBorder(8, 16, 8, 16));
        return boton;
    }

    // Resalta el botón del panel activo
    private void marcarActivo(JButton boton) {
        if (botonActivo != null) {
            botonActivo.setBackground(new Color(30, 64, 175));
        }
        boton.setBackground(new Color(59, 130, 246));
        botonActivo = boton;
    }

    private void mostrarPanel(JPanel panel) {
        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}