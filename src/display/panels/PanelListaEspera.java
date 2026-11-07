package display.panels;

import javax.swing.*;
import java.awt.*;
import display.components.Navegacion;

public class PanelListaEspera extends JPanel {
    private JPanel seccionBotones;

    public PanelListaEspera(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        setLayout(new GridBagLayout());
        setBackground(hueso);
        JPanel seccionBotones = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        seccionBotones.setLayout(borderLayout);

        JButton botonEliminarDeLaLista = new JButton("Eliminar de la lista");
        JButton botonAgregarALista = new JButton("Agregar a la lista");
        JLabel titulo = new JLabel("Menu Lista De Espera", SwingConstants.CENTER);
        botonAgregarALista.setBackground(verdeOscuro);
        botonAgregarALista.setFont(font);
        botonAgregarALista.setForeground(verdeClaro);

        botonEliminarDeLaLista.setBackground(verdeOscuro);
        botonEliminarDeLaLista.setFont(font);
        botonEliminarDeLaLista.setForeground(verdeClaro);

        botonAgregarALista.addActionListener(e -> {
            Navegacion.mostrar(this, "agregar_cliente");
        });

        botonEliminarDeLaLista.addActionListener(e -> {
            Navegacion.mostrar(this, "borrar_cliente");
        });

        titulo.setBackground(verdeOscuro);
        titulo.setFont(font);
        titulo.setForeground(verdeClaro);

        seccionBotones.add(botonAgregarALista, BorderLayout.CENTER);
        seccionBotones.add(botonEliminarDeLaLista, BorderLayout.SOUTH);
        seccionBotones.add(titulo, BorderLayout.NORTH);
        add(seccionBotones);
    }
}