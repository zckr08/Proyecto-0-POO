package display;

import control.Controladora;
import display.frames.FramePrincipal;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controladora controladora = Controladora.getInstance();
            String archivo = System.getProperty("user.home") + File.separator + "barberia_datos.dat";

            try {
                controladora.cargarDatos(archivo);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "No se pudieron cargar los datos guardados.\n" + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            FramePrincipal ventana = new FramePrincipal();
            ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            ventana.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        controladora.guardarDatos(archivo);
                        ventana.dispose();
                        System.exit(0);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana,
                                "No se pudieron guardar los datos.\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        });
    }
}
