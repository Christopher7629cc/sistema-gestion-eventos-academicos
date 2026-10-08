package utp.eventos.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Horario {
    private int idHorario;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public boolean validarDisponibilidad() { return true; }
    public void asignarEvento(Evento evento) {}
    public void modificarHorario(LocalTime inicio, LocalTime fin) {}
}
