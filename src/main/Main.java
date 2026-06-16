package main;

import mvc.view.PrincipalView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrincipalView ventanaPrincipal = new PrincipalView();
            ventanaPrincipal.setVisible(true);
        });
    }
}