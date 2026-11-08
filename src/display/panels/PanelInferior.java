package display.panels;

import javax.swing.*;

import display.components.Colores;
import display.components.Fuentes;
import display.dialogs.DialogEstablecerHorario;
import display.frames.FrameEnviarCorreoConfirmacion;

import java.awt.*;

public class PanelInferior extends JPanel {

    public PanelInferior() {
        setBackground(Colores.VERDE);

        JButton botonHorario = new JButton("Horario");
        JButton botonSalir = new JButton("Salir");
        JButton botonNotificar = new JButton("Notificar");

        botonHorario.setBackground(Colores.VERDE_OSCURO);
        botonHorario.setFont(Fuentes.GENERAL);
        botonHorario.setForeground(Colores.TEXTO);

        botonSalir.setBackground(Colores.VERDE_OSCURO);
        botonSalir.setFont(Fuentes.GENERAL);
        botonSalir.setForeground(Colores.TEXTO);

        botonNotificar.setBackground(Colores.VERDE_OSCURO);
        botonNotificar.setFont(Fuentes.GENERAL);
        botonNotificar.setForeground(Colores.TEXTO);

        botonHorario.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogEstablecerHorario dialogHorario = new DialogEstablecerHorario(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.TEXTO, Fuentes.GENERAL, "Establecer horario", ventana);
            dialogHorario.setVisible(true);
        });

        botonSalir.addActionListener(e -> {
            System.exit(0);
        });

        botonNotificar.addActionListener(e -> {
            FrameEnviarCorreoConfirmacion frameConfirmacion = new FrameEnviarCorreoConfirmacion();
            frameConfirmacion.setVisible(true);
        });

        add(botonHorario);
        add(botonSalir);
        add(botonNotificar);
    }
}