package display.panels;

import control.Controladora;
import display.components.PanelTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PanelAgregarCliente extends JPanel {

     public PanelAgregarCliente(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();

        setLayout(new BorderLayout(0, 10));
        setBackground(verde);

        JLabel titulo = new JLabel("Clientes en Sistema - Agregar Cliente a Lista de Espera", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(verdeOscuro);
        titulo.setForeground(verdeClaro);
        titulo.setFont(font);

        PanelTabla tablaClientes = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);
        tablaClientes.setElementos(controladora.getListaClientes());

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setFont(font);
        botonAgregar.setBackground(verdeOscuro);
        botonAgregar.setForeground(verdeClaro);

        botonAgregar.addActionListener(e -> {
            int pos = tablaClientes.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.");
                return;
            }
            try {
                controladora.agregarAListaEspera(pos);
                JOptionPane.showMessageDialog(this, "Cliente agregado a la lista de espera.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel instruccion = new JLabel("Seleccione el cliente que desea agregar a la lista de espera", SwingConstants.CENTER);
        instruccion.setForeground(verdeClaro);
        instruccion.setFont(font);

        JPanel contenedorBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorBoton.setOpaque(false);
        contenedorBoton.add(botonAgregar);

        JPanel seccionInferior = new JPanel(new GridLayout(2, 1, 0, 10));
        seccionInferior.setOpaque(false);
        seccionInferior.add(contenedorBoton);
        seccionInferior.add(instruccion);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                tablaClientes.setElementos(controladora.getListaClientes());
            }
        });

        add(titulo, BorderLayout.NORTH);
        add(tablaClientes, BorderLayout.CENTER);
        add(seccionInferior, BorderLayout.SOUTH);
    }

}
