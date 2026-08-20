package pe.edu.utp.PROYECTO.AccesoDatos;

import pe.edu.utp.PROYECTO.modelo.Proyecto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProyectoDAO {

    public ArrayList<Proyecto> listarProyectos(){
        ArrayList<Proyecto> proyectos = new ArrayList<>();

        String sql = "select idProyecto, nombre, descripcion, fechaInicio, fechaFin, estadoProyecto from Proyecto";
        try {
            PreparedStatement consulta = ConexionBDSQLServer.getInstancia()
                    .getConexion().prepareStatement(sql);
            ResultSet filas = consulta.executeQuery();
            while(filas.next()){
                proyectos.add(new Proyecto(
                        filas.getInt(1),
                        filas.getString(2),
                        filas.getString(3),
                        filas.getDate(4).toLocalDate(),
                        filas.getDate(5).toLocalDate(),
                        filas.getString(6)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proyectos;
    }

    public int registrarProyecto(Proyecto proyecto) {
        String sql = " INSERT INTO Proyecto (nombre, descripcion, fechaInicio, fechaFin, estadoProyecto) VALUES (?, ?, ?, ?, ?) ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);

            consulta.setString(1, proyecto.getNombre());
            consulta.setString(2, proyecto.getDescripcion());
            consulta.setDate(3, java.sql.Date.valueOf(proyecto.getFechaInicio()));
            consulta.setDate(4, java.sql.Date.valueOf(proyecto.getFechaFin()));
            consulta.setString(5, proyecto.getEstadoProyecto());
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarProyecto(Proyecto proyecto) {
        String sql = "UPDATE Proyecto SET nombre = ?, descripcion = ?, fechaInicio = ?, fechaFin = ?, estadoProyecto = ? WHERE idProyecto = ? ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);
            consulta.setString(1, proyecto.getNombre());
            consulta.setString(2, proyecto.getDescripcion());
            consulta.setDate(3, java.sql.Date.valueOf(proyecto.getFechaInicio()));
            consulta.setDate(4, java.sql.Date.valueOf(proyecto.getFechaFin()));
            consulta.setString(5, proyecto.getEstadoProyecto());
            consulta.setInt(6, proyecto.getIdProyecto());
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarProyecto(int idProyecto) {
        String sql = "DELETE FROM Proyecto WHERE idProyecto = ?";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);
            consulta.setInt(1, idProyecto);
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
