package pe.edu.utp.PROYECTO.vista;

import pe.edu.utp.PROYECTO.Servicio.GestorReportes;

import javax.swing.*;

public class PanelReportes extends JFrame{
    private JPanel jpReportes;
    private JLabel txtReportesSistema;
    private JButton btnGenerarReporte;
    private JButton btnRegresar;
    private JComboBox cboxTipoReporte;
    private JComboBox cboxFormato;
    private GestorReportes gestorReportes;

    public PanelReportes() {
        gestorReportes = new GestorReportes();
        prepararFormulario();
        prepararEventos();
    }

    private void prepararFormulario() {
        setContentPane(jpReportes);
        setTitle("REPORTES DEL SISTEMA");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cargarTiposReporte();
        cargarFormatos();
    }

    private void cargarTiposReporte() {
        cboxTipoReporte.removeAllItems();
        cboxTipoReporte.addItem("Usuarios");
        cboxTipoReporte.addItem("Proyectos");
        cboxTipoReporte.addItem("Tareas");
    }
    private void cargarFormatos() {
        cboxFormato.removeAllItems();
        cboxFormato.addItem("PDF");
        cboxFormato.addItem("Excel");
        cboxFormato.addItem("HTML");

    }
    private void prepararEventos() {
        btnRegresar.addActionListener(e -> {
            PanelPRINCIPAL panel = new PanelPRINCIPAL();
            panel.setVisible(true);
            dispose();
        });
        btnGenerarReporte.addActionListener(e -> {
            String tipo = cboxTipoReporte.getSelectedItem().toString();
            String formato = cboxFormato.getSelectedItem().toString();
            switch (formato.toUpperCase()) {
                case "PDF":
                    gestorReportes.exportarPDF(tipo);
                    break;
                case "EXCEL":
                    gestorReportes.exportarExcel(tipo);
                    break;
                case "HTML":
                    gestorReportes.exportarHTML(tipo);
                    break;
                default:
                    JOptionPane.showMessageDialog(
                            this,
                            "Formato no válido."
                    );
                    return;
            }
            JOptionPane.showMessageDialog(
                    this,
                    "Reporte generado correctamente."
            );
        });
    }
}
