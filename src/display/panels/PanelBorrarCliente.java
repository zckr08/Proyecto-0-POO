package display.panels;

import control.Controladora;
import display.components.PanelTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PanelBorrarCliente extends JPanel {

    public PanelBorrarCliente(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();

        setLayout(new BorderLayout(0, 10));
        setBackground(verde);

        JLabel titulo = new JLabel("Clientes en Lista de Espera - Borrar Cliente de la Lista de Espera", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(verdeOscuro);
        titulo.setForeground(verdeClaro);
        titulo.setFont(font);

        PanelTabla tablaEspera = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);
        tablaEspera.setElementos(controladora.getListaEsperaClientes());

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setFont(font);
        botonEliminar.setBackground(verdeOscuro);
        botonEliminar.setForeground(verdeClaro);

        botonEliminar.addActionListener(e -> {
            int pos = tablaEspera.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.");
                return;
            }
            try {
                controladora.borrarDeListaEspera(pos);
                tablaEspera.setElementos(controladora.getListaEsperaClientes());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel instruccion = new JLabel("Seleccione el cliente que desea eliminar de la lista de espera", SwingConstants.CENTER);
        instruccion.setForeground(verdeClaro);
        instruccion.setFont(font);

        JPanel contenedorBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorBoton.setOpaque(false);
        contenedorBoton.add(botonEliminar);

        JPanel seccionInferior = new JPanel(new GridLayout(2, 1, 0, 10));
        seccionInferior.setOpaque(false);
        seccionInferior.add(contenedorBoton);
        seccionInferior.add(instruccion);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                tablaEspera.setElementos(controladora.getListaEsperaClientes());
            }
        });

        add(titulo, BorderLayout.NORTH);
        add(tablaEspera, BorderLayout.CENTER);
        add(seccionInferior, BorderLayout.SOUTH);
    }
}
