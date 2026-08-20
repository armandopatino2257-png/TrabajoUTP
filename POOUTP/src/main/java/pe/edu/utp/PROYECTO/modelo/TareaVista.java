package pe.edu.utp.PROYECTO.modelo;

import java.time.LocalDate;

public class TareaVista {

    private int idTarea;
    private int idUsuario;
    private int idProyecto;
    private String nombreUsuario;
    private String nombreProyecto;
    private String titulo;
    private String descripcion;
    private String estadoTarea;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String prioridad;

    public TareaVista() {
    }

    public TareaVista(int idTarea, int idUsuario, int idProyecto, String nombreUsuario, String nombreProyecto, String titulo,
                      String descripcion, String estadoTarea, LocalDate fechaInicio, LocalDate fechaFin, String prioridad) {

        this.idTarea = idTarea;
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
        this.nombreUsuario = nombreUsuario;
        this.nombreProyecto = nombreProyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estadoTarea = estadoTarea;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.prioridad = prioridad;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }


    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstadoTarea() {
        return estadoTarea;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getPrioridad() {
        return prioridad;
    }

    @Override
    public String toString() {
        return String.format(
                "TareaVista\n" +
                        "----------------------------\n" +
                        "ID Tarea      : %d\n" +
                        "Usuario       : %s\n" +
                        "Proyecto      : %s\n" +
                        "Título        : %s\n" +
                        "Estado        : %s\n" +
                        "Prioridad     : %s",
                idTarea,
                nombreUsuario,
                nombreProyecto,
                titulo,
                estadoTarea,
                prioridad
        );
    }
}
