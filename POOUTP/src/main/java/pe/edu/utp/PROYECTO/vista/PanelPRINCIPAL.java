package pe.edu.utp.PROYECTO.vista;

import pe.edu.utp.PROYECTO.AccesoDatos.ProyectoDAO;
import pe.edu.utp.PROYECTO.AccesoDatos.TareaDao;
import pe.edu.utp.PROYECTO.AccesoDatos.UsuarioDAO;

import javax.swing.*;

public class PanelPRINCIPAL extends JFrame{
    private JPanel jpPrincipal;
    private JButton btnUsuarios;
    private JButton btnProyectos;
    private JButton btnTareas;
    private JButton btnReportes;
    private JButton btnSalir;
    private JLabel lblFecha;
    private JButton btnEstadisticas;
    private JLabel txtUsuariosRegistrados;
    private JLabel txtProyectosRegistrados;
    private JLabel txtTareasRegistradas;
    private PanelUsuario panelUsuario;
    private PanelProyecto panelProyecto;
    private PanelTarea panelTarea;
    //para los dashboards
    private UsuarioDAO usuarioDAO;
    private ProyectoDAO proyectoDAO;
    private TareaDao tareaDAO;

    public PanelPRINCIPAL(){
        prepararFormulario();
        usuarioDAO = new UsuarioDAO();
        proyectoDAO = new ProyectoDAO();
        tareaDAO = new TareaDao();
        prepararEventos();
        cargarDashboard();
        btnSalir.addActionListener(e ->  salirSistema());

    }
    private void prepararFormulario(){
        setContentPane(jpPrincipal);
        setTitle("ADMINISTRACIÓN DE PROYECTOS Y TAREAS");
        setSize(800,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void cargarDashboard() {
        txtUsuariosRegistrados.setText("Usuarios registrados: " + usuarioDAO.listarUsuario().size());
        txtProyectosRegistrados.setText("Proyectos registrados: " + proyectoDAO.listarProyectos().size());
        txtTareasRegistradas.setText("Tareas registradas: " + tareaDAO.listarTareas().size());
    }
    private void prepararEventos() {
        btnUsuarios.addActionListener(e -> {
            PanelUsuario panel = new PanelUsuario();
            panel.setVisible(true);
            dispose();
        });

        btnProyectos.addActionListener(e -> {
            PanelProyecto panel = new PanelProyecto();
            panel.setVisible(true);
            dispose();
        });

        btnTareas.addActionListener(e -> {
            PanelTarea panel = new PanelTarea();
            panel.setVisible(true);
            dispose();
        });
        btnEstadisticas.addActionListener(e -> {
            PanelEstadisticas panel = new PanelEstadisticas();
            panel.setVisible(true);
            dispose();
        });

        btnReportes.addActionListener(e -> {
            PanelReportes panel = new PanelReportes();
            panel.setVisible(true);
            dispose();
        });
    }
    //CERRAR SISTEMA
    private void salirSistema() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea salir del sistema?",
                "Salir",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
