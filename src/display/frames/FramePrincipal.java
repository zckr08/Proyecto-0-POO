package display.frames;

import javax.swing.*;
import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import display.panels.PanelContenido;
import display.panels.PanelInferior;
import display.panels.PanelNavegacion;
import display.components.PanelTabla;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FramePrincipal extends JFrame {

    private Controladora controladora = Controladora.getInstance();
    private String rutaArchivo = "data.dat";

    public FramePrincipal() {



        setLayout(new BorderLayout());

        try {
            controladora.cargarDatos(rutaArchivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error al cargar datos", JOptionPane.ERROR_MESSAGE);
        }

        PanelContenido contenido = new PanelContenido();
        PanelNavegacion navegacion = new PanelNavegacion(contenido);
        PanelInferior inferior = new PanelInferior();
        add(navegacion, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        PanelTabla tabla = new PanelTabla(Colores.VERDE, Colores.VERDE_OSCURO, Colores.HUESO, Colores.VERDE_CLARO, Fuentes.GENERAL);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    controladora.guardarDatos(rutaArchivo);
                } catch (Exception ex) {JOptionPane.showMessageDialog(FramePrincipal.this, ex.getMessage(), "Error al guardar datos.", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        setVisible(true);
    }

}
