package pe.edu.utp.PROYECTO.modelo;

public class Usuario {
    private int idUsuario;
    private int dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    public Usuario(){}

    public Usuario(int idUsuario, int dni, String nombre, String apellido,
                   String email, String telefono) {
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    @Override
    public String toString() {
        return String.format(
                "Usuario\n" +
                        "----------------------------\n" +
                        "ID         : %d\n" +
                        "DNI        : %d\n" +
                        "Nombre     : %s\n" +
                        "Apellido   : %s\n" +
                        "Email      : %s\n" +
                        "Teléfono   : %s",
                idUsuario,
                dni,
                nombre,
                apellido,
                email,
                telefono
        );
    }
}
