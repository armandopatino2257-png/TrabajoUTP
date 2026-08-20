package pe.edu.utp.PROYECTO.vista;

import pe.edu.utp.PROYECTO.AccesoDatos.UsuarioDAO;
import pe.edu.utp.PROYECTO.Servicio.GestorUsuario;
import pe.edu.utp.PROYECTO.modelo.Usuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelUsuario extends JFrame{
    private JPanel jpUsuario;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton btnRegistrarUsuario;
    private JButton btnLimpiar;
    private JTextField textField5;
    private JTable tableUsuario;
    private DefaultTableModel tModel;
    private JLabel txtDNI;
    private JLabel txtNombre;
    private JLabel txtApellido;
    private JLabel txtCorreo;
    private JLabel txtTelefono;
    private JButton btnEliminar;
    private JLabel txtBuscarUsuario;
    private JTextField textField6;
    private JComboBox cboxCriterioBusqueda;
    private JLabel txtOrdenarPor;
    private JComboBox cboxCriterioOrdenamiento;
    private JButton btnRegresar;
    private JComboBox cboxDirección;
    private JLabel txtUsuarioRegistrado;
    private JLabel txtResultadoEncontrado;
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioActual = null;

    //PANEL USUARIO
    public PanelUsuario(){
        usuarioDAO = new UsuarioDAO();

        prepararFormularioUsuario();
        configurarEventosBusqueda();
        configurarEventosCRUD();
        cargarUsuarios();
        TablaUtil.ocultarColumnaID(tableUsuario);


    }
    //PREPARAR EL FORMULARIO
    private void prepararFormularioUsuario() {
        setContentPane(jpUsuario);
        setTitle("GESTIÓN DE USUARIOS");
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
        tModel.addColumn("DNI");
        tModel.addColumn("Nombre");
        tModel.addColumn("Apellido");
        tModel.addColumn("Email");
        tModel.addColumn("Teléfono");
        tableUsuario.setModel(tModel);
        cboxCriterioBusqueda.removeAllItems();
        cboxCriterioOrdenamiento.removeAllItems();
        cboxDirección.removeAllItems();
        cboxCriterioBusqueda.addItem("DNI");
        cboxCriterioBusqueda.addItem("Nombre");
        cboxCriterioBusqueda.addItem("Apellido");
        cboxCriterioBusqueda.addItem("Email");
        cboxCriterioBusqueda.addItem("Teléfono");
        cboxCriterioOrdenamiento.addItem("DNI");
        cboxCriterioOrdenamiento.addItem("Nombre");
        cboxCriterioOrdenamiento.addItem("Apellido");
        cboxCriterioOrdenamiento.addItem("Email");
        cboxCriterioOrdenamiento.addItem("Teléfono");
        cboxDirección.addItem("Ascendente");
        cboxDirección.addItem("Descendente");


    }
    //CARGAR TABLA USUARIO
    private void cargarUsuarios() {
        actualizarTabla(usuarioDAO.listarUsuario());
    }

    //VALIDAR EL FORMULARIO
    private boolean validarFormulario() {
        String dni = textField1.getText().trim();
        String nombre = textField2.getText().trim();
        String apellido = textField3.getText().trim();
        String correo = textField4.getText().trim();
        String telefono = textField5.getText().trim();
        // Campos vacíos
        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe completar todos los campos.",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // DNI
        System.out.println("[" + dni + "]");
        System.out.println("Longitud: " + dni.length());
        System.out.println(dni.matches("\\d{8}"));
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe tener exactamente 8 dígitos.",
                    "DNI inválido",
                    JOptionPane.ERROR_MESSAGE);
            textField1.requestFocus();
            return false;
        }
        // Nombre
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this,
                    "El nombre solo puede contener letras.",
                    "Nombre inválido",
                    JOptionPane.ERROR_MESSAGE);
            textField2.requestFocus();
            return false;
        }
        // Apellido
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this,
                    "El apellido solo puede contener letras.",
                    "Apellido inválido",
                    JOptionPane.ERROR_MESSAGE);
            textField3.requestFocus();
            return false;
        }
        // Correo
        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un correo electrónico válido.",
                    "Correo inválido",
                    JOptionPane.ERROR_MESSAGE);
            textField4.requestFocus();
            return false;
        }
        // Teléfono
        if (!telefono.matches("9\\d{8}")) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono debe comenzar con 9 y tener 9 dígitos.",
                    "Teléfono inválido",
                    JOptionPane.ERROR_MESSAGE);
            textField4.requestFocus();
            return false;
        }
        return true;
    }
    //LIMPIAR EL FORMULARIO
    private void limpiarFormulario() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        usuarioActual = null;
        btnRegistrarUsuario.setText("Registrar");
        tableUsuario.clearSelection();
        textField1.requestFocus();

    }
    //REGISTRAR EL USUARIO
    private void registrarUsuario() {
        if (!validarFormulario()) {
            return;
        }
        Usuario usuario = new Usuario(
                0,
                Integer.parseInt(textField1.getText().trim()),
                textField2.getText().trim(),
                textField3.getText().trim(),
                textField4.getText().trim(),
                textField5.getText().trim()
        );
        int filas = usuarioDAO.registrarUsuario(usuario);
        if (filas > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Usuario registrado correctamente."
            );
            cargarUsuarios();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el usuario."
            );
        }

    }
    //EDITAR USUARIO
    private void cargarUsuarioEdicion(int fila) {
        if (fila == -1) {
            return;
        }
        usuarioActual = new Usuario();
        usuarioActual.setIdUsuario(
                Integer.parseInt(tableUsuario.getValueAt(fila,0).toString()));
        usuarioActual.setDni(
                Integer.parseInt(tableUsuario.getValueAt(fila,1).toString()));
        usuarioActual.setNombre(
                tableUsuario.getValueAt(fila,2).toString());
        usuarioActual.setApellido(
                tableUsuario.getValueAt(fila,3).toString());
        usuarioActual.setEmail(
                tableUsuario.getValueAt(fila,4).toString());
        usuarioActual.setTelefono(
                tableUsuario.getValueAt(fila,5).toString());

        textField1.setText(String.valueOf(usuarioActual.getDni()));
        textField2.setText(usuarioActual.getNombre());
        textField3.setText(usuarioActual.getApellido());
        textField4.setText(usuarioActual.getEmail());
        textField5.setText(usuarioActual.getTelefono());

        btnRegistrarUsuario.setText("Actualizar");
    }
    //actualizar usuario
    private void actualizarUsuario() {

        if (!validarFormulario()) {
            return;
        }
        Usuario usuario = new Usuario(
                usuarioActual.getIdUsuario(),
                Integer.parseInt(textField1.getText().trim()),
                textField2.getText().trim(),
                textField3.getText().trim(),
                textField4.getText().trim(),
                textField5.getText().trim());
        int filas = usuarioDAO.actualizarUsuario(usuario);
        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente."
            );
            cargarUsuarios();
            limpiarFormulario();
            usuarioActual = null;
            btnRegistrarUsuario.setText("Registrar");
        } else {
            JOptionPane.showMessageDialog(this,"No se pudo actualizar el usuario."
            );
        }
    }
    //ELIMINAR USUARIO
    private void eliminarUsuario() {
        int fila = tableUsuario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un usuario.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        int idUsuario = Integer.parseInt(tableUsuario.getValueAt(fila,0).toString()
        );
        int filas = usuarioDAO.eliminarUsuario(idUsuario);
        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            cargarUsuarios();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario."
            );
        }
    }
    //ACTUALIZAR TABLA (BUSQUEDA)
    private void actualizarTabla(List<Usuario> usuarios) {
        DefaultTableModel modelo = (DefaultTableModel) tableUsuario.getModel();
        modelo.setRowCount(0);
        for (Usuario usuario : usuarios) {
            modelo.addRow(new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getDni(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getTelefono()
            });
        }
        TablaUtil.ocultarColumnaID(tableUsuario);
        txtUsuarioRegistrado.setText("Usuarios registrados: " + usuarioDAO.listarUsuario().size());
        txtResultadoEncontrado.setText("Resultados encontrados: " + usuarios.size());
    }
    private void aplicarFiltros() {
        List<Usuario> usuarios = usuarioDAO.listarUsuario();
        GestorUsuario gestor = new GestorUsuario(usuarios);
        // Buscar
        System.out.println(cboxCriterioBusqueda.getSelectedItem());
        System.out.println(cboxCriterioOrdenamiento.getSelectedItem());
        System.out.println(cboxDirección.getSelectedItem());
        usuarios = gestor.buscar(
                textField6.getText(),
                cboxCriterioBusqueda.getSelectedItem().toString()
        );
        // Crear un nuevo gestor con la lista filtrada
        gestor = new GestorUsuario(usuarios);
        // Ordenar
        usuarios = gestor.ordenar(
                cboxCriterioOrdenamiento.getSelectedItem().toString(),
                cboxDirección.getSelectedItem().toString());
        // Mostrar resultados
        actualizarTabla(usuarios);
    }
    private void configurarEventosBusqueda() {
        // Buscar mientras se escribe
        textField6.getDocument().addDocumentListener(new DocumentListener() {
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
        cboxCriterioOrdenamiento.addActionListener(e -> aplicarFiltros());
        // Cambiar dirección
        cboxDirección.addActionListener(e -> aplicarFiltros());
    }

    private void configurarEventosCRUD() {
        // Registrar / Actualizar
        btnRegistrarUsuario.addActionListener(e -> {
            if (btnRegistrarUsuario.getText().equals("Registrar")) {
                registrarUsuario();
            } else {
                actualizarUsuario();
            }
        });
        // Limpiar
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        // Regresar
        btnRegresar.addActionListener(e -> {
            new PanelPRINCIPAL().setVisible(true);
            dispose();
        });
        // Doble clic para editar
        tableUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cargarUsuarioEdicion(tableUsuario.getSelectedRow());
                }
            }
        });
        // Eliminar
        btnEliminar.addActionListener(e -> eliminarUsuario());

    }

}
