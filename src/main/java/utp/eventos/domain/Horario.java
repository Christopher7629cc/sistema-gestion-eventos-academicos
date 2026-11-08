package utp.eventos.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Horario {
    private int idHorario;
    private LocalDate fecha;
    private LocalTime horaInicio, horaFin;
    private Evento evento;

    public Horario(int idHorario, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.idHorario = idHorario;
        this.fecha = Objects.requireNonNull(fecha);
        validarHoras(horaInicio, horaFin);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public boolean validarDisponibilidad() { return evento == null; }
    public void asignarEvento(Evento evento) {
        Objects.requireNonNull(evento);
        if (this.evento != null && this.evento != evento)
            throw new IllegalStateException("Horario asignado a otro evento");
        this.evento = evento;
    }

    public void modificarHorario(LocalTime inicio, LocalTime fin) {
        validarHoras(inicio, fin);
        Horario candidato = new Horario(idHorario, fecha, inicio, fin);
        if (evento != null && evento.getEspacio() != null) {
            Espacio espacio = evento.getEspacio();
            if (!espacio.validarDisponibilidad(candidato, evento))
                throw new IllegalStateException("El nuevo horario coincide con otra reserva");
            espacio.reservar(evento, candidato);
        }
        horaInicio = inicio;
        horaFin = fin;
    }

    private static void validarHoras(LocalTime inicio, LocalTime fin) {
        if (inicio == null || fin == null || !inicio.isBefore(fin))
            throw new IllegalArgumentException("El inicio debe ser anterior al final");
    }
    void desasignarEvento(Evento evento) { if (this.evento == evento) this.evento = null; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
}
