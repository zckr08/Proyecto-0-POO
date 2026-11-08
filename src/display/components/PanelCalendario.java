package display.components;

import control.Controladora;
import logic.Cita;
import logic.EstadoCita;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PanelCalendario extends JPanel {
    private Controladora controladora;
    private LocalDate mesActual;
    private LocalDate fechaSeleccionada;
    private JPanel cuadricula;
    private JComboBox<String> selectorMes;
    private JTextField campoAnio;
    private JLabel textoFecha;
    private String[] nombresMeses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private String[] nombresDias = {
            "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    public PanelCalendario() {
        controladora = Controladora.getInstance();
        mesActual = LocalDate.now().withDayOfMonth(1);
        fechaSeleccionada = LocalDate.now();
        setLayout(new BorderLayout(4, 4));
        setBackground(Colores.HUESO);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 1));
        controles.setBackground(Colores.VERDE_OSCURO);

        textoFecha = crearEtiqueta("Ver a partir de: " + fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        controles.add(textoFecha);
        controles.add(crearEtiqueta("Año:"));

        campoAnio = new JTextField(String.valueOf(mesActual.getYear()), 5);
        campoAnio.setFont(Fuentes.CALENDARIO);
        controles.add(campoAnio);

        controles.add(crearEtiqueta("Mes:"));
        selectorMes = new JComboBox<>(nombresMeses);
        selectorMes.setFont(Fuentes.CALENDARIO);
        selectorMes.setSelectedIndex(mesActual.getMonthValue() - 1);
        controles.add(selectorMes);

        JButton botonVer = crearBoton("Ver");
        botonVer.addActionListener(e -> elegirMes());
        controles.add(botonVer);

        JPanel parteSuperior = new JPanel(new BorderLayout());
        parteSuperior.add(controles, BorderLayout.NORTH);

        JPanel titulosDias = new JPanel(new GridLayout(1, 7, 1, 0));
        titulosDias.setBackground(Colores.VERDE_OSCURO);
        for (String nombre : nombresDias) {
            JLabel etiqueta = crearEtiqueta(nombre);
            etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
            etiqueta.setFont(Fuentes.CALENDARIO);
            titulosDias.add(etiqueta);
        }
        parteSuperior.add(titulosDias, BorderLayout.SOUTH);
        add(parteSuperior, BorderLayout.NORTH);

        cuadricula = new JPanel(new GridLayout(6, 7, 1, 1));
        cuadricula.setBackground(Colores.HUESO);
        add(cuadricula, BorderLayout.CENTER);

        JButton botonConsultar = crearBoton("Consultar día");
        botonConsultar.addActionListener(e -> consultarDia());
        JPanel parteInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        parteInferior.setBackground(Colores.HUESO);
        parteInferior.add(botonConsultar);
        add(parteInferior, BorderLayout.SOUTH);

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
        boton.setFocusPainted(false);
        return boton;
    }

    private void elegirMes() {
        try {
            int anio = Integer.parseInt(campoAnio.getText().trim());
            if (anio < 1900 || anio > 2100) {
                throw new Exception("Escriba un año entre 1900 y 2100.");
            }
            mesActual = LocalDate.of(anio, selectorMes.getSelectedIndex() + 1, 1);
            fechaSeleccionada = mesActual;
            actualizar();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Escriba un año válido, por ejemplo 2026.", "Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int contarCitas(LocalDate fecha) {
        int cantidad = 0;
        int total = controladora.getListaCitas().size();
        for (int i = 0; i < total; i++) {
            Cita cita = controladora.getCita(i);
            if (cita.getEstado() != EstadoCita.CANCELADA
                    && cita.getFechaHora().toLocalDate().equals(fecha)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public void actualizar() {
        cuadricula.removeAll();
        textoFecha.setText("Ver a partir de: " + fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        int inicio = mesActual.getDayOfWeek().getValue() % 7;
        int filas = (inicio + mesActual.lengthOfMonth() + 6) / 7;
        cuadricula.setLayout(new GridLayout(filas, 7, 1, 1));

        for (int i = 0; i < filas * 7; i++) {
            int numero = i - inicio + 1;
            if (numero < 1 || numero > mesActual.lengthOfMonth()) {
                JPanel vacio = new JPanel();
                vacio.setBackground(Colores.VERDE);
                cuadricula.add(vacio);
            } else {
                LocalDate fecha = mesActual.withDayOfMonth(numero);
                int citas = contarCitas(fecha);
                String texto = "<html>" + numero;
                if (citas > 0) {
                    texto += "<br>" + citas + (citas == 1 ? " cita" : " citas");
                }
                texto += "</html>";

                JButton botonDia = new JButton(texto);
                botonDia.setFont(new Font("Monospaced", Font.PLAIN, 14));
                botonDia.setMargin(new Insets(1, 2, 1, 2));
                botonDia.setHorizontalAlignment(SwingConstants.LEFT);
                botonDia.setVerticalAlignment(SwingConstants.TOP);
                botonDia.setBackground(fecha.equals(fechaSeleccionada) ? Colores.HUESO : Colores.VERDE);
                botonDia.setForeground(Colores.VERDE_CLARO);
                if (fecha.equals(fechaSeleccionada)) {
                    botonDia.setForeground(Colores.VERDE_OSCURO);
                }
                botonDia.setBorder(BorderFactory.createLineBorder(Colores.HUESO));
                botonDia.setFocusPainted(false);
                botonDia.addActionListener(e -> {
                    fechaSeleccionada = fecha;
                    actualizar();
                });
                cuadricula.add(botonDia);
            }
        }
        cuadricula.revalidate();
        cuadricula.repaint();
    }

    private void consultarDia() {
        StringBuilder mensaje = new StringBuilder("Citas del "
                + fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n");
        int cantidad = 0;
        int total = controladora.getListaCitas().size();
        DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < total; i++) {
            Cita cita = controladora.getCita(i);
            if (cita.getEstado() != EstadoCita.CANCELADA
                    && cita.getFechaHora().toLocalDate().equals(fechaSeleccionada)) {
                mensaje.append(cita.getFechaHora().format(hora))
                        .append(" - ").append(cita.getCliente().getNombre())
                        .append(" - ").append(cita.getServicio().getDescripcion())
                        .append("\n");
                cantidad++;
            }
        }
        if (cantidad == 0) {
            mensaje.append("No hay citas programadas para este día.");
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Consultar día", JOptionPane.INFORMATION_MESSAGE);
    }
}
