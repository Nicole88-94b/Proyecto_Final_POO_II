package modelo;

public class Estudiante extends Persona {
    private int idEstudiante;
    private String curso;

    public Estudiante(String nombre, String rut, String correo, int idEstudiante, String curso) {
        super(nombre, rut, correo);
        this.idEstudiante = idEstudiante;
        setCurso(curso);
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un curso");
        }
        this.curso = curso.trim();
    }
}
