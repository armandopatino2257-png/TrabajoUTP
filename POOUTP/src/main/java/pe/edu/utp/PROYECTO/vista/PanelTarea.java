package pe.edu.utp.PROYECTO.vista;

import pe.edu.utp.PROYECTO.AccesoDatos.ProyectoDAO;
import pe.edu.utp.PROYECTO.AccesoDatos.TareaDao;
import pe.edu.utp.PROYECTO.AccesoDatos.UsuarioDAO;
import pe.edu.utp.PROYECTO.Servicio.GestorTarea;
import pe.edu.utp.PROYECTO.modelo.Proyecto;
import pe.edu.utp.PROYECTO.modelo.Tarea;
import pe.edu.utp.PROYECTO.modelo.TareaVista;
import pe.edu.utp.PROYECTO.modelo.Usuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PanelTarea extends JFrame{
    private JComboBox cboxUsuario;
    private JLabel txtUsuario;
    private JLabel txtProyecto;
    private JLabel txtTitulo;
    private JLabel txtDescripcion;
    private JLabel txtFechaInicio;
    private JLabel txtFechaFin;
    private JComboBox cboxProyecto;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton btnRegistrarTarea;
    private JButton btnLimpiar;
    private JLabel txtEstado;
    private JLabel txtPrioridad;
    private JComboBox cboxEstado;
    private JComboBox cboxPrioridad;
    private JLabel txtBuscar;
    private JTable tablaTarea;
    private DefaultTableModel tModel;
    private JLabel txtTareasRegistradas;
    private JLabel txtResultadosEncontrados;
    private JButton btnEliminarTarea;
    private JButton btnRegresar;
    private JComboBox cboxOrdenarPor;
    private JComboBox cboxAscDesc;
    private JTextField textField5;
    private JComboBox cboxBuscar;
    private JPanel jpTarea;
    private TareaDao tareaDAO;
    private UsuarioDAO usuarioDAO;
    private ProyectoDAO proyectoDAO;
    private ArrayList<TareaVista> listaTareasVista;
    private Tarea tareaActual = null;

    public PanelTarea() {
        tareaDAO = new TareaDao();
        usuarioDAO = new UsuarioDAO();
        proyectoDAO = new ProyectoDAO();
        prepararFormularioTarea();
        cargarUsuariosCombo();
        cargarProyectosCombo();
        cargarEstados();
        cargarPrioridades();
        configurarEventosBusqueda();
        configurarEventosCRUD();
        cargarTareas();
        TablaUtil.ocultarColumnaID(tablaTarea);
    }

    private void prepararFormularioTarea() {
        setContentPane(jpTarea);
        setTitle("GESTIÓN DE TAREAS");
        setSize(1000,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tModel.addColumn("ID");
        tModel.addColumn("Usuario");
        tModel.addColumn("Proyecto");
        tModel.addColumn("Título");
        tModel.addColumn("Descripción");
        tModel.addColumn("Estado");
        tModel.addColumn("Fecha Inicio");
        tModel.addColumn("Fecha Fin");
        tModel.addColumn("Prioridad");
        tablaTarea.setModel(tModel);
        //todos los combox (buscar,ordenar,direccion)
        cboxBuscar.removeAllItems();
        cboxBuscar.addItem("Usuario");
        cboxBuscar.addItem("Proyecto");
        cboxBuscar.addItem("Título");
        cboxBuscar.addItem("Descripción");
        cboxBuscar.addItem("Estado");
        cboxBuscar.addItem("Fecha Inicio");
        cboxBuscar.addItem("Fecha Fin");
        cboxBuscar.addItem("Prioridad");
        cboxOrdenarPor.removeAllItems();
        cboxOrdenarPor.addItem("Usuario");
        cboxOrdenarPor.addItem("Proyecto");
        cboxOrdenarPor.addItem("Título");
        cboxOrdenarPor.addItem("Fecha Inicio");
        cboxOrdenarPor.addItem("Fecha Fin");
        cboxOrdenarPor.addItem("Estado");
        cboxOrdenarPor.addItem("Prioridad");
        cboxAscDesc.removeAllItems();
        cboxAscDesc.addItem("Ascendente");
        cboxAscDesc.addItem("Descendente");
    }

    private void cargarTareas() {
        actualizarTabla(tareaDAO.listarTareasVista());
    }

    private void actualizarTabla(List<TareaVista> tareas) {
        this.listaTareasVista = new ArrayList<>(tareas);
        tModel.setRowCount(0);
        for (TareaVista tarea : tareas) {
            tModel.addRow(new Object[]{
                    tarea.getIdTarea(),
                    tarea.getNombreUsuario(),
                    tarea.getNombreProyecto(),
                    tarea.getTitulo(),
                    tarea.getDescripcion(),
                    tarea.getEstadoTarea(),
                    tarea.getFechaInicio(),
                    tarea.getFechaFin(),
                    tarea.getPrioridad()
            });
        }
        TablaUtil.ocultarColumnaID(tablaTarea);
        txtTareasRegistradas.setText("Tareas registradas: " + tareaDAO.listarTareasVista().size());
        txtResultadosEncontrados.setText("Resultados encontrados: " + tareas.size());
    }
    //COMBO BOX USUARIO
    private void cargarUsuariosCombo() {
        cboxUsuario.removeAllItems();
        ArrayList<Usuario> usuarios = usuarioDAO.listarUsuario();
        for (Usuario usuario : usuarios) {
            cboxUsuario.addItem(
                    new ComboItem(
                            usuario.getIdUsuario(),
                            usuario.getNombre() + " " + usuario.getApellido()
                    )
            );
        }
    }
    //COMBO BOX PROYECTO
    private void cargarProyectosCombo() {
        cboxProyecto.removeAllItems();
        ArrayList<Proyecto> proyectos = proyectoDAO.listarProyectos();
        for (Proyecto proyecto : proyectos) {
            cboxProyecto.addItem(new ComboItem(proyecto.getIdProyecto(), proyecto.getNombre())
            ); 
        }
    }
    //cargar estados y prioridades
    private void cargarEstados() {
        cboxEstado.removeAllItems();
        cboxEstado.addItem("Pendiente");
        cboxEstado.addItem("En Curso");
        cboxEstado.addItem("Finalizado");
        cboxEstado.setSelectedIndex(-1);
    }
    private void cargarPrioridades() {
        cboxPrioridad.removeAllItems();
        cboxPrioridad.addItem("Alta");
        cboxPrioridad.addItem("Media");
        cboxPrioridad.addItem("Baja");
        cboxPrioridad.setSelectedIndex(-1);
    }

    private boolean validarFormulario() {
        if (cboxUsuario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
            return false;
        }

        if (cboxProyecto.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto.");
            return false;
        }

        String titulo = textField1.getText().trim();
        String descripcion = textField2.getText().trim();
        String fechaInicio = textField3.getText().trim();
        String fechaFin = textField4.getText().trim();

        if (titulo.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos."
            );
            return false;
        }
        if (cboxEstado.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estado."
            );
            return false;
        }
        if (cboxPrioridad.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una prioridad."
            );
            return false;
        }
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            if (fin.isBefore(inicio)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin no puede ser menor que la fecha de inicio."
                );
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Las fechas deben tener el formato AAAA-MM-DD."
            );
            return false;
        }
        return true;
    }
    //LIMPIAR EL FORMULARIO
    private void limpiarFormulario() {
        cboxUsuario.setSelectedIndex(-1);
        cboxProyecto.setSelectedIndex(-1);
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        cboxEstado.setSelectedIndex(-1);
        cboxPrioridad.setSelectedIndex(-1);
        tablaTarea.clearSelection();
        btnRegistrarTarea.setText("Registrar");
        textField1.requestFocus();
    }
    //registrar tarea
    private void registrarTarea() {
        if (!validarFormulario()) {
            return;
        }
        ComboItem usuario = (ComboItem) cboxUsuario.getSelectedItem();
        ComboItem proyecto = (ComboItem) cboxProyecto.getSelectedItem();
        Tarea tarea = new Tarea(0,
                usuario.getId(),
                proyecto.getId(),
                textField1.getText().trim(),
                textField2.getText().trim(),
                cboxEstado.getSelectedItem().toString(),
                LocalDate.parse(textField3.getText().trim()),
                LocalDate.parse(textField4.getText().trim()),
                cboxPrioridad.getSelectedItem().toString()
        );
        int filas = tareaDAO.registrarTarea(tarea);
        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Tarea registrada correctamente.");
            cargarTareas();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la tarea.");
        }
    }

    private void cargarTareaEdicion(int fila) {
        if (fila == -1) {
            return;
        }
        TareaVista tarea = listaTareasVista.get(fila);
        tareaActual = new Tarea();
        tareaActual.setIdTarea(tarea.getIdTarea());
        tareaActual.setIdUsuario(tarea.getIdUsuario());
        tareaActual.setIdProyecto(tarea.getIdProyecto());
        tareaActual.setTitulo(tarea.getTitulo());
        tareaActual.setDescripcion(tarea.getDescripcion());
        tareaActual.setEstadoTarea(tarea.getEstadoTarea());
        tareaActual.setFechaInicio(tarea.getFechaInicio());
        tareaActual.setFechaFin(tarea.getFechaFin());
        tareaActual.setPrioridad(tarea.getPrioridad());
        //Seleccionar Usuario
        cboxUsuario.setSelectedItem(new ComboItem(tarea.getIdUsuario(), tarea.getNombreUsuario()));
        //Seleccionar Proyecto
        cboxProyecto.setSelectedItem(
                new ComboItem(tarea.getIdProyecto(), tarea.getNombreProyecto()));
        textField1.setText(tarea.getTitulo());
        textField2.setText(tarea.getDescripcion());
        textField3.setText(tarea.getFechaInicio().toString());
        textField4.setText(tarea.getFechaFin().toString());
        cboxEstado.setSelectedItem(tarea.getEstadoTarea());
        cboxPrioridad.setSelectedItem(tarea.getPrioridad());
        btnRegistrarTarea.setText("Actualizar");
    }
    //ELIMINAR TAREA
    private void actualizarTarea() {
        if (!validarFormulario()) {
            return;
        }
        ComboItem usuario = (ComboItem) cboxUsuario.getSelectedItem();
        ComboItem proyecto = (ComboItem) cboxProyecto.getSelectedItem();
        Tarea tarea = new Tarea(
                tareaActual.getIdTarea(),
                usuario.getId(),
                proyecto.getId(),
                textField1.getText().trim(),
                textField2.getText().trim(),
                cboxEstado.getSelectedItem().toString(),
                LocalDate.parse(textField3.getText().trim()),
                LocalDate.parse(textField4.getText().trim()),
                cboxPrioridad.getSelectedItem().toString());
        int filas = tareaDAO.actualizarTarea(tarea);
        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Tarea actualizada correctamente.");
            cargarTareas();
            limpiarFormulario();
            tareaActual = null;
            btnRegistrarTarea.setText("Registrar");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la tarea.");
        }
    }
    //ELIMINAR TAREA
    private void eliminarTarea() {
        int fila = tablaTarea.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                this, "¿Está seguro de eliminar esta tarea?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        );
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        int idTarea = listaTareasVista.get(fila).getIdTarea();
        int filas = tareaDAO.eliminarTarea(idTarea);
        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente.");
            cargarTareas();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la tarea.");
        }
    }
    private void aplicarFiltros() {
        List<TareaVista> tareas = tareaDAO.listarTareasVista();
        GestorTarea gestor = new GestorTarea(tareas);
        tareas = gestor.buscar(
                textField5.getText(),
                cboxBuscar.getSelectedItem().toString());
        gestor = new GestorTarea(tareas);
        tareas = gestor.ordenar(
                cboxOrdenarPor.getSelectedItem().toString(),
                cboxAscDesc.getSelectedItem().toString());
        actualizarTabla(tareas);
    }
    private void configurarEventosBusqueda() {
        textField5.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
        });
        cboxBuscar.addActionListener(e -> aplicarFiltros());
        cboxOrdenarPor.addActionListener(e -> aplicarFiltros());
        cboxAscDesc.addActionListener(e -> aplicarFiltros());

    }
    private void configurarEventosCRUD() {
        // Registrar - Actualizar
        btnRegistrarTarea.addActionListener(e -> {
            if (btnRegistrarTarea.getText().equals("Registrar")) {
                registrarTarea();
            } else {
                actualizarTarea();
            }
        });
        // Limpiar formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        // Regresar
        btnRegresar.addActionListener(e -> {
            new PanelPRINCIPAL().setVisible(true);
            dispose();
        });
        // Doble clic en la tabla
        tablaTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cargarTareaEdicion(tablaTarea.getSelectedRow());
                }
            }
        });
        // Eliminar
        btnEliminarTarea.addActionListener(e -> eliminarTarea());
    }

}
