package pe.edu.utp.PROYECTO.modelo;

import java.time.LocalDate;

public class Tarea {
    private int idUsuario;
    private int idTarea;
    private int idProyecto;
    private String titulo;
    private String descripcion;
    private String estadoTarea;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String prioridad;

    public Tarea(){}

    public Tarea(int idTarea, int idUsuario, int idProyecto, String titulo, String descripcion,
                 String estadoTarea, LocalDate fechaInicio, LocalDate fechaFin, String prioridad) {
        this.idTarea = idTarea;
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estadoTarea = estadoTarea;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.prioridad = prioridad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(String estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return String.format(
                "Tarea\n" +
                        "----------------------------\n" +
                        "ID Tarea      : %d\n" +
                        "ID Usuario    : %d\n" +
                        "ID Proyecto   : %d\n" +
                        "Título        : %s\n" +
                        "Descripción   : %s\n" +
                        "Estado        : %s\n" +
                        "Fecha Inicio  : %s\n" +
                        "Fecha Fin     : %s\n" +
                        "Prioridad     : %s",
                idTarea,
                idUsuario,
                idProyecto,
                titulo,
                descripcion,
                estadoTarea,
                fechaInicio,
                fechaFin,
                prioridad
        );
    }
}
