package display.panels;

import display.components.PanelCalendario;

import javax.swing.*;
import java.awt.*;

public class PanelBienvenida extends JPanel {
    private PanelCalendario calendario;

    public PanelBienvenida(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        setLayout(new BorderLayout());
        setBackground(hueso);
        calendario = new PanelCalendario();
        add(calendario, BorderLayout.CENTER);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && calendario != null) {
            calendario.actualizar();
        }
    }
}
