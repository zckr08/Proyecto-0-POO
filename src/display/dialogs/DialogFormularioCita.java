package display.dialogs;

import control.Controladora;
import display.components.PanelTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DialogFormularioCita extends JDialog {
    private Integer seleccionCliente;
    private Integer seleccionServicio;

    public DialogFormularioCita(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font, String opcion, Window propietario, Integer index) {
        super(propietario, opcion, ModalityType.APPLICATION_MODAL);
        seleccionCliente = null;
        seleccionServicio = null;
        Controladora controladora = Controladora.getInstance();

        JLabel titulo = new JLabel(opcion, SwingConstants.CENTER);
        titulo.setFont(font);
        titulo.setOpaque(true);
        titulo.setBackground(verdeOscuro);
        titulo.setForeground(verdeClaro);


        JTextField campoFecha = new JTextField();
        JTextField campoHora = new JTextField();
        JTextField campoCliente = new JTextField();
        JTextField campoServicio = new JTextField();
        JLabel campoEstado = new JLabel();

        campoFecha.setFont(font);
        campoFecha.setForeground(verdeOscuro);

        campoHora.setFont(font);
        campoHora.setForeground(verdeOscuro);

        campoCliente.setFont(font);
        campoCliente.setForeground(verdeOscuro);

        campoServicio.setFont(font);
        campoServicio.setForeground(verdeOscuro);

        campoEstado.setFont(font);
        campoEstado.setForeground(verdeOscuro);

        if (opcion.equals("Modificar Cita")) {
            LocalDateTime fechaHora = controladora.getCita(index).getFechaHora();
            LocalDate fecha = fechaHora.toLocalDate();
            LocalTime hora = fechaHora.toLocalTime();
            campoFecha.setText(fecha.toString());
            campoHora.setText(hora.toString());
            campoCliente.setText(controladora.getCita(index).getCliente().getNombre());
            campoServicio.setText(controladora.getCita(index).getServicio().toString());
            campoEstado.setText(controladora.getCita(index).getEstado().toString());

            campoServicio.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarServicio = new JDialog(
                            propietario,
                            "Seleccione un servicio",
                            Dialog.ModalityType.APPLICATION_MODAL
                    );

                    ventanaCambiarServicio.setSize(756, 369);
                    ventanaCambiarServicio.setLocationRelativeTo(propietario);
                    ventanaCambiarServicio.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ventanaCambiarServicio.setLayout(new BorderLayout());

                    PanelTabla tablaServicio = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);

                    JButton botonSeleccionar = new JButton("Seleccionar");
                    botonSeleccionar.setFont(font);
                    botonSeleccionar.setBackground(verdeOscuro);
                    botonSeleccionar.setForeground(verdeClaro);

                    botonSeleccionar.addActionListener(ev -> {
                        seleccionServicio = tablaServicio.getIndiceSeleccionado();

                        if (seleccionServicio != -1) {
                            try {
                                campoServicio.setText(controladora.obtenerServicio(seleccionServicio).getDescripcion());
                                ventanaCambiarServicio.dispose();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(ventanaCambiarServicio, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(ventanaCambiarServicio, "Seleccione un servicio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    });
                    ventanaCambiarServicio.add(botonSeleccionar, BorderLayout.SOUTH);
                    JLabel title = new JLabel("Seleccione el servicio para la cita");
                    ventanaCambiarServicio.add(tablaServicio, BorderLayout.CENTER);
                    ventanaCambiarServicio.add(title, BorderLayout.NORTH);
                    tablaServicio.setElementos(controladora.listaServicios());
                    ventanaCambiarServicio.setVisible(true);
                }
            });

            campoFecha.setEditable(true);
            campoCliente.setEditable(false);
            campoServicio.setEditable(false);
            campoHora.setEditable(true);
        }

        else if (opcion.equals("Crear Cita")) {
            campoFecha.setText("");
            campoHora.setText("");
            campoCliente.setText("seleccionar cliente");
            campoServicio.setText("seleccionar servicio");

            campoCliente.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarCliente = new JDialog(propietario, "Seleccione un cliente", Dialog.ModalityType.APPLICATION_MODAL);
                    ventanaCambiarCliente.setSize(756, 369);
                    ventanaCambiarCliente.setLocationRelativeTo(propietario);
                    ventanaCambiarCliente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ventanaCambiarCliente.setLayout(new BorderLayout());

                    PanelTabla tablaCliente = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);
                    JButton botonSeleccionar = new JButton("Seleccionar");
                    botonSeleccionar.setFont(font);
                    botonSeleccionar.setBackground(verdeOscuro);
                    botonSeleccionar.setForeground(verdeClaro);
                    botonSeleccionar.addActionListener(ev -> {seleccionCliente = tablaCliente.getIndiceSeleccionado();
                        if (seleccionCliente != -1) {
                            campoCliente.setText(controladora.obtenerCliente(seleccionCliente).getNombre());
                            ventanaCambiarCliente.dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(ventanaCambiarCliente, "Seleccione un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    });

                    ventanaCambiarCliente.add(botonSeleccionar, BorderLayout.SOUTH);

                    JLabel title = new JLabel("Seleccione el cliente para la cita");
                    ventanaCambiarCliente.add(tablaCliente, BorderLayout.CENTER);
                    ventanaCambiarCliente.add(title, BorderLayout.NORTH);
                    tablaCliente.setElementos(controladora.getListaClientes());
                    ventanaCambiarCliente.setVisible(true);
                }
            });

            campoServicio.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarServicio = new JDialog(
                            propietario,
                            "Seleccione un servicio",
                            Dialog.ModalityType.APPLICATION_MODAL
                    );

                    ventanaCambiarServicio.setSize(756, 369);
                    ventanaCambiarServicio.setLocationRelativeTo(propietario);
                    ventanaCambiarServicio.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ventanaCambiarServicio.setLayout(new BorderLayout());

                    PanelTabla tablaServicio = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);

                    JButton botonSeleccionar = new JButton("Seleccionar");
                    botonSeleccionar.setFont(font);
                    botonSeleccionar.setBackground(verdeOscuro);
                    botonSeleccionar.setForeground(verdeClaro);

                    botonSeleccionar.addActionListener(ev -> {
                        seleccionServicio = tablaServicio.getIndiceSeleccionado();

                        if (seleccionServicio != -1) {
                            try {
                                campoServicio.setText(controladora.obtenerServicio(seleccionServicio).getDescripcion());
                                ventanaCambiarServicio.dispose();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(ventanaCambiarServicio, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(ventanaCambiarServicio, "Seleccione un servicio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    });
                    ventanaCambiarServicio.add(botonSeleccionar, BorderLayout.SOUTH);
                    JLabel title = new JLabel("Seleccione el servicio para la cita");
                    ventanaCambiarServicio.add(tablaServicio, BorderLayout.CENTER);
                    ventanaCambiarServicio.add(title, BorderLayout.NORTH);
                    tablaServicio.setElementos(controladora.listaServicios());
                    ventanaCambiarServicio.setVisible(true);
                }
            });

            campoFecha.setEditable(true);
            campoHora.setEditable(true);
            campoCliente.setEditable(false);
            campoServicio.setEditable(false);
        }

        else if (opcion.equals("Consultar Cita")) {
            LocalDateTime fechaHora = controladora.getCita(index).getFechaHora();
            LocalDate fecha = fechaHora.toLocalDate();
            LocalTime hora = fechaHora.toLocalTime();
            campoFecha.setText(fecha.toString());
            campoHora.setText(hora.toString());
            campoCliente.setText(controladora.getCita(index).getCliente().getNombre());
            campoServicio.setText(controladora.getCita(index).getServicio().toString());
            campoEstado.setText(controladora.getCita(index).getEstado().toString());

            campoFecha.setEditable(false);
            campoHora.setEditable(false);
            campoCliente.setEditable(false);
            campoServicio.setEditable(false);
        }

        else if (opcion.equals("Se ha borrado una cita!")) {
            LocalDateTime fechaHora = controladora.getCita(index).getFechaHora();
            LocalDate fecha = fechaHora.toLocalDate();
            LocalTime hora = fechaHora.toLocalTime();
            campoFecha.setText(fecha.toString());
            campoHora.setText(hora.toString());
            campoCliente.setText(controladora.getCita(index).getCliente().getNombre());
            campoServicio.setText(controladora.getCita(index).getServicio().toString());
            campoEstado.setText(controladora.getCita(index).getEstado().toString());

            campoFecha.setEditable(false);
            campoHora.setEditable(false);
            campoCliente.setEditable(false);
            campoServicio.setEditable(false);
        }

        setSize(756, 369);
        setLocationRelativeTo(propietario);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());


        boolean esCrear = opcion.equals("Crear Cita");
        JPanel formulario = new JPanel(new GridLayout(esCrear ? 4 : 5, 2, 10, 15));

        formulario.setBackground(verdeClaro);

        JButton botonConfirmar = new JButton("Aceptar");


        botonConfirmar.setFont(font);
        botonConfirmar.setBackground(verdeOscuro);
        botonConfirmar.setForeground(verdeClaro);

        botonConfirmar.addActionListener(e -> {
            LocalDate fecha;
            LocalTime hora;
            LocalDateTime fechaHora = null;
            String cliente;
            String servicio;
            try {
                fecha = LocalDate.parse(campoFecha.getText());
                hora = LocalTime.parse(campoHora.getText());
                fechaHora = fecha.atTime(hora);
                cliente = campoCliente.getText();
                servicio = campoServicio.getText();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (opcion.equals("Modificar Cita")) {
                try {
                    if (seleccionServicio != null) {
                        controladora.modificarCita(index, fechaHora, seleccionServicio);
                    } else {
                        controladora.modificarCita(controladora.getCita(index), fechaHora);
                    }
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Crear Cita")) {
                try {
                    if (seleccionCliente == null || seleccionServicio == null)
                        throw new Exception("Al crear una nueva cita tiene que seleccionarse un servicio y un cliente");
                    controladora.crearCita(fechaHora, seleccionCliente, controladora.obtenerServicio(seleccionServicio));
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Consultar Cita")) {
                try {
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Se ha borrado una cita!")) {
                try {
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton botonConfirmarCita = new JButton("Confirmar cita");

        botonConfirmarCita.setFont(font);
        botonConfirmarCita.setBackground(verdeOscuro);
        botonConfirmarCita.setForeground(verdeClaro);

        botonConfirmarCita.addActionListener(e -> {
            try {
                controladora.confirmarCita(index);
                campoEstado.setText(controladora.getCita(index).getEstado().toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        formulario.add(new JLabel("Fecha (aaaa-mm-dd):"));
        formulario.add(campoFecha);
        formulario.add(new JLabel("Hora(HH:mm):"));
        formulario.add(campoHora);
        formulario.add(new JLabel("Cliente: "));
        formulario.add(campoCliente);
        formulario.add(new JLabel("Servicio: "));
        formulario.add(campoServicio);
        if (!esCrear) {
            formulario.add(new JLabel("Estado: "));
            formulario.add(campoEstado);
        }

        add(formulario, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);
        if (opcion.equals("Modificar Cita")) {
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
            panelBotones.setBackground(verdeClaro);
            panelBotones.add(botonConfirmarCita);
            panelBotones.add(botonConfirmar);
            add(panelBotones, BorderLayout.SOUTH);
        }
        else {
            add(botonConfirmar, BorderLayout.SOUTH);
        }
    }
}

