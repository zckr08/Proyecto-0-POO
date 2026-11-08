package display.panels;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import display.components.PanelCalendario;
import display.components.PanelTabla;
import display.dialogs.DialogFormularioCita;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelCitas extends JPanel {

    public PanelCitas(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();
        setLayout(new BorderLayout(0, 5));
        setBackground(hueso);

        JLabel titulo = new JLabel("Menú de citas", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(verdeOscuro);
        titulo.setForeground(verdeClaro);
        titulo.setFont(Fuentes.CALENDARIO);

        PanelTabla tablaCitas = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO,
                Colores.HUESO, Colores.VERDE_CLARO, Fuentes.CALENDARIO);
        List<String> listaCitas = controladora.getListaCitas();
        tablaCitas.setElementos(listaCitas);

        PanelCalendario calendario = new PanelCalendario();
        calendario.setPreferredSize(new Dimension(900, 300));

        JPanel seccionBotones = new JPanel(new GridLayout(1, 4, 8, 0));
        seccionBotones.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        seccionBotones.setBackground(verde);

        JButton botonConsultar = new JButton("Consultar Cita");
        JButton botonModificar = new JButton("Modificar Cita");
        JButton botonBorrar = new JButton("Borrar Cita");
        JButton botonCrear = new JButton("Crear Cita");

        for (JButton boton : new JButton[]{botonConsultar, botonModificar, botonBorrar, botonCrear}) {
            boton.setFont(Fuentes.CALENDARIO);
            boton.setBackground(verdeOscuro);
            boton.setForeground(verdeClaro);
            boton.setFocusPainted(false);
            seccionBotones.add(boton);
        }

        botonBorrar.addActionListener(e -> {
            int pos = tablaCitas.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
                return;
            }
            try {
                Window ventana = SwingUtilities.getWindowAncestor(this);
                DialogFormularioCita dialogCita = new DialogFormularioCita(
                        Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO,
                        Colores.VERDE_CLARO, Fuentes.GENERAL, "Se ha borrado una cita!", ventana, pos);
                controladora.borrarCita(pos);
                tablaCitas.setElementos(controladora.getListaCitas());
                calendario.actualizar();
                dialogCita.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonModificar.addActionListener(e -> {
            int pos = tablaCitas.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCita dialogCita = new DialogFormularioCita(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO,
                    Colores.VERDE_CLARO, Fuentes.GENERAL, "Modificar Cita", ventana, pos);
            dialogCita.setVisible(true);
            tablaCitas.setElementos(controladora.getListaCitas());
            calendario.actualizar();
        });

        botonConsultar.addActionListener(e -> {
            int pos = tablaCitas.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCita dialogCita = new DialogFormularioCita(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO,
                    Colores.VERDE_CLARO, Fuentes.GENERAL, "Consultar Cita", ventana, pos);
            dialogCita.setVisible(true);
        });

        botonCrear.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCita dialogCita = new DialogFormularioCita(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO,
                    Colores.VERDE_CLARO, Fuentes.GENERAL, "Crear Cita", ventana, -1);
            dialogCita.setVisible(true);
            tablaCitas.setElementos(controladora.getListaCitas());
            calendario.actualizar();
        });

        JPanel seccionCitas = new JPanel(new BorderLayout());
        seccionCitas.setBackground(verde);
        seccionCitas.add(tablaCitas, BorderLayout.CENTER);
        seccionCitas.add(seccionBotones, BorderLayout.SOUTH);

        add(titulo, BorderLayout.NORTH);
        add(seccionCitas, BorderLayout.CENTER);
        add(calendario, BorderLayout.SOUTH);
    }
}
