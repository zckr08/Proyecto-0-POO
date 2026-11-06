package display.components;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class PanelTabla extends JPanel {
    private static final int ELEMENTOS_POR_COLUMNA = 25;
    private static final int ELEMENTOS_POR_PAGINA = 50;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonAnterior;
    private JButton botonSiguiente;
    private JLabel etiquetaPagina;
    private List<String> elementos = new ArrayList<>();
    private int paginaActual = 0;

    public PanelTabla(Color verde, Color verdeOscuro, Color hueso, Color verdeClaro, Font font) {
        setLayout(new BorderLayout(0, 10));
        setBackground(verde);
        modelo = new DefaultTableModel(new Object[]{"#", "Elemento", "#", "Elemento"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modelo);

        tabla.setFont(font);
        tabla.setForeground(verdeOscuro);
        tabla.setBackground(verdeClaro);

        tabla.setRowHeight(35);
        tabla.setShowGrid(true);
        tabla.setGridColor(verdeOscuro);

        tabla.setCellSelectionEnabled(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(hueso);
        tabla.setSelectionForeground(verdeOscuro);
        tabla.setTableHeader(null);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(45);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(450);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(45);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(450);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(verde);
        add(scroll, BorderLayout.CENTER);

        JPanel paginacion = new JPanel(new FlowLayout());
        paginacion.setBackground(verde);
        botonAnterior = new JButton("Anterior");
        botonSiguiente = new JButton("Siguiente");
        etiquetaPagina = new JLabel("Pagina 1 de 1");

        botonAnterior.setFont(font);
        botonSiguiente.setFont(font);
        etiquetaPagina.setFont(font);

        botonAnterior.addActionListener(e -> {
        if (paginaActual > 0) {
            paginaActual--;
            actualizarTabla();
        }
    });

        botonSiguiente.addActionListener(e -> {
        if (paginaActual + 1 < getTotalPaginas()) {
            paginaActual++;
            actualizarTabla();
        }
    });

        paginacion.add(botonAnterior);
        paginacion.add(etiquetaPagina);
        paginacion.add(botonSiguiente);

    add(paginacion, BorderLayout.SOUTH);

    actualizarTabla();
}

public void setElementos(List<String> elementos) {
    this.elementos = new ArrayList<>(elementos);
    paginaActual = 0;
    actualizarTabla();
}

private int getTotalPaginas() {
    return Math.max(
            1, (elementos.size() + ELEMENTOS_POR_PAGINA - 1)
                    / ELEMENTOS_POR_PAGINA
    );
}

private void actualizarTabla() {

    modelo.setRowCount(0);

    int inicio = paginaActual * ELEMENTOS_POR_PAGINA;

    int cantidad = Math.min(
            ELEMENTOS_POR_PAGINA,
            elementos.size() - inicio
    );

    int filas = Math.min(
            ELEMENTOS_POR_COLUMNA,
            cantidad
    );

    for (int i = 0; i < filas; i++) {

        int izquierda = inicio + i;
        int derecha = inicio + ELEMENTOS_POR_COLUMNA + i;

        Object[] fila = {
                izquierda + 1,
                elementos.get(izquierda),
                null,
                null
        };

        if (derecha < inicio + cantidad) {
            fila[2] = derecha + 1;
            fila[3] = elementos.get(derecha);
        }

        modelo.addRow(fila);
    }

    tabla.clearSelection();

    etiquetaPagina.setText(
            "Página " + (paginaActual + 1)
                    + " de " + getTotalPaginas()
    );

    botonAnterior.setEnabled(paginaActual > 0);
    botonSiguiente.setEnabled(
            paginaActual + 1 < getTotalPaginas()
    );
}
public int getIndiceSeleccionado() {

    int fila = tabla.getSelectedRow();
    int columna = tabla.getSelectedColumn();
    if (fila == -1 || columna == -1) {
        return -1;
    }
    int indice = paginaActual * ELEMENTOS_POR_PAGINA
            + fila;
    if (columna >= 2) {
        indice += ELEMENTOS_POR_COLUMNA;
    }

    if (indice >= elementos.size()) {
        return -1;
    }

    return indice;
}
}
