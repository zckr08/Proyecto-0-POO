
package display.dialogs;

import control.Controladora;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DialogEstablecerHorario extends JDialog {

    public DialogEstablecerHorario(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font, String opcion, Window propietario) {
        super(propietario, opcion, ModalityType.APPLICATION_MODAL);
        Controladora controladora = Controladora.getInstance();
        String[] diasSemana = {
                "Domingo:", "Lunes:", "Martes:", "Miércoles:",
                "Jueves:", "Viernes:", "Sábado:"
        };

        JTextField[][] camposHorario = new JTextField[7][2];

        JLabel titulo = new JLabel(opcion, SwingConstants.CENTER);
        titulo.setForeground(verdeClaro);
        titulo.setBackground(verdeOscuro);
        titulo.setOpaque(true);
        titulo.setFont(font);

        for (int i = 0; i < 7; i++) {
            camposHorario[i][0] = new JTextField();
            camposHorario[i][1] = new JTextField();

            camposHorario[i][0].setFont(font);
            camposHorario[i][0].setForeground(verdeOscuro);

            camposHorario[i][1].setFont(font);
            camposHorario[i][1].setForeground(verdeOscuro);
        }

        setSize(756, 500);
        setLocationRelativeTo(propietario);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(8, 3, 10, 10));
        formulario.setBackground(verdeClaro);

        formulario.add(new JLabel("Día"));
        formulario.add(new JLabel("Hora de apertura (HH:mm)"));
        formulario.add(new JLabel("Hora de cierre (HH:mm)"));

        for (int i = 0; i < 7; i++) {
            formulario.add(new JLabel(diasSemana[i]));
            formulario.add(camposHorario[i][0]);
            formulario.add(camposHorario[i][1]);
        }

        JButton botonConfirmar = new JButton("Confirmar");

        botonConfirmar.setFont(font);
        botonConfirmar.setBackground(verdeOscuro);
        botonConfirmar.setForeground(verdeClaro);

        botonConfirmar.addActionListener(e -> {
            try {
                LocalDate fecha = LocalDate.now();

                for (int i = 0; i < 7; i++) {
                    String apertura = camposHorario[i][0].getText().trim();
                    String cierre = camposHorario[i][1].getText().trim();

                    if (apertura.isEmpty() && cierre.isEmpty()) {
                        continue;
                    }

                    if (apertura.isEmpty() || cierre.isEmpty()) {
                        throw new Exception("Complete ambas horas para " + diasSemana[i] + ".");
                    }

                    LocalTime horaApertura = LocalTime.parse(apertura);
                    LocalTime horaCierre = LocalTime.parse(cierre);

                    LocalDateTime fechaApertura = LocalDateTime.of(fecha, horaApertura);
                    LocalDateTime fechaCierre = LocalDateTime.of(fecha, horaCierre);

                    int numDia = (i == 0) ? 7 : i;

                    controladora.establecerHorario(numDia, fechaApertura, fechaCierre);
                }

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Verifique las horas ingresadas.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(formulario, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);
        add(botonConfirmar, BorderLayout.SOUTH);
    }
}
