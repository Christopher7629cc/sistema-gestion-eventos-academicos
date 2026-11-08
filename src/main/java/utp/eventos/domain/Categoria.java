package utp.eventos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Categoria {
    private int idCategoria;
    private String nombre, descripcion;
    private final List<Evento> eventos = new ArrayList<>();

    public Categoria(int idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = Objects.requireNonNull(nombre);
        this.descripcion = Objects.requireNonNull(descripcion);
    }
    public void clasificarEvento(Evento evento) {
        Objects.requireNonNull(evento);
        evento.setCategoria(this);
    }
    void agregar(Evento evento) { if (!eventos.contains(evento)) eventos.add(evento); }
    void quitar(Evento evento) { eventos.remove(evento); }
    public List<Evento> obtenerEventos() { return Collections.unmodifiableList(eventos); }
}
