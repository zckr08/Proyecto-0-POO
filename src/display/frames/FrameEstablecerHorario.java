package display.frames;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class FrameEstablecerHorario extends JFrame {

    private JTextField[][] campos;
    private Controladora controladora;
    private FrameHorario ventanaHorario;
    private String[] dias;

    public FrameEstablecerHorario(FrameHorario ventanaHorario) {
        this.ventanaHorario = ventanaHorario;
        controladora = Controladora.getInstance();
        campos = new JTextField[7][2];
        dias = new String[]{"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        setTitle("Establecer horario - Barbería");
        setSize(740, 510);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(ventanaHorario);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Colores.VERDE);

        JLabel titulo = new JLabel("Establecer horario (HH:mm)", SwingConstants.CENTER);
        titulo.setFont(Fuentes.GENERAL);
        titulo.setOpaque(true);
        titulo.setBackground(Colores.VERDE_OSCURO);
        titulo.setForeground(Colores.TEXTO);
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(8, 3, 10, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        formulario.setBackground(Colores.VERDE_CLARO);

        formulario.add(crearEtiqueta("Día"));
        formulario.add(crearEtiqueta("Apertura"));
        formulario.add(crearEtiqueta("Cierre"));

        for (int i = 0; i < 7; i++) {
            formulario.add(crearEtiqueta(dias[i]));
            campos[i][0] = new JTextField();
            campos[i][1] = new JTextField();
            campos[i][0].setFont(Fuentes.CALENDARIO);
            campos[i][1].setFont(Fuentes.CALENDARIO);
            formulario.add(campos[i][0]);
            formulario.add(campos[i][1]);

            int numeroDia = i == 0 ? 7 : i;
            try {
                String horario = controladora.consultarHorario(numeroDia);
                String[] partes = horario.split(" - ");
                if (partes.length == 2) {
                    campos[i][0].setText(partes[0]);
                    campos[i][1].setText(partes[1]);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        JButton botonConfirmar = new JButton("Confirmar");
        JButton botonCancelar = new JButton("Cancelar");
        for (JButton boton : new JButton[]{botonConfirmar, botonCancelar}) {
            boton.setFont(Fuentes.CALENDARIO);
            boton.setBackground(Colores.VERDE_OSCURO);
            boton.setForeground(Colores.TEXTO);
        }

        botonConfirmar.addActionListener(e -> guardarHorario());
        botonCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel();
        botones.setBackground(Colores.VERDE);
        botones.add(botonConfirmar);
        botones.add(botonCancelar);

        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(Fuentes.CALENDARIO);
        etiqueta.setForeground(Colores.VERDE_OSCURO);
        return etiqueta;
    }

    private void guardarHorario() {
        try {
            LocalTime[] aperturas = new LocalTime[7];
            LocalTime[] cierres = new LocalTime[7];

            for (int i = 0; i < 7; i++) {
                String apertura = campos[i][0].getText().trim();
                String cierre = campos[i][1].getText().trim();

                if (apertura.isEmpty() && cierre.isEmpty()) {
                    continue;
                }
                if (apertura.isEmpty() || cierre.isEmpty()) {
                    throw new Exception("Complete la hora de apertura y cierre del " + dias[i].toLowerCase() + ".");
                }

                try {
                    aperturas[i] = LocalTime.parse(apertura);
                    cierres[i] = LocalTime.parse(cierre);
                } catch (DateTimeParseException ex) {
                    throw new Exception("La hora del " + dias[i].toLowerCase() + " no es válida. Use el formato HH:mm, por ejemplo 08:00.");
                }
                if (!aperturas[i].isBefore(cierres[i])) {
                    throw new Exception("La apertura del " + dias[i].toLowerCase() + " debe ser anterior al cierre.");
                }
            }

            LocalDate fecha = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                if (aperturas[i] != null) {
                    int numeroDia = i == 0 ? 7 : i;
                    LocalDateTime horaApertura = LocalDateTime.of(fecha, aperturas[i]);
                    LocalDateTime horaCierre = LocalDateTime.of(fecha, cierres[i]);
                    controladora.establecerHorario(numeroDia, horaApertura, horaCierre);
                }
            }

            ventanaHorario.actualizarHorario();
            JOptionPane.showMessageDialog(this, "Horario guardado correctamente.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Horario incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }
}
