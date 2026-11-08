package display.dialogs;

import control.Controladora;
import display.components.PanelTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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

        for (JTextField campo : new JTextField[]{campoFecha, campoHora, campoCliente, campoServicio}) {
            campo.setFont(font);
            campo.setForeground(verdeOscuro);
        }
        campoEstado.setFont(font);
        campoEstado.setForeground(verdeOscuro);

        if (opcion.equals("Modificar Cita") || opcion.equals("Consultar Cita") || opcion.equals("Se ha borrado una cita!")) {
            LocalDateTime fechaHora = controladora.getCita(index).getFechaHora();
            campoFecha.setText(fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
            campoHora.setText(fechaHora.format(DateTimeFormatter.ofPattern("HH:mm")));
            campoCliente.setText(controladora.getCita(index).getCliente().getNombre());
            campoServicio.setText(controladora.getCita(index).getServicio().toString());
            campoEstado.setText(controladora.getCita(index).getEstado().toString());
        }

        if (opcion.equals("Crear Cita")) {
            ponerEjemplo(campoFecha, "dd/MM/aaaa", verdeOscuro);
            ponerEjemplo(campoHora, "HH:mm", verdeOscuro);
            campoCliente.setText("");
            campoServicio.setText("");

            campoCliente.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarCliente = new JDialog(propietario, "Seleccione un cliente", ModalityType.APPLICATION_MODAL);
                    ventanaCambiarCliente.setSize(756, 369);
                    ventanaCambiarCliente.setLocationRelativeTo(propietario);
                    ventanaCambiarCliente.setLayout(new BorderLayout());

                    PanelTabla tablaCliente = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);
                    tablaCliente.setElementos(controladora.getListaClientes());

                    JButton botonSeleccionar = new JButton("Seleccionar");
                    botonSeleccionar.setFont(font);
                    botonSeleccionar.setBackground(verdeOscuro);
                    botonSeleccionar.setForeground(verdeClaro);
                    botonSeleccionar.addActionListener(ev -> {
                        int pos = tablaCliente.getIndiceSeleccionado();
                        if (pos == -1) {
                            JOptionPane.showMessageDialog(ventanaCambiarCliente, "Seleccione un cliente.");
                            return;
                        }
                        seleccionCliente = pos;
                        campoCliente.setText(controladora.obtenerCliente(pos).getNombre());
                        ventanaCambiarCliente.dispose();
                    });

                    ventanaCambiarCliente.add(new JLabel("Seleccione el cliente para la cita", SwingConstants.CENTER), BorderLayout.NORTH);
                    ventanaCambiarCliente.add(tablaCliente, BorderLayout.CENTER);
                    ventanaCambiarCliente.add(botonSeleccionar, BorderLayout.SOUTH);
                    ventanaCambiarCliente.setVisible(true);
                }
            });
        }

        if (opcion.equals("Crear Cita") || opcion.equals("Modificar Cita")) {
            campoServicio.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarServicio = new JDialog(propietario, "Seleccione un servicio", ModalityType.APPLICATION_MODAL);
                    ventanaCambiarServicio.setSize(756, 369);
                    ventanaCambiarServicio.setLocationRelativeTo(propietario);
                    ventanaCambiarServicio.setLayout(new BorderLayout());

                    PanelTabla tablaServicio = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);
                    tablaServicio.setElementos(controladora.getListaServicios());

                    JButton botonSeleccionar = new JButton("Seleccionar");
                    botonSeleccionar.setFont(font);
                    botonSeleccionar.setBackground(verdeOscuro);
                    botonSeleccionar.setForeground(verdeClaro);
                    botonSeleccionar.addActionListener(ev -> {
                        int pos = tablaServicio.getIndiceSeleccionado();
                        if (pos == -1) {
                            JOptionPane.showMessageDialog(ventanaCambiarServicio, "Seleccione un servicio.");
                            return;
                        }
                        try {
                            seleccionServicio = pos;
                            campoServicio.setText(controladora.obtenerServicio(pos).getDescripcion());
                            ventanaCambiarServicio.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ventanaCambiarServicio, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    ventanaCambiarServicio.add(new JLabel("Seleccione el servicio para la cita", SwingConstants.CENTER), BorderLayout.NORTH);
                    ventanaCambiarServicio.add(tablaServicio, BorderLayout.CENTER);
                    ventanaCambiarServicio.add(botonSeleccionar, BorderLayout.SOUTH);
                    ventanaCambiarServicio.setVisible(true);
                }
            });
        }

        campoCliente.setEditable(false);
        campoServicio.setEditable(false);
        if (opcion.equals("Consultar Cita") || opcion.equals("Se ha borrado una cita!")) {
            campoFecha.setEditable(false);
            campoHora.setEditable(false);
        }

        setSize(756, 410);
        setLocationRelativeTo(propietario);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        boolean esCrear = opcion.equals("Crear Cita");
        JPanel formulario = new JPanel(new GridLayout(esCrear ? 4 : 5, 2, 10, 15));
        formulario.setBackground(verdeClaro);
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));

        formulario.add(new JLabel("Fecha (dd/MM/aaaa):"));
        formulario.add(campoFecha);
        formulario.add(new JLabel("Hora (HH:mm):"));
        formulario.add(campoHora);
        formulario.add(new JLabel("Cliente (seleccionar):"));
        formulario.add(campoCliente);
        formulario.add(new JLabel("Servicio (seleccionar):"));
        formulario.add(campoServicio);
        if (!esCrear) {
            formulario.add(new JLabel("Estado:"));
            formulario.add(campoEstado);
        }

        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setFont(font);
        botonConfirmar.setBackground(verdeOscuro);
        botonConfirmar.setForeground(verdeClaro);

        botonConfirmar.addActionListener(e -> {
            if (opcion.equals("Consultar Cita") || opcion.equals("Se ha borrado una cita!")) {
                dispose();
                return;
            }

            try {
                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
                LocalDate fecha = LocalDate.parse(campoFecha.getText().trim(), formatoFecha);
                LocalTime hora = LocalTime.parse(campoHora.getText().trim(), formatoHora);
                LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

                if (opcion.equals("Crear Cita")) {
                    if (seleccionCliente == null) {
                        JOptionPane.showMessageDialog(this, "Seleccione un cliente haciendo clic en el campo Cliente.");
                        return;
                    }
                    if (seleccionServicio == null) {
                        JOptionPane.showMessageDialog(this, "Seleccione un servicio haciendo clic en el campo Servicio.");
                        return;
                    }
                    controladora.crearCita(fechaHora, seleccionCliente, controladora.obtenerServicio(seleccionServicio));
                } else if (opcion.equals("Modificar Cita")) {
                    if (seleccionServicio != null) {
                        controladora.modificarCita(index, fechaHora, seleccionServicio);
                    } else {
                        controladora.modificarCita(controladora.getCita(index), fechaHora);
                    }
                }
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Revise la fecha y la hora.\nFecha: dd/MM/aaaa (ejemplo: 21/03/2027).\nHora: HH:mm (ejemplo: 11:30).",
                        "Fecha u hora incorrecta", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo guardar la cita", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonConfirmarCita = new JButton("Confirmar cita");
        JButton botonCancelarCita = new JButton("Cancelar cita");
        for (JButton boton : new JButton[]{botonConfirmarCita, botonCancelarCita}) {
            boton.setFont(font);
            boton.setBackground(verdeOscuro);
            boton.setForeground(verdeClaro);
        }
        botonConfirmarCita.addActionListener(e -> {
            try {
                controladora.confirmarCita(index);
                campoEstado.setText(controladora.getCita(index).getEstado().toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        botonCancelarCita.addActionListener(e -> {
            try {
                controladora.cancelarCita(index);
                campoEstado.setText(controladora.getCita(index).getEstado().toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(formulario, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);
        if (opcion.equals("Modificar Cita")) {
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
            panelBotones.setBackground(verdeClaro);
            panelBotones.add(botonConfirmarCita);
            panelBotones.add(botonCancelarCita);
            panelBotones.add(botonConfirmar);
            add(panelBotones, BorderLayout.SOUTH);
        } else {
            add(botonConfirmar, BorderLayout.SOUTH);
        }
    }

    private void ponerEjemplo(JTextField campo, String ejemplo, Color colorNormal) {
        campo.setText(ejemplo);
        campo.setForeground(Color.GRAY);
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(ejemplo)) {
                    campo.setText("");
                    campo.setForeground(colorNormal);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(ejemplo);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }
}
