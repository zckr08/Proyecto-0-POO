package display.panels;

import display.components.Colores;
import display.components.Fuentes;
import display.frames.FrameHorario;
import display.frames.FrameServicios;
import display.frames.FrameEnviarCorreoConfirmacion;

import javax.swing.*;
import java.awt.Window;
import java.awt.event.WindowEvent;

public class PanelInferior extends JPanel {

    public PanelInferior() {
        setBackground(Colores.VERDE);

        JButton botonHorario = new JButton("Horario");
        JButton botonSalir = new JButton("Salir");
        JButton botonServicios = new JButton("Servicios");
        JButton botonNotificar = new JButton("Notificar");

        for (JButton boton : new JButton[]{botonHorario, botonSalir, botonServicios, botonNotificar}) {
            boton.setBackground(Colores.VERDE_OSCURO);
            boton.setFont(Fuentes.GENERAL);
            boton.setForeground(Colores.TEXTO);
        }

        botonHorario.addActionListener(e -> {
            FrameHorario ventana = new FrameHorario();
            ventana.setVisible(true);
        });

        botonSalir.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana != null) {
                ventana.dispatchEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSING));
            }
        });

        botonServicios.addActionListener(e -> {
            FrameServicios ventana = new FrameServicios();
            ventana.setVisible(true);
        });

        botonNotificar.addActionListener(e -> {
            FrameEnviarCorreoConfirmacion ventana = new FrameEnviarCorreoConfirmacion();
            ventana.setVisible(true);
        });

        add(botonHorario);
        add(botonSalir);
        add(botonServicios);
        add(botonNotificar);
    }
}
