package display.panels;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import display.components.PanelTabla;
import display.dialogs.DialogFormularioCliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelCitas extends JPanel {

    public PanelCitas(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Citas en Sistema", SwingConstants.CENTER);

        PanelTabla tablaCliente = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL);
        List<String> citas = controladora.getListaCitas();
        tablaCliente.setElementos(citas);

        JPanel seccionBotones = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 2, 20, 10);
        seccionBotones.setLayout(gridLayout);
        seccionBotones.setOpaque(false);

        JButton botonBorrar = new JButton("Borrar Cliente");
        JButton botonModificar = new JButton("Modificar Cliente");
        JButton botonConsultar = new JButton("Consultar Cliente");
        JButton botonCrear = new JButton("Crear Cliente");

        botonBorrar.setFont(font);
        botonBorrar.setBackground(verdeOscuro);
        botonBorrar.setForeground(verdeClaro);
        botonBorrar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Se ha borrado un cliente!",
                    ventana,
                    //Se manda el indice del cliente seleccionado en vez del cliente
                    //esto para evitar tocar la logica desde la interfaz y hacer que cualquier
                    //funcionamiento pase por la capa de control
                    tablaCliente.getIndiceSeleccionado());
            dialogCliente.setVisible(true);
        });

        botonModificar.setFont(font);
        botonModificar.setBackground(verdeOscuro);
        botonModificar.setForeground(verdeClaro);

        botonModificar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Modificar Cliente",
                    ventana, tablaCliente.getIndiceSeleccionado());
            dialogCliente.setVisible(true);
        });

        botonConsultar.setFont(font);
        botonConsultar.setBackground(verdeOscuro);
        botonConsultar.setForeground(verdeClaro);

        botonConsultar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Consultar Cliente",
                    ventana, tablaCliente.getIndiceSeleccionado());
            dialogCliente.setVisible(true);
        });

        botonCrear.setFont(font);
        botonCrear.setBackground(verdeOscuro);
        botonCrear.setForeground(verdeClaro);

        botonCrear.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Crear Cliente",
                    ventana, tablaCliente.getIndiceSeleccionado());
            dialogCliente.setVisible(true);
        });

        seccionBotones.add(botonBorrar);
        seccionBotones.add(botonModificar);
        seccionBotones.add(botonConsultar);
        seccionBotones.add(botonCrear);

        add(titulo, BorderLayout.NORTH);
        add(tablaCliente, BorderLayout.CENTER);
        add(seccionBotones, BorderLayout.SOUTH);
    }
})
}
