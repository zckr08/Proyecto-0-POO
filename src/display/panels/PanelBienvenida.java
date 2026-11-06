
package display.panels;

import javax.swing.*;
import java.awt.*;

public class PanelBienvenida extends JPanel {

    private JLabel mensaje;

    public PanelBienvenida(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        setLayout(new GridBagLayout());
        setBackground(hueso);
        mensaje = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Bienvenido a su gestor.<br>"
                        + "Aqui podra administrar sus clientes,<br>"
                        + "servicios, citas y horarios.<br>"
                        + "Gracias por usar. :)"
                        + "</div></html>"
        );
        mensaje.setFont(font);
        mensaje.setForeground(verdeOscuro);
        add(mensaje);
    }
}
