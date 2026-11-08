package utp.eventos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Organizador extends Usuario {
    private int idOrganizador;
    private String dependencia;
    private final List<Evento> eventos = new ArrayList<>();
    private Evento eventoSeleccionado;

    public Organizador(int id, String nombre, String correo, String contrasena, boolean activo,
                       int idOrganizador, String dependencia) {
        super(id, nombre, correo, contrasena, "ORGANIZADOR", activo);
        this.idOrganizador = idOrganizador;
        this.dependencia = dependencia;
    }

    public void crearEvento(Evento evento) {
        Objects.requireNonNull(evento);
        if (buscarEvento(evento.getIdEvento()) != null)
            throw new IllegalArgumentException("Ya existe un evento con ese ID");
        eventos.add(evento);
        eventoSeleccionado = evento;
        Participante.registrarEventoDisponible(evento);
    }

    /** Actualiza el evento administrado a partir de una nueva version con el mismo ID. */
    public void modificarEvento(Evento nuevaVersion) {
        Objects.requireNonNull(nuevaVersion);
        Evento existente = buscarEvento(nuevaVersion.getIdEvento());
        if (existente == null) throw new IllegalArgumentException("No administra este evento");
        if (existente != nuevaVersion) existente.actualizarDatosDesde(nuevaVersion);
        eventoSeleccionado = existente;
    }

    public void asignarEspacio(Espacio espacio) { seleccionar().asignarEspacio(espacio); }
    public void asignarHorario(Horario horario) { seleccionar().asignarHorario(horario); }
    private Evento seleccionar() {
        if (eventoSeleccionado == null) throw new IllegalStateException("Primero seleccione un evento");
        return eventoSeleccionado;
    }
    private Evento buscarEvento(int id) {
        for (Evento e : eventos) if (e.getIdEvento() == id) return e;
        return null;
    }
    public List<Evento> getEventos() { return Collections.unmodifiableList(eventos); }
}
