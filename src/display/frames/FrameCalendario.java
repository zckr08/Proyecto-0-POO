package display.frames;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import logic.Cita;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FrameCalendario extends JFrame {

    private Controladora controladora;
    private LocalDate mesActual;
    private LocalDate fechaSeleccionada;
    private JPanel panelDias;
    private JTextField campoAnio;
    private JComboBox<String> campoMes;
    private JLabel textoFecha;
    private DateTimeFormatter formatoFecha;
    private String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private String[] dias = {
            "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    public FrameCalendario() {
        this(LocalDate.now());
    }

    public FrameCalendario(LocalDate fecha) {
        controladora = Controladora.getInstance();
        formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        fechaSeleccionada = fecha;
        mesActual = fecha.withDayOfMonth(1);

        setTitle("Calendario mensual - Barbería");
        setSize(920, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Colores.HUESO);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        controles.setBackground(Colores.VERDE_OSCURO);

        textoFecha = crearEtiqueta("");
        controles.add(textoFecha);
        controles.add(crearEtiqueta("Año:"));

        campoAnio = new JTextField(5);
        campoAnio.setFont(Fuentes.CALENDARIO);
        controles.add(campoAnio);

        controles.add(crearEtiqueta("Mes:"));
        campoMes = new JComboBox<>(meses);
        campoMes.setFont(Fuentes.CALENDARIO);
        controles.add(campoMes);

        JButton botonVer = crearBoton("Ver");
        botonVer.addActionListener(e -> elegirMes());
        controles.add(botonVer);

        JButton botonAnterior = crearBoton("Anterior");
        botonAnterior.addActionListener(e -> {
            mesActual = mesActual.minusMonths(1);
            fechaSeleccionada = mesActual;
            actualizar();
        });
        controles.add(botonAnterior);

        JButton botonSiguiente = crearBoton("Siguiente");
        botonSiguiente.addActionListener(e -> {
            mesActual = mesActual.plusMonths(1);
            fechaSeleccionada = mesActual;
            actualizar();
        });
        controles.add(botonSiguiente);

        JPanel nombresDias = new JPanel(new GridLayout(1, 7, 2, 2));
        nombresDias.setBackground(Colores.VERDE_OSCURO);
        for (int i = 0; i < 7; i++) {
            JLabel nombre = crearEtiqueta(dias[i]);
            nombre.setHorizontalAlignment(SwingConstants.CENTER);
            nombresDias.add(nombre);
        }

        JPanel superior = new JPanel(new BorderLayout());
        superior.add(controles, BorderLayout.NORTH);
        superior.add(nombresDias, BorderLayout.SOUTH);
        add(superior, BorderLayout.NORTH);

        panelDias = new JPanel(new GridLayout(6, 7, 2, 2));
        panelDias.setBackground(Colores.HUESO);
        add(panelDias, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        botones.setBackground(Colores.HUESO);

        JButton botonConsultar = crearBoton("Consultar día");
        botonConsultar.addActionListener(e -> consultarDia());
        botones.add(botonConsultar);

        JButton botonSemana = crearBoton("Ver semana");
        botonSemana.addActionListener(e -> {
            FrameCalendarioSemanal ventana = new FrameCalendarioSemanal(fechaSeleccionada);
            ventana.setVisible(true);
            dispose();
        });
        botones.add(botonSemana);

        add(botones, BorderLayout.SOUTH);
        actualizar();
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(Fuentes.CALENDARIO);
        etiqueta.setForeground(Colores.VERDE_CLARO);
        return etiqueta;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(Fuentes.CALENDARIO);
        boton.setBackground(Colores.VERDE_OSCURO);
        boton.setForeground(Colores.VERDE_CLARO);
        return boton;
    }

    private void elegirMes() {
        try {
            int anio = Integer.parseInt(campoAnio.getText().trim());
            if (anio < 1900 || anio > 2100) {
                JOptionPane.showMessageDialog(this, "Escriba un año entre 1900 y 2100.");
                return;
            }
            int numeroMes = campoMes.getSelectedIndex() + 1;
            mesActual = LocalDate.of(anio, numeroMes, 1);
            fechaSeleccionada = mesActual;
            actualizar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Escriba un año válido.");
        }
    }

    private int contarCitas(LocalDate fecha) {
        int cantidad = 0;
        int total = controladora.getListaCitas().size();
        for (int i = 0; i < total; i++) {
            Cita cita = controladora.getCita(i);
            if (cita.getFechaHora().toLocalDate().equals(fecha)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public void actualizar() {
        campoAnio.setText("" + mesActual.getYear());
        campoMes.setSelectedIndex(mesActual.getMonthValue() - 1);
        textoFecha.setText("Ver a partir de: " + fechaSeleccionada.format(formatoFecha));
        panelDias.removeAll();

        int primerDia = mesActual.getDayOfWeek().getValue() % 7;
        int diasMes = mesActual.lengthOfMonth();
        int filas = (primerDia + diasMes + 6) / 7;
        panelDias.setLayout(new GridLayout(filas, 7, 2, 2));

        for (int i = 0; i < filas * 7; i++) {
            int numero = i - primerDia + 1;
            if (numero < 1 || numero > diasMes) {
                JPanel vacio = new JPanel();
                vacio.setBackground(Colores.VERDE);
                panelDias.add(vacio);
            } else {
                LocalDate fecha = mesActual.withDayOfMonth(numero);
                int cantidad = contarCitas(fecha);
                String texto = "<html>" + numero;
                if (cantidad > 0) {
                    texto += "<br>" + cantidad + " cita(s)";
                }
                texto += "</html>";

                JButton botonDia = crearBoton(texto);
                botonDia.setHorizontalAlignment(SwingConstants.LEFT);
                botonDia.setVerticalAlignment(SwingConstants.TOP);
                botonDia.setBorder(BorderFactory.createLineBorder(Colores.HUESO));
                if (fecha.equals(fechaSeleccionada)) {
                    botonDia.setBackground(Colores.HUESO);
                    botonDia.setForeground(Colores.VERDE_OSCURO);
                } else {
                    botonDia.setBackground(Colores.VERDE);
                    botonDia.setForeground(Colores.VERDE_CLARO);
                }
                botonDia.addActionListener(e -> {
                    fechaSeleccionada = fecha;
                    actualizar();
                });
                panelDias.add(botonDia);
            }
        }

        panelDias.revalidate();
        panelDias.repaint();
    }

    public static void main(String[] args) {
        new FrameCalendario().setVisible(true);
    }

    private void consultarDia() {
        String mensaje = "Citas del " + fechaSeleccionada.format(formatoFecha) + ":\n\n";
        int cantidad = 0;
        int total = controladora.getListaCitas().size();
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < total; i++) {
            Cita cita = controladora.getCita(i);
            if (cita.getFechaHora().toLocalDate().equals(fechaSeleccionada)) {
                mensaje += cita.getFechaHora().format(formatoHora) + " - "
                        + cita.getCliente().getNombre() + " - "
                        + cita.getServicio().getDescripcion() + " - "
                        + cita.getEstado() + "\n";
                cantidad++;
            }
        }

        if (cantidad == 0) {
            mensaje += "No hay citas para este día.";
        }
        JOptionPane.showMessageDialog(this, mensaje, "Consultar día", JOptionPane.INFORMATION_MESSAGE);
    }
}
