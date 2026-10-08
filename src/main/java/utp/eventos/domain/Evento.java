package utp.eventos.domain;

import java.time.LocalDate;

public class Evento {
    private int idEvento;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private int cupoMaximo;
    private String estado;

    public boolean validarCupo() { return false; }
    public Inscripcion registrarInscripcion(Participante participante) { return null; }
    public void asignarEspacio(Espacio espacio) {}
    public void asignarHorario(Horario horario) {}
    public Reporte generarReporte() { return null; }
}
