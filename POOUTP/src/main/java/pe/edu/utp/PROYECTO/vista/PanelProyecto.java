package pe.edu.utp.PROYECTO.vista;

import pe.edu.utp.PROYECTO.AccesoDatos.ProyectoDAO;
import pe.edu.utp.PROYECTO.Servicio.GestorProyecto;
import pe.edu.utp.PROYECTO.modelo.Proyecto;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PanelProyecto extends JFrame{
    private JPanel jpProyecto;
    private JLabel txtNombre;
    private JLabel txtDescripcion;
    private JLabel txtInicio;
    private JLabel txtFin;
    private JLabel txtEstado;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton btnRegistrarProyecto;
    private JButton btnLimpiar;
    private JTable tablaProyecto;
    private DefaultTableModel tModel;
    private JButton btnEliminar;
    private JLabel txtProyectRegistrado;
    private JLabel txtResultEncontrado;
    private JLabel txtBuscarProyecto;
    private JTextField textField5;
    private JComboBox cboxCriterioBusqueda;
    private JComboBox cboxCriterioOrdenar;
    private JComboBox cboxDireccion;
    private JButton btnRegresar;
    private ProyectoDAO proyectoDAO;
    private Proyecto proyectoActual = null;


    public PanelProyecto() {
        proyectoDAO = new ProyectoDAO();

        prepararFormularioProyecto();
        configurarEventosBusqueda();
        configurarEventosCRUD();
        cargarEstados();
        cargarProyectos();
        TablaUtil.ocultarColumnaID(tablaProyecto);

    }

    private void prepararFormularioProyecto() {
        setContentPane(jpProyecto);
        setTitle("GESTIÓN DE PROYECTOS");
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
        tModel.addColumn("Nombre");
        tModel.addColumn("Descripción");
        tModel.addColumn("Fecha Inicio");
        tModel.addColumn("Fecha Fin");
        tModel.addColumn("Estado");
        tablaProyecto.setModel(tModel);
        cboxCriterioBusqueda.removeAllItems();
        cboxCriterioBusqueda.addItem("Nombre");
        cboxCriterioBusqueda.addItem("Descripción");
        cboxCriterioBusqueda.addItem("Fecha Inicio");
        cboxCriterioBusqueda.addItem("Fecha Fin");
        cboxCriterioBusqueda.addItem("Estado");
        cboxCriterioOrdenar.removeAllItems();
        cboxCriterioOrdenar.addItem("Nombre");
        cboxCriterioOrdenar.addItem("Fecha Inicio");
        cboxCriterioOrdenar.addItem("Fecha Fin");
        cboxCriterioOrdenar.addItem("Estado");
        cboxDireccion.removeAllItems();
        cboxDireccion.addItem("Ascendente");
        cboxDireccion.addItem("Descendente");
    }


    //VALIDAR FORMULARIO
    private boolean validarFormulario() {
        String nombre = textField1.getText().trim();
        String descripcion = textField2.getText().trim();
        String fechaInicio = textField3.getText().trim();
        String fechaFin = textField4.getText().trim();
        if (nombre.isEmpty()
                || descripcion.isEmpty()
                || fechaInicio.isEmpty()
                || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );
            return false;
        }
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            if (fin.isBefore(inicio)) {
                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de fin no puede ser menor que la fecha de inicio."
                );
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Las fechas deben tener el formato AAAA-MM-DD."
            );
            return false;
        }
        if (comboBox1.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un estado."
            );
            return false;
        }
        return true;
    }
    //LIMPIAR FORMULARIO
    private void limpiarFormulario() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        comboBox1.setSelectedIndex(-1);
        proyectoActual = null;
        btnRegistrarProyecto.setText("Registrar");
        tablaProyecto.clearSelection();
        textField1.requestFocus();
    }
    //REGISTRAR PRODUCTO
    private void registrarProyecto() {
        if (!validarFormulario()) {
            return;
        }
        Proyecto proyecto = new Proyecto(
                0,
                textField1.getText().trim(),
                textField2.getText().trim(),
                LocalDate.parse(textField3.getText().trim()),
                LocalDate.parse(textField4.getText().trim()),
                comboBox1.getSelectedItem().toString()
        );
        int filas = proyectoDAO.registrarProyecto(proyecto);
        if (filas > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Proyecto registrado correctamente."
            );
            cargarProyectos();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el proyecto."
            );
        }
    }
    //CARGAR PROYECTO, DOBLE CLICK
    private void cargarProyectoEdicion(int fila) {
        if (fila == -1) {
            return;
        }
        proyectoActual = new Proyecto();
        proyectoActual.setIdProyecto(Integer.parseInt(tablaProyecto.getValueAt(fila, 0).toString()));
        proyectoActual.setNombre(tablaProyecto.getValueAt(fila, 1).toString());
        proyectoActual.setDescripcion(tablaProyecto.getValueAt(fila, 2).toString());
        proyectoActual.setFechaInicio(LocalDate.parse(tablaProyecto.getValueAt(fila, 3).toString()));
        proyectoActual.setFechaFin(LocalDate.parse(tablaProyecto.getValueAt(fila, 4).toString()));
        proyectoActual.setEstadoProyecto(tablaProyecto.getValueAt(fila, 5).toString());
        //Cargar datos al formulario
        textField1.setText(proyectoActual.getNombre());
        textField2.setText(proyectoActual.getDescripcion());
        textField3.setText(proyectoActual.getFechaInicio().toString());
        textField4.setText(proyectoActual.getFechaFin().toString());
        comboBox1.setSelectedItem(proyectoActual.getEstadoProyecto());
        btnRegistrarProyecto.setText("Actualizar");
    }
    //ACTUALIZAR PROYECTO
    private void actualizarProyecto() {
        if (!validarFormulario()) {
            return;
        }
        Proyecto proyecto = new Proyecto(
                proyectoActual.getIdProyecto(),
                textField1.getText().trim(),
                textField2.getText().trim(),
                LocalDate.parse(textField3.getText().trim()),
                LocalDate.parse(textField4.getText().trim()),
                comboBox1.getSelectedItem().toString()

        );
        int filas = proyectoDAO.actualizarProyecto(proyecto);
        if (filas > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Proyecto actualizado correctamente."
            );
            cargarProyectos();
            limpiarFormulario();
            proyectoActual = null;
            btnRegistrarProyecto.setText("Registrar");
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el proyecto."
            );
        }
    }
    //ELIMINAR PROYECTO
    private void eliminarProyecto() {
        int fila = tablaProyecto.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un proyecto."
            );
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este proyecto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE

        );
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        int idProyecto = Integer.parseInt(
                tablaProyecto.getValueAt(fila,0).toString());
        int filas = proyectoDAO.eliminarProyecto(idProyecto);
        if (filas > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Proyecto eliminado correctamente.");
            cargarProyectos();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el proyecto.");
        }
    }
    private void cargarEstados() {
        comboBox1.removeAllItems();
        comboBox1.addItem("Activo");
        comboBox1.addItem("Pausado");
        comboBox1.addItem("Finalizado");
        comboBox1.setSelectedIndex(-1);
    }

    private void cargarProyectos() {
        actualizarTabla(proyectoDAO.listarProyectos());
    }

    private void actualizarTabla(List<Proyecto> proyectos) {
        tModel.setRowCount(0);
        for (Proyecto proyecto : proyectos) {
            tModel.addRow(new Object[]{
                    proyecto.getIdProyecto(),
                    proyecto.getNombre(),
                    proyecto.getDescripcion(),
                    proyecto.getFechaInicio(),
                    proyecto.getFechaFin(),
                    proyecto.getEstadoProyecto()
            });
        }
        TablaUtil.ocultarColumnaID(tablaProyecto);
        txtProyectRegistrado.setText("Proyectos registrados: " + proyectoDAO.listarProyectos().size());
        txtResultEncontrado.setText("Resultados encontrados: " + proyectos.size());
    }
    private void aplicarFiltros() {
        List<Proyecto> proyectos = proyectoDAO.listarProyectos();
        GestorProyecto gestor = new GestorProyecto(proyectos);
        proyectos = gestor.buscar(textField5.getText(), cboxCriterioBusqueda.getSelectedItem().toString());
        gestor = new GestorProyecto(proyectos);
        proyectos = gestor.ordenar(cboxCriterioOrdenar.getSelectedItem().toString(), cboxDireccion.getSelectedItem().toString());
        actualizarTabla(proyectos);
    }
    private void configurarEventosBusqueda() {
        // Buscar mientras se escribe
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
        // Cambiar criterio de búsqueda
        cboxCriterioBusqueda.addActionListener(e -> aplicarFiltros());
        // Cambiar criterio de ordenamiento
        cboxCriterioOrdenar.addActionListener(e -> aplicarFiltros());
        // Cambiar dirección
        cboxDireccion.addActionListener(e -> aplicarFiltros());
    }
    private void configurarEventosCRUD() {
        btnRegistrarProyecto.addActionListener(e -> {
            if (btnRegistrarProyecto.getText().equals("Registrar")) {
                registrarProyecto();
            } else {
                actualizarProyecto();
            }
        });
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegresar.addActionListener(e -> {
            new PanelPRINCIPAL().setVisible(true);
            dispose();
        });
        tablaProyecto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cargarProyectoEdicion(tablaProyecto.getSelectedRow());
                }
            }
        });
        btnEliminar.addActionListener(e -> eliminarProyecto());
    }

}
