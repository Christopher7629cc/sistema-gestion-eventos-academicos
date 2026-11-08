package utp.eventos.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Inscripcion {
    private int idInscripcion;
    private LocalDateTime fechaInscripcion;
    private String estado, codigoAcceso;
    private final Participante participante;
    private final Evento evento;
    private final List<Notificacion> notificaciones = new ArrayList<>();
    private static int siguienteNotificacion = 1;

    public Inscripcion(int idInscripcion, Participante participante, Evento evento) {
        this.idInscripcion = idInscripcion;
        this.participante = Objects.requireNonNull(participante);
        this.evento = Objects.requireNonNull(evento);
        fechaInscripcion = LocalDateTime.now();
        estado = "PENDIENTE";
    }
    public void confirmar() {
        if (estaCancelada()) throw new IllegalStateException("No puede confirmar una inscripcion cancelada");
        if (estaConfirmada()) return;
        estado = "CONFIRMADA";
        codigoAcceso = "EV-" + evento.getIdEvento() + "-" + idInscripcion;
        notificarCambio();
    }
    public void cancelar() {
        if (estaCancelada()) return;
        estado = "CANCELADA";
        notificarCambio();
    }
    public Notificacion generarNotificacion() {
        return new Notificacion(siguienteNotificacion++,
            "Inscripcion " + estado.toLowerCase() + " para " + evento.getNombre(), "INSCRIPCION");
    }
    private void notificarCambio() {
        Notificacion n = generarNotificacion();
        n.enviar();
        notificaciones.add(n);
    }
    public List<Notificacion> getNotificaciones() {
        return Collections.unmodifiableList(notificaciones);
    }
    public boolean estaConfirmada() { return "CONFIRMADA".equals(estado); }
    public boolean estaCancelada() { return "CANCELADA".equals(estado); }
    public Participante getParticipante() { return participante; }
    public Evento getEvento() { return evento; }
    public String getCodigoAcceso() { return codigoAcceso; }
    public String getEstado() { return estado; }
}
