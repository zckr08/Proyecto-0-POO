package display.dialogs;

import control.Controladora;
import logic.Cita;
import logic.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DialogFormularioCita extends JDialog {

    public DialogFormularioCita(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font, String opcion, Window propietario, Cita cita) {
        super(propietario, opcion, ModalityType.APPLICATION_MODAL);

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
            LocalDateTime fechaHora = cita.getFechaHora();
            LocalDate fecha = fechaHora.toLocalDate();
            LocalTime hora = fechaHora.toLocalTime();
            campoFecha.setText(fecha.toString());
            campoHora.setText(hora.toString());
            campoCliente.setText(cita.getCliente().getNombre());
            campoServicio.setText(cita.getServicio().toString());
            campoEstado.setText(cita.getEstado().toString());

            campoFecha.setEditable(true);
            campoHora.setEditable(true);
            campoCliente.setEditable(true);
            campoServicio.setEditable(true);
            campoEstado.setEditable(true);
        }

        else if (opcion.equals("Crear Cita")) {
            campoFecha.setText("");
            campoHora.setText("");
            campoCliente.setText("");
            campoServicio.setText("");
            campoEstado.setText("");

            campoFecha.setEditable(true);
            campoHora.setEditable(true);
            campoCliente.setEditable(true);
            campoServicio.setEditable(true);
            campoEstado.setEditable(true);
        }

        else if (opcion.equals("Consultar Cita")) {
            LocalDateTime fechaHora = cita.getFechaHora();
            LocalDate fecha = fechaHora.toLocalDate();
            LocalTime hora = fechaHora.toLocalTime();
            campoFecha.setText(fecha.toString());
            campoHora.setText(hora.toString());
            campoCliente.setText(cita.getCliente().getNombre());
            campoServicio.setText(cita.getServicio().toString());
            campoEstado.setText(cita.getEstado().toString());

            campoFecha.setEditable(false);
            campoHora.setEditable(false);
            campoCliente.setEditable(false);
            campoServicio.setEditable(false);
            campoEstado.setEditable(false);
        }

        else if (opcion.equals("Se ha borrado una cita!")) {
            LocalDateTime fechaHora = cita.getFechaHora();
            LocalDate fecha = fechaHora.toLocalDate();
            LocalTime hora = fechaHora.toLocalTime();
            campoFecha.setText(fecha.toString());
            campoHora.setText(hora.toString());
            campoCliente.setText(cita.getCliente().getNombre());
            campoServicio.setText(cita.getServicio().toString());
            campoEstado.setText(cita.getEstado().toString());

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
            String fecha = campoFecha.getText();
            String hora = campoHora.getText();
            String cliente = campoCliente.getText();
            String servicio = campoServicio.getText();
            String estado = campoEstado.getText();

            if (opcion.equals("Modificar Cita")) {
                try {
                    controladora.
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Crear Cita")) {
                try {
                    controladora.
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Consultar Cita")) {
                try {
                    controladora.
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Se ha borrado una cita!")) {
                try {
                    controladora.
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

