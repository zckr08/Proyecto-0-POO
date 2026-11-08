package display.frames;

import display.components.PanelCalendario;

import javax.swing.*;
import java.awt.*;

public class FrameCalendario extends JFrame {
    public FrameCalendario() {
        setTitle("Calendario mensual - Barbería");
        setSize(880, 650);
        setMinimumSize(new Dimension(680, 480));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(new PanelCalendario(), BorderLayout.CENTER);
    }
}
