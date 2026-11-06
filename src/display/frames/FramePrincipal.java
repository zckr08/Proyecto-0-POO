package display.frames;

import javax.swing.*;
import display.components.Colores;
import display.components.Colores;
import display.panels.PanelContenido;
import display.panels.PanelInferior;
import display.panels.PanelNavegacion;

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

    }

}
