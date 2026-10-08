package utp.eventos.domain;

import java.time.LocalDateTime;

public class Notificacion {
    private int idNotificacion;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean leida;
    private String tipo;

    public void enviar() {}
    public void marcarComoLeida() { leida = true; }
    public void generarAlertaEventoProximo(Evento evento) {}
}
