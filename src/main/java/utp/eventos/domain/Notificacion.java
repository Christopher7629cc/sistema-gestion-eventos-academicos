package utp.eventos.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notificacion {
    private int idNotificacion;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean leida;
    private String tipo;

    public Notificacion(int idNotificacion, String mensaje, String tipo) {
        this.idNotificacion = idNotificacion;
        this.mensaje = Objects.requireNonNull(mensaje);
        this.tipo = Objects.requireNonNull(tipo);
    }
    public void enviar() { fechaEnvio = LocalDateTime.now(); }
    public void marcarComoLeida() { leida = true; }
    public void generarAlertaEventoProximo(Evento evento) {
        Objects.requireNonNull(evento);
        mensaje = "Recordatorio: " + evento.getNombre() + " el " + evento.getFecha();
        tipo = "RECORDATORIO";
        leida = false;
        enviar();
    }
    public String getMensaje() { return mensaje; }
    public boolean isLeida() { return leida; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
}
