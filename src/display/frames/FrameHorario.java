package display.frames;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;

import javax.swing.*;
import java.awt.*;

public class FrameHorario extends JFrame {

    private Controladora controladora;
    private JLabel[] horarios;
    private String[] dias;

    public FrameHorario() {
        controladora = Controladora.getInstance();
        horarios = new JLabel[7];
        dias = new String[]{"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        setTitle("Horario de atención - Barbería");
        setSize(620, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Colores.VERDE);

        JLabel titulo = new JLabel("Horario de atención", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(Colores.VERDE_OSCURO);
        titulo.setForeground(Colores.TEXTO);
        titulo.setFont(Fuentes.GENERAL);
        add(titulo, BorderLayout.NORTH);

        JPanel panelHorarios = new JPanel(new GridLayout(7, 2, 10, 10));
        panelHorarios.setBackground(Colores.VERDE_CLARO);
        panelHorarios.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        for (int i = 0; i < 7; i++) {
            JLabel nombreDia = new JLabel(dias[i]);
            nombreDia.setForeground(Colores.VERDE_OSCURO);
            nombreDia.setFont(Fuentes.CALENDARIO);
            horarios[i] = new JLabel();
            horarios[i].setForeground(Colores.VERDE_OSCURO);
            horarios[i].setFont(Fuentes.CALENDARIO);
            panelHorarios.add(nombreDia);
            panelHorarios.add(horarios[i]);
        }

        JButton botonEstablecer = new JButton("Establecer horario");
        JButton botonSalir = new JButton("Salir");

        for (JButton boton : new JButton[]{botonEstablecer, botonSalir}) {
            boton.setBackground(Colores.VERDE_OSCURO);
            boton.setForeground(Colores.TEXTO);
            boton.setFont(Fuentes.CALENDARIO);
        }

        botonEstablecer.addActionListener(e -> {
            FrameEstablecerHorario ventana = new FrameEstablecerHorario(this);
            ventana.setVisible(true);
        });
        botonSalir.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Colores.VERDE);
        panelBotones.add(botonEstablecer);
        panelBotones.add(botonSalir);

        add(panelHorarios, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        actualizarHorario();
    }

    public void actualizarHorario() {
        for (int i = 0; i < 7; i++) {
            int numeroDia = i == 0 ? 7 : i;
            try {
                String horario = controladora.consultarHorario(numeroDia);
                if (horario.startsWith("No hay horario")) {
                    horario = "Sin horario";
                }
                horarios[i].setText(horario);
            } catch (Exception e) {
                horarios[i].setText("No disponible");
            }
        }
    }
}
