package display.panels;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import display.components.PanelTabla;
import display.dialogs.DialogFormularioCita;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelCitas extends JPanel {

    public PanelCitas(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Menu de citas", SwingConstants.CENTER);

        PanelTabla tablaCitas = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL);
        List<String> listaCitas = controladora.getListaCitas();
        tablaCitas.setElementos(listaCitas);

        JPanel seccionBotones = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 3, 20, 10);
        seccionBotones.setLayout(gridLayout);
        seccionBotones.setOpaque(false);

        JButton botonBorrar = new JButton("Borrar Cita");
        JButton botonModificar = new JButton("Modificar Cita");
        JButton botonConsultar = new JButton("Consultar Cita");
        JButton botonCrear = new JButton("Crear Cita");
        JButton botonCalendario = new JButton("Ver Calendario");

        botonBorrar.setFont(font);
        botonBorrar.setBackground(verdeOscuro);
        botonBorrar.setForeground(verdeClaro);
        botonBorrar.addActionListener(e -> {
            int pos = tablaCitas.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
                return;
            }
            try {
                Window ventana = SwingUtilities.getWindowAncestor(this);

                DialogFormularioCita dialogCita = new DialogFormularioCita(
                        Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO,
                        Fuentes.GENERAL, "Se ha borrado una cita!", ventana, pos);

                controladora.borrarCita(pos);

                tablaCitas.setElementos(controladora.getListaCitas());
                dialogCita.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonModificar.setFont(font);
        botonModificar.setBackground(verdeOscuro);
        botonModificar.setForeground(verdeClaro);
        botonModificar.addActionListener(e -> {
            int pos = tablaCitas.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCita dialogCita = new DialogFormularioCita(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Modificar Cita",
                    ventana, pos);
            dialogCita.setVisible(true);
            tablaCitas.setElementos(controladora.getListaCitas());
        });

        botonConsultar.setFont(font);
        botonConsultar.setBackground(verdeOscuro);
        botonConsultar.setForeground(verdeClaro);
        botonConsultar.addActionListener(e -> {
            int pos = tablaCitas.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCita dialogCita = new DialogFormularioCita(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Consultar Cita",
                    ventana, pos);
            dialogCita.setVisible(true);
            tablaCitas.setElementos(controladora.getListaCitas());
        });

        botonCrear.setFont(font);
        botonCrear.setBackground(verdeOscuro);
        botonCrear.setForeground(verdeClaro);
        botonCrear.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCita dialogCita = new DialogFormularioCita(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Crear Cita",
                    ventana, -1);
            dialogCita.setVisible(true);
            tablaCitas.setElementos(controladora.getListaCitas());
        });

        botonCalendario.setFont(font);
        botonCalendario.setBackground(verdeOscuro);
        botonCalendario.setForeground(verdeClaro);
        botonCalendario.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "El calendario todavía no está disponible.");
        });

        seccionBotones.add(botonBorrar);
        seccionBotones.add(botonModificar);
        seccionBotones.add(botonConsultar);
        seccionBotones.add(botonCrear);
        seccionBotones.add(botonCalendario);

        add(titulo, BorderLayout.NORTH);
        add(tablaCitas, BorderLayout.CENTER);
        add(seccionBotones, BorderLayout.SOUTH);
    }
}