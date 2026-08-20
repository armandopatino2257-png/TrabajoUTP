package pe.edu.utp.PROYECTO.AccesoDatos;

import pe.edu.utp.PROYECTO.modelo.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    public ArrayList<Usuario> listarUsuario(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "select idUsuario,dni,nombre,apellido,email,telefono from Usuario";
        try {
            PreparedStatement consulta = ConexionBDSQLServer.getInstancia()
                    .getConexion().prepareStatement(sql);
            ResultSet filas = consulta.executeQuery();
            while(filas.next()){
                usuarios.add(new Usuario(
                        filas.getInt(1),
                        filas.getInt(2),
                        filas.getString(3),
                        filas.getString(4),
                        filas.getString(5),
                        filas.getString(6)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public int registrarUsuario(Usuario usuario) {
        String sql = " INSERT INTO Usuario (dni, nombre, apellido, email, telefono) VALUES (?, ?, ?, ?, ?) ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);

            consulta.setInt(1, usuario.getDni());
            consulta.setString(2, usuario.getNombre());
            consulta.setString(3, usuario.getApellido());
            consulta.setString(4, usuario.getEmail());
            consulta.setString(5, usuario.getTelefono());

            return consulta.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarUsuario(Usuario usuario) {
        String sql = " UPDATE Usuario SET dni = ?, nombre = ?, apellido = ?, email = ?, telefono = ? WHERE idUsuario = ? ";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);
            consulta.setInt(1, usuario.getDni());
            consulta.setString(2, usuario.getNombre());
            consulta.setString(3, usuario.getApellido());
            consulta.setString(4, usuario.getEmail());
            consulta.setString(5, usuario.getTelefono());
            consulta.setInt(6, usuario.getIdUsuario());
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";
        try {
            PreparedStatement consulta = ConexionBDSQLServer
                    .getInstancia()
                    .getConexion()
                    .prepareStatement(sql);
            consulta.setInt(1, idUsuario);
            return consulta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
