package pe.edu.utp.PROYECTO.modelo;

import java.time.LocalDate;

public class Proyecto {
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoProyecto;

    public Proyecto() {}

    public Proyecto(int idProyecto, String nombre, String descripcion, LocalDate fechaInicio,
                    LocalDate fechaFin, String estadoProyecto) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoProyecto = estadoProyecto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    @Override
    public String toString() {
        return String.format(
                "Proyecto\n" +
                        "----------------------------\n" +
                        "ID Proyecto : %d\n" +
                        "Nombre      : %s\n" +
                        "Descripción : %s\n" +
                        "Fecha Inicio: %s\n" +
                        "Fecha Fin   : %s\n" +
                        "Estado      : %s",
                idProyecto,
                nombre,
                descripcion,
                fechaInicio,
                fechaFin,
                estadoProyecto
        );
    }
}
