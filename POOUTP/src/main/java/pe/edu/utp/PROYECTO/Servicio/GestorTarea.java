package pe.edu.utp.PROYECTO.Servicio;

import pe.edu.utp.PROYECTO.modelo.TareaVista;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class GestorTarea extends Gestor<TareaVista> {
    public GestorTarea(List<TareaVista> lista) {
        super(lista);
    }

    public List<TareaVista> buscar(String texto, String criterio) {

        if (texto == null || texto.trim().isEmpty()) {
            return lista;
        }
        final String filtro = texto.toLowerCase();
        switch (criterio) {
            case "Usuario":
                return lista.stream().filter(t -> t.getNombreUsuario().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Proyecto":
                return lista.stream().filter(t -> t.getNombreProyecto().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Título":
                return lista.stream().filter(t -> t.getTitulo().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Descripción":
                return lista.stream().filter(t -> t.getDescripcion().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Estado":
                return lista.stream().filter(t -> t.getEstadoTarea().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Fecha Inicio":
                return lista.stream().filter(t -> t.getFechaInicio().toString().contains(filtro)).collect(Collectors.toList());
            case "Fecha Fin":
                return lista.stream().filter(t -> t.getFechaFin().toString().contains(filtro)).collect(Collectors.toList());
            case "Prioridad":
                return lista.stream().filter(t -> t.getPrioridad().toLowerCase().contains(filtro)).collect(Collectors.toList());
            default:
                return lista;
        }
    }
    public List<TareaVista> ordenar(String criterio, String direccion) {
        Comparator<TareaVista> comparator;
        switch (criterio) {

            case "Usuario":
                comparator = Comparator.comparing(TareaVista::getNombreUsuario, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Proyecto":
                comparator = Comparator.comparing(TareaVista::getNombreProyecto, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Título":
                comparator = Comparator.comparing(TareaVista::getTitulo, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Fecha Inicio":
                comparator = Comparator.comparing(TareaVista::getFechaInicio);
                break;
            case "Fecha Fin":
                comparator = Comparator.comparing(TareaVista::getFechaFin);
                break;
            case "Estado":
                comparator = Comparator.comparing(TareaVista::getEstadoTarea, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Prioridad":
                comparator = Comparator.comparing(TareaVista::getPrioridad, String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                return lista;
        }
        if ("Descendente".equals(direccion)) {
            comparator = comparator.reversed();
        }
        return lista.stream().sorted(comparator).collect(Collectors.toList());
    }
}
