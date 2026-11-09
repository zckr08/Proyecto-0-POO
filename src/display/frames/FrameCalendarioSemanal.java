package display.frames;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import logic.Cita;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FrameCalendarioSemanal extends JFrame {

    private Controladora controladora;
    private LocalDate lunes;
    private JPanel panelDias;
    private JTextField campoFecha;
    private JLabel tituloSemana;
    private DateTimeFormatter formatoFecha;
    private DateTimeFormatter formatoHora;
    private String[] dias = {
            "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    };

    public FrameCalendarioSemanal() {
        this(LocalDate.now());
    }

    public FrameCalendarioSemanal(LocalDate fecha) {
        controladora = Controladora.getInstance();
        formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        lunes = fecha.minusDays(fecha.getDayOfWeek().getValue() - 1);

        setTitle("Calendario semanal - Barbería");
        setSize(1100, 570);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Colores.HUESO);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        controles.setBackground(Colores.VERDE_OSCURO);

        JLabel etiqueta = new JLabel("Ver a partir de (dd/MM/yyyy):");
        etiqueta.setFont(Fuentes.CALENDARIO);
        etiqueta.setForeground(Colores.VERDE_CLARO);
        controles.add(etiqueta);

        campoFecha = new JTextField(10);
        campoFecha.setFont(Fuentes.CALENDARIO);
        controles.add(campoFecha);

        JButton botonVer = crearBoton("Ver");
        botonVer.addActionListener(e -> elegirFecha());
        controles.add(botonVer);

        JButton botonAnterior = crearBoton("Anterior");
        botonAnterior.addActionListener(e -> {
            lunes = lunes.minusWeeks(1);
            actualizar();
        });
        controles.add(botonAnterior);

        JButton botonSiguiente = crearBoton("Siguiente");
        botonSiguiente.addActionListener(e -> {
            lunes = lunes.plusWeeks(1);
            actualizar();
        });
        controles.add(botonSiguiente);

        tituloSemana = new JLabel("", SwingConstants.CENTER);
        tituloSemana.setFont(Fuentes.CALENDARIO);
        tituloSemana.setForeground(Colores.VERDE_OSCURO);

        JPanel superior = new JPanel(new BorderLayout());
        superior.add(controles, BorderLayout.NORTH);
        superior.add(tituloSemana, BorderLayout.SOUTH);
        add(superior, BorderLayout.NORTH);

        panelDias = new JPanel(new GridLayout(1, 7, 3, 3));
        panelDias.setBackground(Colores.HUESO);
        add(panelDias, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        botones.setBackground(Colores.HUESO);
        JButton botonMensual = crearBoton("Ver calendario mensual");
        botonMensual.addActionListener(e -> {
            FrameCalendario ventana = new FrameCalendario(lunes);
            ventana.setVisible(true);
            dispose();
        });
        botones.add(botonMensual);
        add(botones, BorderLayout.SOUTH);

        actualizar();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(Fuentes.CALENDARIO);
        boton.setBackground(Colores.VERDE_OSCURO);
        boton.setForeground(Colores.VERDE_CLARO);
        return boton;
    }

    public static void main(String[] args) {
        new FrameCalendarioSemanal().setVisible(true);
    }

    private void elegirFecha() {
        try {
            LocalDate fecha = LocalDate.parse(campoFecha.getText().trim(), formatoFecha);
            lunes = fecha.minusDays(fecha.getDayOfWeek().getValue() - 1);
            actualizar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Escriba una fecha válida, por ejemplo 16/11/2026.");
        }
    }

    public void actualizar() {
        campoFecha.setText(lunes.format(formatoFecha));
        LocalDate domingo = lunes.plusDays(6);
        tituloSemana.setText("Semana del " + lunes.format(formatoFecha)
                + " al " + domingo.format(formatoFecha));
        panelDias.removeAll();

        for (int i = 0; i < 7; i++) {
            LocalDate fecha = lunes.plusDays(i);
            JPanel columna = new JPanel(new BorderLayout());
            columna.setBackground(Colores.VERDE);

            JLabel nombre = new JLabel("<html><center>" + dias[i] + "<br>"
                    + fecha.format(DateTimeFormatter.ofPattern("dd/MM")) + "</center></html>", SwingConstants.CENTER);
            nombre.setFont(Fuentes.CALENDARIO);
            nombre.setForeground(Colores.VERDE_CLARO);
            nombre.setBackground(Colores.VERDE_OSCURO);
            nombre.setOpaque(true);
            columna.add(nombre, BorderLayout.NORTH);

            String texto = "";
            int cantidad = 0;
            int total = controladora.getListaCitas().size();

            for (int j = 0; j < total; j++) {
                Cita cita = controladora.getCita(j);
                if (cita.getFechaHora().toLocalDate().equals(fecha)) {
                    texto += cita.getFechaHora().format(formatoHora) + "\n"
                            + cita.getCliente().getNombre() + "\n"
                            + cita.getServicio().getDescripcion() + "\n"
                            + cita.getEstado() + "\n\n";
                    cantidad++;
                }
            }

            if (cantidad == 0) {
                texto = "Sin citas";
            }

            JTextArea citas = new JTextArea(texto);
            citas.setEditable(false);
            citas.setLineWrap(true);
            citas.setWrapStyleWord(true);
            citas.setFont(Fuentes.CALENDARIO);
            citas.setBackground(Colores.VERDE);
            citas.setForeground(Colores.VERDE_CLARO);

            JScrollPane desplazamiento = new JScrollPane(citas);
            columna.add(desplazamiento, BorderLayout.CENTER);
            panelDias.add(columna);
        }

        panelDias.revalidate();
        panelDias.repaint();
    }
}
