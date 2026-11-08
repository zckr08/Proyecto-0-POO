package display.panels;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import display.components.PanelTabla;
import display.dialogs.DialogFormularioServicio;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelServicios extends JPanel {

    public PanelServicios(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();

        setLayout(new BorderLayout());
        setBackground(verde);

        JLabel titulo = new JLabel("Servicios", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(verdeOscuro);
        titulo.setForeground(verdeClaro);
        titulo.setFont(font);

        PanelTabla tablaServicios = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);
        List<String> descripciones = controladora.getListaServicios();
        tablaServicios.setElementos(descripciones);

        JPanel seccionBotones = new JPanel(new GridLayout(2, 2, 20, 10));
        seccionBotones.setBackground(verde);
        seccionBotones.setOpaque(true);

        JButton botonBorrar = new JButton("Borrar Servicio");
        JButton botonModificar = new JButton("Modificar Servicio");
        JButton botonConsultar = new JButton("Consultar Servicio");
        JButton botonCrear = new JButton("Crear Servicio");

        for (JButton boton : new JButton[]{botonBorrar, botonModificar, botonConsultar, botonCrear}) {
            boton.setFont(font);
            boton.setBackground(verdeOscuro);
            boton.setForeground(verdeClaro);
        }

        botonBorrar.addActionListener(e -> {
            int pos = tablaServicios.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.");
                return;
            }
            try {
                Window ventana = SwingUtilities.getWindowAncestor(this);

                DialogFormularioServicio dialogServicio = new DialogFormularioServicio(
                        Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO,
                        Fuentes.GENERAL, "Se ha borrado un servicio!", ventana, pos);

                
                controladora.borrarServicio(pos);

                tablaServicios.setElementos(controladora.getListaServicios());
                dialogServicio.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonModificar.addActionListener(e -> {
            int pos = tablaServicios.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioServicio dialogServicio = new DialogFormularioServicio(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO,
                    Fuentes.GENERAL, "Modificar Servicio", ventana, pos);
            dialogServicio.setVisible(true);
            tablaServicios.setElementos(controladora.getListaServicios());
        });

        botonConsultar.addActionListener(e -> {
            int pos = tablaServicios.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioServicio dialogServicio = new DialogFormularioServicio(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO,
                    Fuentes.GENERAL, "Consultar Servicio", ventana, pos);
            dialogServicio.setVisible(true);
        });

        botonCrear.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioServicio dialogServicio = new DialogFormularioServicio(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO,
                    Fuentes.GENERAL, "Crear Servicio", ventana, -1);
            dialogServicio.setVisible(true);
            tablaServicios.setElementos(controladora.getListaServicios());
        });

        seccionBotones.add(botonBorrar);
        seccionBotones.add(botonModificar);
        seccionBotones.add(botonConsultar);
        seccionBotones.add(botonCrear);

        JLabel instruccion = new JLabel("Seleccione el servicio a modificar, borrar o consultar", SwingConstants.CENTER);
        instruccion.setForeground(verdeClaro);
        instruccion.setFont(font);

        JPanel seccionInferior = new JPanel(new BorderLayout(0, 10));
        seccionInferior.setOpaque(false);
        seccionInferior.add(seccionBotones, BorderLayout.CENTER);
        seccionInferior.add(instruccion, BorderLayout.SOUTH);

        add(titulo, BorderLayout.NORTH);
        add(tablaServicios, BorderLayout.CENTER);
        add(seccionInferior, BorderLayout.SOUTH);
    }
}