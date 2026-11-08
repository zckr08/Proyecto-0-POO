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
        titulo.setForeground(verdeClaro);
        titulo.setBackground(verdeOscuro);
        titulo.setFont(font);

        JTextField campoFecha = new JTextField();
        JTextField campoHora = new JTextField();
        JTextField campoCliente = new JTextField();
        JTextField campoServicio = new JTextField();
        JTextField campoEstado = new JTextField();

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
                    JDialog ventanaCambiarServicio = new JDialog();
                    ventanaCambiarServicio.setSize(756, 369);
                    ventanaCambiarServicio.setLocationRelativeTo(propietario);
                    ventanaCambiarServicio.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ventanaCambiarServicio.setLayout(new BorderLayout());

                    PanelTabla tablaServicio = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);

                    JLabel title = new JLabel("Seleccione el servicio para la cita");
                    ventanaCambiarServicio.add(tablaServicio, BorderLayout.CENTER);
                    ventanaCambiarServicio.add(title, BorderLayout.NORTH);
                    tablaServicio.setElementos(controladora.listaServicios());
                    seleccionServicio = tablaServicio.getIndiceSeleccionado();
                    try {
                        campoCliente.setText(controladora.obtenerServicio(seleccionServicio).getDescripcion());
                        dispose();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            campoFecha.setEditable(true);
            campoCliente.setEditable(false);
            campoServicio.setEditable(true);
            campoHora.setEditable(true);
            campoEstado.setEditable(true);
        }

        else if (opcion.equals("Crear Cita")) {
            campoFecha.setText("");
            campoHora.setText("");
            campoCliente.setText("");
            campoServicio.setText("");
            campoEstado.setText("");

            campoCliente.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarCliente = new JDialog();
                    ventanaCambiarCliente.setSize(756, 369);
                    ventanaCambiarCliente.setLocationRelativeTo(propietario);
                    ventanaCambiarCliente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ventanaCambiarCliente.setLayout(new BorderLayout());

                    PanelTabla tablaCliente = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);

                    JLabel title = new JLabel("Seleccione el cliente para la cita");
                    ventanaCambiarCliente.add(tablaCliente, BorderLayout.CENTER);
                    ventanaCambiarCliente.add(title, BorderLayout.NORTH);
                    tablaCliente.setElementos(controladora.getListaClientes());
                    seleccionCliente = tablaCliente.getIndiceSeleccionado();
                    campoCliente.setText(controladora.obtenerCliente(seleccionCliente).getNombre());
                    dispose();
                }
            });

            campoServicio.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JDialog ventanaCambiarServicio = new JDialog();
                    ventanaCambiarServicio.setSize(756, 369);
                    ventanaCambiarServicio.setLocationRelativeTo(propietario);
                    ventanaCambiarServicio.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    ventanaCambiarServicio.setLayout(new BorderLayout());

                    PanelTabla tablaServicio = new PanelTabla(verde, verdeOscuro, hueso, verdeClaro, font);

                    JLabel title = new JLabel("Seleccione el servicio para la cita");
                    ventanaCambiarServicio.add(tablaServicio, BorderLayout.CENTER);
                    ventanaCambiarServicio.add(title, BorderLayout.NORTH);
                    tablaServicio.setElementos(controladora.listaServicios());
                    seleccionServicio = tablaServicio.getIndiceSeleccionado();
                    try {
                        campoCliente.setText(controladora.obtenerServicio(seleccionServicio).getDescripcion());
                        dispose();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            campoFecha.setEditable(true);
            campoHora.setEditable(true);
            campoCliente.setEditable(false);
            campoServicio.setEditable(true);
            campoEstado.setEditable(true);
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
            campoEstado.setEditable(false);
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
            campoEstado.setEditable(false);
        }

        setSize(756, 369);
        setLocationRelativeTo(propietario);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());


        JPanel formulario = new JPanel(new GridLayout(3,2,10,15));

        formulario.setBackground(verdeClaro);

        JButton botonConfirmar = new JButton("Confirmar");

        botonConfirmar.setFont(font);
        botonConfirmar.setBackground(verdeOscuro);
        botonConfirmar.setForeground(verdeClaro);

        botonConfirmar.addActionListener(e -> {
            CharSequence fecha = campoFecha.getText();
            CharSequence hora = campoHora.getText();
            LocalDateTime fechaHora = LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(hora));
            String cliente = campoCliente.getText();
            String servicio = campoServicio.getText();
            String estado = campoEstado.getText();

            if (opcion.equals("Modificar Cita")) {
                try {
                    controladora.modificarCita(controladora.getCita(index), fechaHora, seleccionServicio != null ?  controladora.obtenerServicio(seleccionServicio) : controladora.getCita(index).getServicio());
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Crear Cita")) {
                try {
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

        formulario.add(new JLabel("Fecha:"));
        formulario.add(campoFecha);
        formulario.add(new JLabel("Hora:"));
        formulario.add(campoHora);
        formulario.add(new JLabel("Cliente: "));
        formulario.add(campoCliente);
        formulario.add(new JLabel("Servicio: "));
        formulario.add(campoServicio);
        formulario.add(new JLabel("Estado: "));
        formulario.add(campoEstado);

        add(formulario, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);
    }
}

