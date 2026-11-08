package display.dialogs;

import control.Controladora;
import logic.Servicio;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DialogFormularioServicio extends JDialog {

    public DialogFormularioServicio(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font, String opcion, Window propietario, Integer index) {
        super(propietario, opcion, ModalityType.APPLICATION_MODAL);

        Controladora controladora = Controladora.getInstance();

        JLabel titulo = new JLabel(opcion, SwingConstants.CENTER);
        titulo.setForeground(verdeClaro);
        titulo.setBackground(verdeOscuro);
        titulo.setFont(font);

        JTextField campoDescripcion = new JTextField();

        campoDescripcion.setFont(font);
        campoDescripcion.setForeground(verdeOscuro);

        if (opcion.equals("Modificar servicio")) {
            try {
                campoDescripcion.setText(controladora.obtenerServicio(index).getDescripcion());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            campoDescripcion.setEditable(true);
        }

        else if (opcion.equals("Crear servicio")) {
            campoDescripcion.setText("");

            campoDescripcion.setEditable(true);
        }

        else if (opcion.equals("Consultar servicio")) {
            try {
                campoDescripcion.setText(controladora.obtenerServicio(index).getDescripcion());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            campoDescripcion.setEditable(false);
        }

        else if (opcion.equals("Se ha borrado un servicio!")) {
            try {
                campoDescripcion.setText(controladora.obtenerServicio(index).getDescripcion());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            campoDescripcion.setEditable(false);
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
            String descripcion = campoDescripcion.getText();

            if (opcion.equals("Modificar Servicio")) {
                try {
                    controladora.modificarServicio(index, descripcion);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Crear Servicio")) {
                try {
                    controladora.crearServicio(descripcion);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Consultar Servicio")) {
                try {
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Se ha borrado un servicio!")) {
                try {
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formulario.add(new JLabel("Servicio:"));
        formulario.add(campoDescripcion);

        add(formulario, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);
    }
}

