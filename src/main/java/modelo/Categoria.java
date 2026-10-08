package modelo;

public class Categoria {
    private int idCategoria;
    private String nombre;

    public Categoria(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        setNombre(nombre);
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }
    @Override
    public String toString() {
        return nombre;
    }
}
