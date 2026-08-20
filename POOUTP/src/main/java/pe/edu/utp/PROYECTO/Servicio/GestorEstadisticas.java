package pe.edu.utp.PROYECTO.Servicio;

import pe.edu.utp.PROYECTO.modelo.Proyecto;
import pe.edu.utp.PROYECTO.modelo.TareaVista;

import java.util.List;

public class GestorEstadisticas {

    private List<Proyecto> proyectos;
    private List<TareaVista> tareas;

    public GestorEstadisticas(List<Proyecto> proyectos, List<TareaVista> tareas) {
        this.proyectos = proyectos;
        this.tareas = tareas;
    }

    public long contarProyectosActivos() {
        return proyectos.stream().filter(p -> p.getEstadoProyecto().equalsIgnoreCase("Activo")).count();
    }
    public long contarProyectosPausados() {
        return proyectos.stream().filter(p -> p.getEstadoProyecto().equalsIgnoreCase("Pausado")).count();
    }
    public long contarProyectosFinalizados() {
        return proyectos.stream().filter(p -> p.getEstadoProyecto().equalsIgnoreCase("Finalizado")).count();

    }

    public long contarTareasPendientes() {
        return tareas.stream().filter(t -> t.getEstadoTarea().equalsIgnoreCase("Pendiente")).count();
    }
    public long contarTareasEnCurso() {
        return tareas.stream().filter(t -> t.getEstadoTarea().equalsIgnoreCase("En Curso")).count();
    }
    public long contarTareasFinalizadas() {
        return tareas.stream().filter(t -> t.getEstadoTarea().equalsIgnoreCase("Finalizado")).count();
    }

    public long contarPrioridadAlta() {
        return tareas.stream().filter(t -> t.getPrioridad().equalsIgnoreCase("Alta")).count();
    }
    public long contarPrioridadMedia() {
        return tareas.stream().filter(t -> t.getPrioridad().equalsIgnoreCase("Media")).count();
    }
    public long contarPrioridadBaja() {
        return tareas.stream().filter(t -> t.getPrioridad().equalsIgnoreCase("Baja")).count();
    }
    public long contarTareasTotales() {

        return tareas.size();

    }

    public long contarTareasFinalizadasTotal() {
        return tareas.stream().filter(t -> t.getEstadoTarea().equalsIgnoreCase("Finalizado")).count();
    }

    public double calcularAvanceProyecto() {
        long total = contarTareasTotales();
        if (total == 0) {
            return 0;
        }
        long finalizadas = contarTareasFinalizadasTotal();
        return (finalizadas * 100.0) / total;
    }
}