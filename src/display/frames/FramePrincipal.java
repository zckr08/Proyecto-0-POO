package display.frames;

import javax.swing.*;
import display.components.Colores;
import display.components.Colores;
import display.components.Fuentes;
import display.panels.PanelContenido;
import display.panels.PanelInferior;
import display.panels.PanelNavegacion;
import display.components.PanelTabla;

import java.awt.*;

public class FramePrincipal extends JFrame {

    public FramePrincipal() {
        setLayout(new BorderLayout());
        PanelContenido contenido = new PanelContenido();
        PanelNavegacion navegacion = new PanelNavegacion(contenido);
        PanelInferior inferior = new PanelInferior();
        add(navegacion, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        PanelTabla tabla = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL);

    }

}
