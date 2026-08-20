package pe.edu.utp.PROYECTO.vista;

import javax.swing.*;

public class TablaUtil {
    public static void ocultarColumnaID(JTable tabla) {
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setWidth(0);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}
