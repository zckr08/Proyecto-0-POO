package display.dialogs;

import logic.Cliente;
import control.Controladora;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class DialogFormularioCliente extends JDialog {

    public DialogFormularioCliente(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font, String opcion, Window propietario, int index) {
        super(propietario, opcion, ModalityType.APPLICATION_MODAL);

        Controladora controladora = Controladora.getInstance();

        List<String> datos = new ArrayList<>();
        if (index >= 0) {
            try {
                datos = controladora.obtenerDatosCliente(index);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(propietario, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
        }

        if (datos.size() < 3) {
            datos = new ArrayList<>(List.of("", "", ""));
        }

        JLabel titulo = new JLabel(opcion, SwingConstants.CENTER);
        titulo.setForeground(verdeClaro);
        titulo.setBackground(verdeOscuro);
        titulo.setOpaque(true);
        titulo.setFont(font);

        JTextField campoNombre = new JTextField();
        JTextField campoTelefono = new JTextField();
        JTextField campoCorreo = new JTextField();

        campoNombre.setFont(font);
        campoNombre.setForeground(verdeOscuro);

        campoTelefono.setFont(font);
        campoTelefono.setForeground(verdeOscuro);

        campoCorreo.setFont(font);
        campoCorreo.setForeground(verdeOscuro);

        if (opcion.equals("Modificar Cliente")) {
            campoNombre.setText(datos.get(0));
            campoTelefono.setText(datos.get(1));
            campoCorreo.setText(datos.get(2));

            campoNombre.setEditable(true);
            campoTelefono.setEditable(true);
            campoCorreo.setEditable(true);
        }

        else if (opcion.equals("Crear Cliente")) {
            campoNombre.setText("");
            campoTelefono.setText("");
            campoCorreo.setText("");

            campoNombre.setEditable(true);
            campoTelefono.setEditable(true);
            campoCorreo.setEditable(true);
        }

        else if (opcion.equals("Consultar Cliente")) {
            campoNombre.setText(datos.get(0));
            campoTelefono.setText(datos.get(1));
            campoCorreo.setText(datos.get(2));

            campoNombre.setEditable(false);
            campoTelefono.setEditable(false);
            campoCorreo.setEditable(false);
        }

        else if (opcion.equals("Se ha borrado un cliente!")) {
            campoNombre.setText(datos.get(0));
            campoTelefono.setText(datos.get(1));
            campoCorreo.setText(datos.get(2));

            campoNombre.setEditable(false);
            campoTelefono.setEditable(false);
            campoCorreo.setEditable(false);
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
            String nombre = campoNombre.getText();
            String telefono = campoTelefono.getText();
            String email = campoCorreo.getText();

            if (opcion.equals("Modificar Cliente")) {
                try {
                    controladora.modificarCliente(index, nombre, email, telefono);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else if (opcion.equals("Crear Cliente")) {
                try {
                    controladora.crearCliente(nombre, email, telefono);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            else {
                dispose();
            }
        });

        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Telefono:"));
        formulario.add(campoTelefono);
        formulario.add(new JLabel("Correo: "));
        formulario.add(campoCorreo);


        add(formulario, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);
        add(botonConfirmar, BorderLayout.SOUTH);
    }
}
