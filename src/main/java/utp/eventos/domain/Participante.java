package utp.eventos.domain;

public class Participante extends Usuario {
    private String matricula;
    private String carrera;
    private String telefono;

    public Inscripcion inscribirse(Evento evento) { return null; }
    public void cancelarInscripcion(Inscripcion inscripcion) {}
    public boolean verificarPerfil() { return true; }
}
