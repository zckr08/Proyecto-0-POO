package display.frames;

import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import logic.Cita;
import logic.EstadoCita;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FrameEnviarCorreoConfirmacion extends JFrame {
    private Controladora controladora;
    private List<Cita> citasPendientes;
    private JTextArea listaCitas;

    public FrameEnviarCorreoConfirmacion() {
        controladora = Controladora.getInstance();
        citasPendientes = new ArrayList<>();

        setTitle("Enviar correo de confirmación - Barbería");
        setSize(520, 390);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Colores.VERDE);

        JLabel titulo = new JLabel("Enviar correo de confirmación", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setFont(Fuentes.CALENDARIO);
        titulo.setBackground(Colores.VERDE_OSCURO);
        titulo.setForeground(Colores.TEXTO);
        add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        contenido.setBackground(Colores.VERDE);

        JLabel subtitulo = new JLabel("Citas para mañana sin confirmar:", SwingConstants.CENTER);
        subtitulo.setFont(Fuentes.CALENDARIO);
        subtitulo.setForeground(Colores.VERDE_OSCURO);
        subtitulo.setOpaque(true);
        subtitulo.setBackground(Colores.VERDE_CLARO);

        listaCitas = new JTextArea();
        listaCitas.setFont(Fuentes.CALENDARIO);
        listaCitas.setEditable(false);
        listaCitas.setBackground(Colores.VERDE_CLARO);
        listaCitas.setForeground(Colores.VERDE_OSCURO);
        listaCitas.setMargin(new Insets(8, 12, 8, 12));

        JScrollPane desplazamiento = new JScrollPane(listaCitas);
        desplazamiento.setPreferredSize(new Dimension(450, 150));
        desplazamiento.setBorder(BorderFactory.createEmptyBorder());

        JPanel listado = new JPanel(new BorderLayout());
        listado.setBackground(Colores.VERDE);
        listado.add(subtitulo, BorderLayout.NORTH);
        listado.add(desplazamiento, BorderLayout.CENTER);
        contenido.add(listado, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);

        JButton botonEnviar = new JButton("Enviar Confirmación");
        botonEnviar.setFont(Fuentes.CALENDARIO);
        botonEnviar.setBackground(Colores.VERDE_OSCURO);
        botonEnviar.setForeground(Colores.TEXTO);
        botonEnviar.setFocusPainted(false);
        botonEnviar.addActionListener(e -> enviarCorreos());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
        botones.setBackground(Colores.VERDE);
        botones.add(botonEnviar);
        add(botones, BorderLayout.SOUTH);

        actualizarCitas();
    }

    private void actualizarCitas() {
        citasPendientes.clear();
        LocalDate manana = LocalDate.now().plusDays(1);
        int cantidadCitas = controladora.getListaCitas().size();
        for (int i = 0; i < cantidadCitas; i++) {
            Cita cita = controladora.getCita(i);
            if (cita.getEstado() == EstadoCita.NO_CONFIRMADA
                    && cita.getFechaHora().toLocalDate().equals(manana)
                    && controladora.estaDentroDelHorario(cita.getFechaHora())) {
                citasPendientes.add(cita);
            }
        }

        if (citasPendientes.isEmpty()) {
            listaCitas.setText("No hay citas pendientes para mañana.");
            return;
        }

        String texto = "";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 0; i < citasPendientes.size(); i++) {
            Cita cita = citasPendientes.get(i);
            texto += (i + 1) + ". " + cita.getCliente().getNombre()
                    + " - " + cita.getFechaHora().format(formato) + "\n";
        }
        listaCitas.setText(texto);
    }

    private void enviarCorreos() {
        actualizarCitas();
        if (citasPendientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay citas pendientes de confirmación.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Enviar " + citasPendientes.size() + " correo(s) de confirmación?",
                "Enviar correos", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        int enviados = 0;
        int omitidos = 0;
        for (Cita cita : citasPendientes) {
            boolean terminado = false;
            while (!terminado) {
                try {
                    controladora.enviarNotificacion(cita);
                    enviados++;
                    terminado = true;
                } catch (Exception e) {
                    Object[] opciones = {"Reintentar", "Continuar", "Cancelar"};
                    int respuesta = JOptionPane.showOptionDialog(this,
                            "No se pudo enviar el correo a " + cita.getCliente().getEmail()
                                    + ".\n" + e.getMessage(),
                            "Error de envío", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE, null, opciones, opciones[0]);
                    if (respuesta == 0) {
                        continue;
                    }
                    if (respuesta == 1) {
                        omitidos++;
                        terminado = true;
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Envío cancelado. Correos enviados: " + enviados);
                        return;
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this,
                "Correos enviados: " + enviados + "\nNo enviados: " + omitidos
                        + "\nLas citas siguen pendientes hasta que se confirmen.");
    }
}
