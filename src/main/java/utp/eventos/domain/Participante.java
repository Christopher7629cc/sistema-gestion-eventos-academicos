package utp.eventos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Participante extends Usuario {
    private String matricula, carrera, telefono;
    private static final List<Evento> CATALOGO = new ArrayList<>();

    public Participante(int id, String nombre, String correo, String contrasena, boolean activo,
                        String matricula, String carrera, String telefono) {
        super(id, nombre, correo, contrasena, "PARTICIPANTE", activo);
        this.matricula = matricula;
        this.carrera = carrera;
        this.telefono = telefono;
    }

    public Inscripcion inscribirse(Evento evento) {
        Objects.requireNonNull(evento, "Evento requerido");
        if (!verificarPerfil()) throw new IllegalStateException("Perfil incompleto o inactivo");
        return evento.registrarInscripcion(this);
    }

    public void cancelarInscripcion(Inscripcion inscripcion) {
        Objects.requireNonNull(inscripcion, "Inscripcion requerida");
        if (inscripcion.getParticipante().getId() != getId())
            throw new IllegalArgumentException("La inscripcion no pertenece a este participante");
        inscripcion.cancelar();
    }

    public List<Evento> consultarEventos() { return Collections.unmodifiableList(CATALOGO); }

    public boolean verificarPerfil() {
        return isActivo() && completo(matricula) && completo(carrera) && completo(telefono);
    }

    private static boolean completo(String valor) { return valor != null && !valor.isBlank(); }
    public static void registrarEventoDisponible(Evento evento) {
        Objects.requireNonNull(evento);
        if (!CATALOGO.contains(evento)) CATALOGO.add(evento);
    }
    public String getMatricula() { return matricula; }
}
