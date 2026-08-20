package pe.edu.utp.PROYECTO.vista;

public class ComboItem {
    private int id;
    private String descripcion;
    public ComboItem(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    public int getId() {
        return id;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ComboItem otro = (ComboItem) obj;
        return id == otro.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    @Override
    public String toString() {
        return descripcion;
    }

}
