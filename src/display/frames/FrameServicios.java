package display.frames;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrameServicios extends JFrame {
    private Controladora controladora;
    private JTable tabla;
    private DefaultTableModel modelo;
    private List<String> servicios;

    public FrameServicios() {
        controladora = Controladora.getInstance();

        setTitle("Servicios - Barbería");
        setSize(700, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Colores.VERDE);

        JLabel titulo = new JLabel("Servicios", SwingConstants.CENTER);
        titulo.setFont(Fuentes.CALENDARIO);
        titulo.setForeground(Colores.VERDE_CLARO);
        titulo.setBackground(Colores.VERDE_OSCURO);
        titulo.setOpaque(true);
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"#", "Servicio", "#", "Servicio"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setFont(Fuentes.CALENDARIO);
        tabla.setRowHeight(26);
        tabla.setBackground(Colores.VERDE_CLARO);
        tabla.setForeground(Colores.VERDE_OSCURO);
        tabla.setGridColor(Colores.VERDE_OSCURO);
        tabla.setSelectionBackground(Colores.HUESO);
        tabla.setSelectionForeground(Colores.VERDE_OSCURO);
        tabla.setCellSelectionEnabled(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setTableHeader(null);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(270);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(35);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(270);

        JScrollPane desplazamiento = new JScrollPane(tabla);
        desplazamiento.setBorder(BorderFactory.createEmptyBorder());
        desplazamiento.getViewport().setBackground(Colores.VERDE);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(Colores.VERDE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(15, 12, 10, 12));
        panelCentral.add(desplazamiento, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        JButton botonBorrar = crearBoton("Borrar Servicio");
        JButton botonModificar = crearBoton("Modificar Servicio");
        JButton botonConsultar = crearBoton("Consultar Servicio");
        JButton botonCrear = crearBoton("Crear Servicio");

        JPanel primeraFila = new JPanel(new GridLayout(1, 3, 12, 0));
        primeraFila.setBackground(Colores.VERDE);
        primeraFila.add(botonBorrar);
        primeraFila.add(botonModificar);
        primeraFila.add(botonConsultar);

        JPanel segundaFila = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 3));
        segundaFila.setBackground(Colores.VERDE);
        segundaFila.add(botonCrear);

        JLabel indicacion = new JLabel("Seleccione el servicio a modificar, borrar o consultar", SwingConstants.CENTER);
        indicacion.setFont(Fuentes.CALENDARIO);
        indicacion.setForeground(Colores.VERDE_CLARO);

        JPanel parteInferior = new JPanel(new GridLayout(3, 1, 0, 3));
        parteInferior.setBackground(Colores.VERDE);
        parteInferior.setBorder(BorderFactory.createEmptyBorder(5, 14, 6, 14));
        parteInferior.add(primeraFila);
        parteInferior.add(segundaFila);
        parteInferior.add(indicacion);
        add(parteInferior, BorderLayout.SOUTH);

        botonCrear.addActionListener(e -> abrirFormulario("Crear Servicio", -1));
        botonBorrar.addActionListener(e -> abrirConSeleccion("Borrar Servicio"));
        botonModificar.addActionListener(e -> abrirConSeleccion("Modificar Servicio"));
        botonConsultar.addActionListener(e -> abrirConSeleccion("Consultar Servicio"));

        actualizarTabla();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(Fuentes.CALENDARIO);
        boton.setBackground(Colores.VERDE_OSCURO);
        boton.setForeground(Colores.VERDE_CLARO);
        boton.setFocusPainted(false);
        return boton;
    }

    private void actualizarTabla() {
        servicios = controladora.getListaServicios();
        modelo.setRowCount(0);

        int mitad = (servicios.size() + 1) / 2;
        for (int i = 0; i < mitad; i++) {
            int derecha = i + mitad;
            Object[] fila = {i + 1, servicios.get(i), null, null};

            if (derecha < servicios.size()) {
                fila[2] = derecha + 1;
                fila[3] = servicios.get(derecha);
            }

            modelo.addRow(fila);
        }
    }

    private int obtenerSeleccion() {
        int fila = tabla.getSelectedRow();
        int columna = tabla.getSelectedColumn();

        if (fila == -1 || columna == -1) {
            return -1;
        }

        int mitad = (servicios.size() + 1) / 2;
        int posicion = fila;

        if (columna >= 2) {
            posicion += mitad;
        }

        if (posicion >= servicios.size()) {
            return -1;
        }

        return posicion;
    }

    private void abrirConSeleccion(String opcion) {
        int posicion = obtenerSeleccion();
        if (posicion == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.");
            return;
        }
        abrirFormulario(opcion, posicion);
    }

    private void abrirFormulario(String opcion, int posicion) {
        JDialog ventana = new JDialog(this, opcion, true);
        ventana.setSize(420, 220);
        ventana.setLocationRelativeTo(this);
        ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        JLabel titulo = new JLabel(opcion, SwingConstants.CENTER);
        titulo.setFont(Fuentes.CALENDARIO);
        titulo.setForeground(Colores.VERDE_CLARO);
        titulo.setBackground(Colores.VERDE_OSCURO);
        titulo.setOpaque(true);

        JLabel etiqueta = new JLabel("Descripción Servicio:");
        etiqueta.setFont(Fuentes.CALENDARIO);
        etiqueta.setForeground(Colores.VERDE_OSCURO);

        JTextField descripcion = new JTextField();
        descripcion.setFont(Fuentes.CALENDARIO);
        descripcion.setForeground(Colores.VERDE_OSCURO);

        if (posicion >= 0) {
            descripcion.setText(servicios.get(posicion));
        }

        if (opcion.equals("Consultar Servicio") || opcion.equals("Borrar Servicio")) {
            descripcion.setEditable(false);
        }

        JPanel formulario = new JPanel(new GridLayout(2, 1, 0, 8));
        formulario.setBackground(Colores.VERDE_CLARO);
        formulario.setBorder(BorderFactory.createEmptyBorder(18, 16, 18, 16));
        formulario.add(etiqueta);
        formulario.add(descripcion);

        JButton botonConfirmar = crearBoton("Confirmar");
        if (opcion.equals("Consultar Servicio")) {
            botonConfirmar.setText("Salir");
        } else if (opcion.equals("Borrar Servicio")) {
            botonConfirmar.setText("Borrar");
        }

        botonConfirmar.addActionListener(e -> {
            try {
                if (opcion.equals("Crear Servicio")) {
                    controladora.crearServicio(descripcion.getText());
                } else if (opcion.equals("Modificar Servicio")) {
                    controladora.modificarServicio(posicion, descripcion.getText());
                } else if (opcion.equals("Borrar Servicio")) {
                    int respuesta = JOptionPane.showConfirmDialog(ventana,
                            "¿Desea borrar este servicio?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (respuesta != JOptionPane.YES_OPTION) {
                        return;
                    }
                    controladora.borrarServicio(posicion);
                }
                actualizarTabla();
                ventana.dispose();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(ventana, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventana.add(titulo, BorderLayout.NORTH);
        ventana.add(formulario, BorderLayout.CENTER);
        ventana.add(botonConfirmar, BorderLayout.SOUTH);
        ventana.setVisible(true);
    }
}
