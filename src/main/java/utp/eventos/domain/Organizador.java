package utp.eventos.domain;

public class Organizador extends Usuario {
    private int idOrganizador;
    private String dependencia;

    public void crearEvento(Evento evento) {}
    public void modificarEvento(Evento evento) {}
    public void asignarEspacio(Evento evento, Espacio espacio) {}
    public void asignarHorario(Evento evento, Horario horario) {}
}
