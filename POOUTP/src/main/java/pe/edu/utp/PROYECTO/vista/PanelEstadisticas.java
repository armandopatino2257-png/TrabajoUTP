package pe.edu.utp.PROYECTO.vista;

import pe.edu.utp.PROYECTO.AccesoDatos.ProyectoDAO;
import pe.edu.utp.PROYECTO.AccesoDatos.TareaDao;
import pe.edu.utp.PROYECTO.Servicio.GestorEstadisticas;

import javax.swing.*;

public class PanelEstadisticas extends JFrame {
    private JLabel txtActivos;
    private JLabel txtPausados;
    private JLabel txtFinalizados;
    private JLabel txtPendientes;
    private JLabel txtEnCurso;
    private JLabel txtFinalizadas;
    private JLabel txtAlta;
    private JLabel txtMedia;
    private JLabel txtBaja;
    private JLabel txtTareasTotales;
    private JLabel txtFINALIZADAS;
    private JLabel txtAvance;
    private JPanel jpEstadisticas;
    private JButton btnRegresar;
    private JLabel txtProyectoEstado;
    private JLabel txtTareaPrioridad;
    private JLabel txtTareaEstado;
    private JLabel txtProyectoGeneral;
    private ProyectoDAO proyectoDAO;
    private TareaDao tareaDAO;

    public PanelEstadisticas() {
        proyectoDAO = new ProyectoDAO();
        tareaDAO = new TareaDao();
        prepararFormulario();
        cargarEstadisticas();
        configurarEventos();
    }
    private void cargarEstadisticas() {
        GestorEstadisticas gestor = new GestorEstadisticas(
                proyectoDAO.listarProyectos(),
                tareaDAO.listarTareasVista()
        );
        // Proyecto por estado, solo textos usando setText
        txtActivos.setText("Activos: " + gestor.contarProyectosActivos());
        txtPausados.setText("Pausados: " + gestor.contarProyectosPausados());
        txtFinalizados.setText("Finalizados: " + gestor.contarProyectosFinalizados());
        txtPendientes.setText("Pendientes: " + gestor.contarTareasPendientes());
        txtEnCurso.setText("En Curso: " + gestor.contarTareasEnCurso());
        txtFinalizadas.setText("Finalizadas: " + gestor.contarTareasFinalizadas());
        txtAlta.setText("Alta: " + gestor.contarPrioridadAlta());
        txtMedia.setText("Media: " + gestor.contarPrioridadMedia());
        txtBaja.setText("Baja: " + gestor.contarPrioridadBaja());
        txtTareasTotales.setText("Tareas Totales: " + gestor.contarTareasTotales());
        txtFINALIZADAS.setText("Finalizadas: " + gestor.contarTareasFinalizadasTotal());
        txtAvance.setText(String.format("Avance: %.2f%%", gestor.calcularAvanceProyecto()));
    }
    private void prepararFormulario() {
        setContentPane(jpEstadisticas);
        setTitle("ESTADÍSTICAS DEL SISTEMA");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void configurarEventos() {
        btnRegresar.addActionListener(e -> {
            PanelPRINCIPAL panel = new PanelPRINCIPAL();
            panel.setVisible(true);
            dispose();
        });

    }

}
