package display.panels;

import javax.swing.*;
import display.components.Colores;
import display.components.Fuentes;

public class PanelNavegacion extends JPanel {
    private PanelContenido contenido;

    public PanelNavegacion(PanelContenido contenido) {
        this.contenido = contenido;
        setBackground(Colores.VERDE);

        JButton botonListaEspera = new JButton("Lista de Espera");
        JButton botonCalendario = new JButton("Calendario Mensual");
        JButton botonCitas = new JButton("Citas");
        JButton botonClientes = new JButton("Clientes");

        for (JButton boton : new JButton[]{botonListaEspera, botonCalendario, botonCitas, botonClientes}) {
            boton.setBackground(Colores.VERDE_OSCURO);
            boton.setFont(Fuentes.CALENDARIO);
            boton.setForeground(Colores.TEXTO);
            boton.setFocusPainted(false);
            add(boton);
        }

        botonListaEspera.addActionListener(e -> contenido.setUpPanelContenido("lista_espera"));
        botonCalendario.addActionListener(e -> contenido.setUpPanelContenido("bienvenida"));
        botonCitas.addActionListener(e -> contenido.setUpPanelContenido("citas"));
        botonClientes.addActionListener(e -> contenido.setUpPanelContenido("clientes"));
    }
}
