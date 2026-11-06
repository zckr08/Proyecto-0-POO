package display.panels;

import display.components.Fuentes;
import display.components.Colores;
import display.components.PanelTabla;
import display.components.Navegacion;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public class PanelClientes extends JPanel {

    public PanelClientes(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Clientes en Sistema", SwingConstants.CENTER);

        PanelTabla tablaCliente = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL);

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

        botonModificar.setFont(font);
        botonModificar.setBackground(verdeOscuro);
        botonModificar.setForeground(verdeClaro);

        botonConsultar.setFont(font);
        botonConsultar.setBackground(verdeOscuro);
        botonConsultar.setForeground(verdeClaro);

        botonCrear.setFont(font);
        botonCrear.setBackground(verdeOscuro);
        botonCrear.setForeground(verdeClaro);

        seccionBotones.add(botonBorrar);
        seccionBotones.add(botonModificar);
        seccionBotones.add(botonConsultar);
        seccionBotones.add(botonCrear);

        add(titulo, BorderLayout.NORTH);
        add(tablaCliente, BorderLayout.CENTER);
        add(seccionBotones, BorderLayout.SOUTH);
    }
}
