package pe.edu.utp.PROYECTO.AccesoDatos;

import pe.edu.utp.PROYECTO.modelo.Tarea;
import pe.edu.utp.PROYECTO.modelo.TareaVista;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TareaDao {

    public ArrayList<Tarea> listarTareas(){
        ArrayList<Tarea> tareas = new ArrayList<>();
        String sql = "select idTarea,idUsuario,idProyecto, titulo, descripcion, estadoTarea,fechaInicio,fechaFin,prioridad from Tarea";
        try {
            PreparedStatement consulta = ConexionBDSQLServer.getInstancia()
                    .getConexion().prepareStatement(sql);
            ResultSet filas = consulta.executeQuery();
            while(filas.next()){
                tareas.add(new Tarea(
                        filas.getInt(1),
                        filas.getInt(2),
                        filas.getInt(3),
                        filas.getString(4),
                        filas.getString(5),
                        filas.getString(6),
                        filas.getDate(7).toLocalDate(),
                        filas.getDate(8).toLocalDate(),
                        filas.getString(9)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tareas;
    }
    //EL JTABLE DE TAREA USARA LA CLASE TAREA VISTA
    public ArrayList<TareaVista> listarTareasVista() {
        ArrayList<TareaVista> tareas = new ArrayList<>();
        String sql = " SELECT t.idTarea, t.idUsuario, t.idProyecto, CONCAT(u.nombre,' ',u.apellido) AS nombreUsuario, p.nombre AS nombreProyecto, t.titulo, t.descripcion, t.estadoTarea, t.fechaInicio, t.fechaFin, t.prioridad FROM Tarea t INNER JOIN Usuario u ON t.idUsuario = u.idUsuario INNER JOIN Proyecto p ON t.idProyecto = p.idProyecto ORDER BY t.idTarea ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer.getInstancia().getConexion().prepareStatement(sql);
            ResultSet filas = consulta.executeQuery();
            while (filas.next()) {
                tareas.add(new TareaVista(
                                filas.getInt("idTarea"),
                                filas.getInt("idUsuario"),
                                filas.getInt("idProyecto"),
                                filas.getString("nombreUsuario"),
                                filas.getString("nombreProyecto"),
                                filas.getString("titulo"),
                                filas.getString("descripcion"),
                                filas.getString("estadoTarea"),
                                filas.getDate("fechaInicio").toLocalDate(),
                                filas.getDate("fechaFin").toLocalDate(),
                                filas.getString("prioridad")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tareas;
    }

    public int registrarTarea(Tarea tarea) {

        String sql = "INSERT INTO Tarea (idUsuario,idProyecto,titulo,descripcion, estadoTarea,fechaInicio,fechaFin,prioridad) VALUES (?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);
            consulta.setInt(1, tarea.getIdUsuario());
            consulta.setInt(2, tarea.getIdProyecto());
            consulta.setString(3, tarea.getTitulo());
            consulta.setString(4, tarea.getDescripcion());
            consulta.setString(5, tarea.getEstadoTarea());
            consulta.setDate(6, java.sql.Date.valueOf(tarea.getFechaInicio()));
            consulta.setDate(7, java.sql.Date.valueOf(tarea.getFechaFin()));
            consulta.setString(8, tarea.getPrioridad());
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarTarea(Tarea tarea) {
        String sql = "UPDATE Tarea SET idUsuario = ?, idProyecto = ?, titulo = ?, descripcion = ?, estadoTarea = ?, fechaInicio = ?, fechaFin = ?, prioridad = ? WHERE idTarea = ? ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer.getInstancia().getConexion().prepareStatement(sql);
            consulta.setInt(1, tarea.getIdUsuario());
            consulta.setInt(2, tarea.getIdProyecto());
            consulta.setString(3, tarea.getTitulo());
            consulta.setString(4, tarea.getDescripcion());
            consulta.setString(5, tarea.getEstadoTarea());
            consulta.setDate(6, java.sql.Date.valueOf(tarea.getFechaInicio()));
            consulta.setDate(7, java.sql.Date.valueOf(tarea.getFechaFin()));
            consulta.setString(8, tarea.getPrioridad());
            consulta.setInt(9, tarea.getIdTarea());
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarTarea(int idTarea) {
        String sql = "DELETE FROM Tarea WHERE idTarea = ?";
        try {
            PreparedStatement consulta = ConexionBDSQLServer.getInstancia().getConexion().prepareStatement(sql);
            consulta.setInt(1, idTarea);
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
