package modelo;

/**
 * Representa los datos personales compartidos por usuarios y estudiantes.
 * Centraliza las validaciones básicas de nombre, RUT y correo.
 */
public abstract class Persona {
    private String nombre;
    private String rut;
    private String correo;

    protected Persona(String nombre, String rut, String correo) {
        setNombre(nombre);
        setRut(rut);
        setCorreo(correo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El rut no puede estar vacío");
        }
        String rutSinEspacios = rut.trim();
        if (!rutSinEspacios.matches("[0-9]{7,8}-[0-9kK]")) {
            throw new IllegalArgumentException("El RUT debe escribirse sin puntos y con guion");
        }
        this.rut = rutSinEspacios;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        this.correo = correo.trim();
    }
}
