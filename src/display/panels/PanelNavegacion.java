package display.panels;

import javax.swing.*;
import display.components.Colores;
import display.components.Fuentes;

public class PanelNavegacion extends JPanel {
    private PanelContenido contenido;

    public PanelNavegacion(PanelContenido contenido) {
        this.contenido = contenido;

        this.setBackground(Colores.VERDE);

        JButton botonCitas = new JButton("Citas");
        JButton botonClientes = new JButton("Clientes");
        JButton botonListaEspera = new JButton("Lista de espera");
        JButton botonServicios = new JButton("Servicios");

        botonCitas.setBackground(Colores.VERDE_OSCURO);
        botonCitas.setFont(Fuentes.GENERAL);
        botonCitas.setForeground(Colores.TEXTO);

        botonClientes.setBackground(Colores.VERDE_OSCURO);
        botonClientes.setFont(Fuentes.GENERAL);
        botonClientes.setForeground(Colores.TEXTO);

        botonListaEspera.setBackground(Colores.VERDE_OSCURO);
        botonListaEspera.setFont(Fuentes.GENERAL);
        botonListaEspera.setForeground(Colores.TEXTO);

        botonServicios.setBackground(Colores.VERDE_OSCURO);
        botonServicios.setFont(Fuentes.GENERAL);
        botonServicios.setForeground(Colores.TEXTO);

        botonCitas.addActionListener(e -> {
            contenido.setUpPanelContenido("citas");
        });

        botonClientes.addActionListener(e -> {
            contenido.setUpPanelContenido("clientes");
        });

        botonListaEspera.addActionListener(e -> {
            contenido.setUpPanelContenido("lista_espera");
        });

        botonServicios.addActionListener(e -> {
            contenido.setUpPanelContenido("servicios");
        });
 
        add(botonCitas);
        add(botonClientes);
        add(botonListaEspera);
        add(botonServicios);  
    }
}
