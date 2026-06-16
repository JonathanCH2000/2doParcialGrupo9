package main;

import mvc.view.RegistrarClienteView;
import mvc.view.ConsultarEquiposView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistrarClienteView().setVisible(true);
            new ConsultarEquiposView().setVisible(true);
        });
    }
}
