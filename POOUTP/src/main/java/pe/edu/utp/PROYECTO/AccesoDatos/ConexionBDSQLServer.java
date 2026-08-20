package pe.edu.utp.PROYECTO.AccesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDSQLServer {

    private Connection conexion = null;
    private static ConexionBDSQLServer instancia = null;
    public ConexionBDSQLServer() {
        String cadenaConexion =
                "jdbc:sqlserver://localhost:1433;"
                + "databaseName=GestionProyectos;"
                + "user=arman;"
                + "password=123456;"
                + "encrypt=true;"
                + "trustServerCertificate=true;";
        try {
            conexion = DriverManager.getConnection(cadenaConexion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static ConexionBDSQLServer getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBDSQLServer();
        }
        return instancia;
    }
    public Connection getConexion() {
        return conexion;
    }

}
