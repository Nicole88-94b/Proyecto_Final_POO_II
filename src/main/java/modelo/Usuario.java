package modelo;

/**
 * Representa una cuenta que puede autenticarse en el sistema.
 * El rol determina las funciones disponibles para bibliotecarios y estudiantes.
 */
public class Usuario extends Persona {
    private int idUsuario;
    private String contrasena;
    private String rol;

    public Usuario(String nombre, String rut, String correo, int idUsuario, String contrasena, String rol) {
        super(nombre, rut, correo);
        this.idUsuario = idUsuario;
        setContrasena(contrasena);
        setRol(rol);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar una contraseña");
        }
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un rol");
        }
        if (!rol.equals("bibliotecario") && !rol.equals("estudiante")) {
            throw new IllegalArgumentException("El usuario debe ser bibliotecario o estudiante");
        }
        this.rol = rol;
    }
}
