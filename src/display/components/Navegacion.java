package display.components;

import display.panels.PanelContenido;
import javax.swing.*;

public final class Navegacion {

    private Navegacion() {}

    public static void mostrar(JPanel origen, String destino) {
        PanelContenido contenido = (PanelContenido) SwingUtilities.getAncestorOfClass(PanelContenido.class, origen);
        contenido.setUpPanelContenido(destino);
    }
}