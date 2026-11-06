package display.panels;

import display.components.Colores;
import display.components.Fuentes;
import javax.swing.*;
import java.awt.*;

public class PanelContenido extends JPanel {

    private CardLayout cardLayout;

    public PanelContenido() {

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(new PanelBienvenida(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL), "bienvenida");
        add(new PanelCitas(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL), "citas");
        add(new PanelClientes(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL), "clientes");
        add(new PanelListaEspera(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO), Fuentes.GENERAL, "lista_espera");
        add(new PanelAgregarCliente(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL), "agregar_cliente");
        add(new PanelBorrarCliente(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL), "borrar_cliente");
    }

    public void setUpPanelContenido(String nombre) {
        cardLayout.show(this, nombre);
    }
}
