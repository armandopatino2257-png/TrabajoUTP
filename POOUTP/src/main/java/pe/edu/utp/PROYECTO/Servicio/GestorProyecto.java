package pe.edu.utp.PROYECTO.Servicio;

import pe.edu.utp.PROYECTO.modelo.Proyecto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestorProyecto extends Gestor<Proyecto> {
    public GestorProyecto(List<Proyecto> lista) {
        super(lista);
    }

    public List<Proyecto> buscar(String texto, String criterio) {
        if (texto == null || texto.trim().isEmpty()) {
            return lista;
        }
        final String filtro = texto.toLowerCase();
        switch (criterio) {
            case "Nombre":
                return lista.stream().filter(p -> p.getNombre().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Descripción":
                return lista.stream().filter(p -> p.getDescripcion().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Estado":
                return lista.stream().filter(p -> p.getEstadoProyecto().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Fecha Inicio":
                return lista.stream().filter(p -> p.getFechaInicio().toString().contains(filtro)).collect(Collectors.toList());

            case "Fecha Fin":
                return lista.stream().filter(p -> p.getFechaFin().toString().contains(filtro)).collect(Collectors.toList());
            default:
                return lista;
        }
    }

    public List<Proyecto> ordenar(String criterio, String direccion) {
        Comparator<Proyecto> comparator;
        switch (criterio) {
            case "Nombre":
                comparator = Comparator.comparing(Proyecto::getNombre, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Fecha Inicio":
                comparator = Comparator.comparing(Proyecto::getFechaInicio);
                break;
            case "Fecha Fin":
                comparator = Comparator.comparing(Proyecto::getFechaFin);
                break;
            case "Estado":
                comparator = Comparator.comparing(Proyecto::getEstadoProyecto, String.CASE_INSENSITIVE_ORDER);
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