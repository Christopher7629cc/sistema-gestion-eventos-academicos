package utp.eventos.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Una misma aula puede usarse para distintos eventos si sus horarios no chocan. */
public class Espacio {
    private int idEspacio;
    private String nombre, ubicacion;
    private int capacidad;
    private boolean disponible;
    private boolean bloqueoManual;
    private final Map<Evento, Horario> reservas = new HashMap<>();

    public Espacio(int idEspacio, String nombre, String ubicacion, int capacidad, boolean disponible) {
        if (capacidad < 1) throw new IllegalArgumentException("Capacidad invalida");
        this.idEspacio = idEspacio;
        this.nombre = Objects.requireNonNull(nombre);
        this.ubicacion = Objects.requireNonNull(ubicacion);
        this.capacidad = capacidad;
        this.disponible = disponible;
    }

    public boolean validarDisponibilidad() { return disponible && !bloqueoManual; }

    public boolean validarDisponibilidad(Horario horario, Evento excepto) {
        if (!validarDisponibilidad()) return false;
        for (Map.Entry<Evento, Horario> reserva : reservas.entrySet()) {
            if (reserva.getKey() == excepto) continue;
            Horario ocupado = reserva.getValue();
            boolean mismaFecha = ocupado.getFecha().equals(horario.getFecha());
            boolean solapamiento = horario.getHoraInicio().isBefore(ocupado.getHoraFin())
                && ocupado.getHoraInicio().isBefore(horario.getHoraFin());
            if (mismaFecha && solapamiento) return false;
        }
        return true;
    }

    /** Reservar sin parametros bloquea el espacio completamente (p. ej. mantenimiento). */
    public void reservar() {
        if (!validarDisponibilidad() || !reservas.isEmpty())
            throw new IllegalStateException("Espacio ocupado o no disponible");
        bloqueoManual = true;
    }
    public void liberar() { bloqueoManual = false; }

    public void reservar(Evento evento, Horario horario) {
        Objects.requireNonNull(evento);
        Objects.requireNonNull(horario);
        if (!validarDisponibilidad(horario, evento))
            throw new IllegalStateException("Conflicto de espacio, fecha y hora");
        reservas.put(evento, horario);
    }
    public void liberar(Evento evento) { reservas.remove(evento); }
    public boolean tieneCapacidad(int cupoRequerido) {
        return cupoRequerido >= 0 && capacidad >= cupoRequerido;
    }
}
