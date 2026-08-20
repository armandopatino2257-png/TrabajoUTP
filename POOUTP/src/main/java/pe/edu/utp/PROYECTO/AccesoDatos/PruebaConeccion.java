package pe.edu.utp.PROYECTO.AccesoDatos;

import pe.edu.utp.PROYECTO.modelo.Proyecto;
import pe.edu.utp.PROYECTO.modelo.Tarea;
import pe.edu.utp.PROYECTO.modelo.Usuario;

public class PruebaConeccion {
    public static void main(String[] args) {
        // ConexionBDSQLServer conexion = new ConexionBDSQLServer();
        //exitoso
        //MOSTRAR LOS DATOS DE LA TABLA USUARIO DESDE LA APP
            UsuarioDAO dao = new UsuarioDAO();
        for (Usuario usuario : dao.listarUsuario()) {
            System.out.println(usuario);
        }
        //MOSTRAR LOS DATOS DE LA TABLA PROYECTO DESDE LA APP
            ProyectoDAO daop = new ProyectoDAO();
        for (Proyecto proyecto : daop.listarProyectos()) {
            System.out.println(proyecto);
        }
        //MOSTRAR LOS DATOS DE LA TABLA TAREA DESDE LA APP
        TareaDao daot = new TareaDao();
        for (Tarea tarea : daot.listarTareas()) {
            System.out.println(tarea);
        }
    }
}
