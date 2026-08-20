package pe.edu.utp.PROYECTO.Servicio;

import java.util.ArrayList;
import java.util.List;

public abstract class Gestor<T> {

    protected List<T> lista;

    public Gestor(List<T> lista) {
        this.lista = lista;
    }

    protected List<T> obtenerLista() {
        return lista;
    }
}
