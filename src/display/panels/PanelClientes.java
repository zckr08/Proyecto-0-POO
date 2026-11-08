package display.panels;

import control.Controladora;
import display.components.Fuentes;
import display.components.Colores;
import display.components.PanelTabla;
import display.components.Navegacion;
import display.dialogs.DialogEstablecerHorario;
import display.dialogs.DialogFormularioCliente;
import logic.Cliente;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelClientes extends JPanel {

    public PanelClientes(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        Controladora controladora = Controladora.getInstance();
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Clientes en Sistema", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(verdeOscuro);
        titulo.setForeground(verdeClaro);
        titulo.setFont(font);

        PanelTabla tablaCliente = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL);
        List<String> nombresClientes = controladora.getListaClientes();
        tablaCliente.setElementos(nombresClientes);

        JPanel seccionBotones = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 2, 20, 10);
        seccionBotones.setLayout(gridLayout);
        seccionBotones.setOpaque(false);

        JButton botonBorrar = new JButton("Borrar Cliente");
        JButton botonModificar = new JButton("Modificar Cliente");
        JButton botonConsultar = new JButton("Consultar Cliente");
        JButton botonCrear = new JButton("Crear Cliente");

        botonBorrar.setFont(font);
        botonBorrar.setBackground(verdeOscuro);
        botonBorrar.setForeground(verdeClaro);
        botonBorrar.addActionListener(e -> {
            int pos = tablaCliente.getIndiceSeleccionado();
            if (pos == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.");
        return;
        }
        try {
            Window ventana = SwingUtilities.getWindowAncestor(this);

            // 1. Se construye antes de borrar: el constructor carga los datos del cliente
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(
                    Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO,
                    Fuentes.GENERAL, "Se ha borrado un cliente!", ventana, pos);

            // 2. Se borra. Si falla (por ejemplo, tiene citas), salta al catch
            //    y el diálogo nunca se muestra
            controladora.borrarCliente(pos);

            // 3. Se actualiza la tabla y se muestra el diálogo
            tablaCliente.setElementos(controladora.getListaClientes());
            dialogCliente.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

        botonModificar.setFont(font);
        botonModificar.setBackground(verdeOscuro);
        botonModificar.setForeground(verdeClaro);

        botonModificar.addActionListener(e -> {
            int pos = tablaCliente.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Modificar Cliente",
                    ventana, tablaCliente.getIndiceSeleccionado());
            dialogCliente.setVisible(true);
            tablaCliente.setElementos(controladora.getListaClientes());
        });

        botonConsultar.setFont(font);
        botonConsultar.setBackground(verdeOscuro);
        botonConsultar.setForeground(verdeClaro);

        botonConsultar.addActionListener(e -> {
            int pos = tablaCliente.getIndiceSeleccionado();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.");
                return;
            }
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Consultar Cliente",
                    ventana, tablaCliente.getIndiceSeleccionado());
            dialogCliente.setVisible(true);
        });

        botonCrear.setFont(font);
        botonCrear.setBackground(verdeOscuro);
        botonCrear.setForeground(verdeClaro);

        botonCrear.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            DialogFormularioCliente dialogCliente = new DialogFormularioCliente(Colores.VERDE,
                    Colores.VERDE_OSCURO,
                    Colores.HUESO,
                    Colores.VERDE_CLARO,
                    Fuentes.GENERAL,
                    "Crear Cliente",
                    ventana,-1);
            dialogCliente.setVisible(true);
            tablaCliente.setElementos(controladora.getListaClientes());
        });

        seccionBotones.add(botonBorrar);
        seccionBotones.add(botonModificar);
        seccionBotones.add(botonConsultar);
        seccionBotones.add(botonCrear);

        add(titulo, BorderLayout.NORTH);
        add(tablaCliente, BorderLayout.CENTER);
        add(seccionBotones, BorderLayout.SOUTH);
    }
}
