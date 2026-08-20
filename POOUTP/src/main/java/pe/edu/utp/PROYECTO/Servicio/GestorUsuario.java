package pe.edu.utp.PROYECTO.Servicio;

import pe.edu.utp.PROYECTO.modelo.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestorUsuario extends Gestor<Usuario> {

    public GestorUsuario(List<Usuario> lista) {
        super(lista);
    }
    public List<Usuario> buscar(String texto, String criterio) {
        if (texto == null || texto.trim().isEmpty()) {
            return lista;
        }
        final String filtro = texto.toLowerCase();
        switch (criterio) {
            case "DNI":
                return lista.stream().filter(u -> String.valueOf(u.getDni()).contains(filtro)).collect(Collectors.toList());
            case "Nombre":
                return lista.stream().filter(u -> u.getNombre().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Apellido":
                return lista.stream().filter(u -> u.getApellido().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Email":
                return lista.stream().filter(u -> u.getEmail().toLowerCase().contains(filtro)).collect(Collectors.toList());
            case "Teléfono":
                return lista.stream().filter(u -> u.getTelefono().contains(filtro)).collect(Collectors.toList());
            default:
                return lista;
        }
    }

    public List<Usuario> ordenar(String criterio, String direccion) {
        Comparator<Usuario> comparator;
        switch (criterio) {
            case "DNI":
                comparator = Comparator.comparing(Usuario::getDni);
                break;
            case "Nombre":
                comparator = Comparator.comparing(Usuario::getNombre, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Apellido":
                comparator = Comparator.comparing(Usuario::getApellido, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Email":
                comparator = Comparator.comparing(Usuario::getEmail, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Teléfono":
                comparator = Comparator.comparing(Usuario::getTelefono);
                break;
            default:
                return lista;
        }
        if (direccion.equals("Descendente")) {
            comparator = comparator.reversed();
        }
        return lista.stream().sorted(comparator).collect(Collectors.toList());
    }


}
