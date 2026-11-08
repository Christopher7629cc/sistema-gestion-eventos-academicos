package utp.eventos.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Evento {
    private int idEvento;
    private String nombre, descripcion, estado;
    private LocalDate fecha;
    private int cupoMaximo;
    private Espacio espacio;
    private Horario horario;
    private Categoria categoria;
    private final List<Inscripcion> inscripciones = new ArrayList<>();
    private static int siguienteInscripcion = 1;
    private static int siguienteReporte = 1;

    public Evento(int idEvento, String nombre, String descripcion, LocalDate fecha,
                  int cupoMaximo, String estado) {
        if (cupoMaximo < 1) throw new IllegalArgumentException("Cupo debe ser positivo");
        this.idEvento = idEvento;
        this.nombre = obligatorio(nombre, "Nombre");
        this.descripcion = Objects.requireNonNull(descripcion);
        this.fecha = Objects.requireNonNull(fecha);
        this.cupoMaximo = cupoMaximo;
        this.estado = obligatorio(estado, "Estado");
    }

    public boolean validarCupo() {
        return "ABIERTO".equalsIgnoreCase(estado) && contarConfirmadas() < cupoMaximo;
    }
    public Inscripcion registrarInscripcion(Participante participante) {
        Objects.requireNonNull(participante);
        for (Inscripcion i : inscripciones) {
            if (i.getParticipante().getId() == participante.getId() && !i.estaCancelada())
                throw new IllegalStateException("El participante ya esta inscrito");
        }
        if (!validarCupo()) throw new IllegalStateException("No hay cupos o el evento esta cerrado");
        Inscripcion inscripcion = new Inscripcion(siguienteInscripcion++, participante, this);
        inscripciones.add(inscripcion);
        inscripcion.confirmar();
        return inscripcion;
    }

    public void asignarEspacio(Espacio nuevo) {
        Objects.requireNonNull(nuevo);
        if (!nuevo.tieneCapacidad(cupoMaximo)) throw new IllegalArgumentException("Espacio sin capacidad");
        if (nuevo == espacio) return;
        if (!nuevo.validarDisponibilidad()) throw new IllegalStateException("Espacio no disponible");
        if (horario != null) nuevo.reservar(this, horario); // primero validar la nueva reserva
        if (espacio != null) espacio.liberar(this);
        espacio = nuevo;
    }

    public void asignarHorario(Horario nuevo) {
        Objects.requireNonNull(nuevo);
        if (nuevo == horario) return;
        if (!nuevo.getFecha().equals(fecha) || !nuevo.validarDisponibilidad())
            throw new IllegalArgumentException("Horario no disponible o fecha diferente");
        if (espacio != null) espacio.reservar(this, nuevo);
        if (horario != null) horario.desasignarEvento(this);
        nuevo.asignarEvento(this);
        horario = nuevo;
    }

    public Reporte generarReporte() { return new Reporte(siguienteReporte++, "ASISTENCIA", this); }

    /** Usado por Organizador.modificarEvento para aplicar realmente los cambios. */
    void actualizarDatosDesde(Evento nueva) {
        if (nueva.cupoMaximo < contarConfirmadas())
            throw new IllegalArgumentException("El cupo no puede ser menor a las inscripciones activas");
        if (espacio != null && !espacio.tieneCapacidad(nueva.cupoMaximo))
            throw new IllegalArgumentException("El nuevo cupo supera la capacidad del aula");
        if (horario != null && !horario.getFecha().equals(nueva.fecha))
            throw new IllegalArgumentException("Cambie el horario antes de cambiar la fecha");
        nombre = nueva.nombre;
        descripcion = nueva.descripcion;
        fecha = nueva.fecha;
        cupoMaximo = nueva.cupoMaximo;
        estado = nueva.estado;
    }

    public int contarConfirmadas() {
        int total = 0;
        for (Inscripcion i : inscripciones) if (i.estaConfirmada()) total++;
        return total;
    }
    public boolean tieneInscripcionConfirmada(Participante participante) {
        for (Inscripcion i : inscripciones)
            if (i.getParticipante().getId() == participante.getId() && i.estaConfirmada()) return true;
        return false;
    }
    public void setCategoria(Categoria nueva) {
        Objects.requireNonNull(nueva);
        if (categoria == nueva) return;
        if (categoria != null) categoria.quitar(this);
        categoria = nueva;
        nueva.agregar(this);
    }
    private static String obligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " obligatorio");
        return valor.trim();
    }
    public int getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public LocalDate getFecha() { return fecha; }
    public Espacio getEspacio() { return espacio; }
    public Horario getHorario() { return horario; }
    public List<Inscripcion> getInscripciones() { return Collections.unmodifiableList(inscripciones); }
}
