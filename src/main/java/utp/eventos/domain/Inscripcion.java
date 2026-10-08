package utp.eventos.domain;

import java.time.LocalDateTime;

public class Inscripcion {
    private int idInscripcion;
    private LocalDateTime fechaInscripcion;
    private String estado;
    private String codigoAcceso;

    public void confirmar() {}
    public void cancelar() {}
    public boolean validarCupo() { return false; }
    public Notificacion generarNotificacion() { return null; }
}
