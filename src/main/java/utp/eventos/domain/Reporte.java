package utp.eventos.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.UncheckedIOException;

public class Reporte {
    private int idReporte;
    private LocalDateTime fechaGeneracion;
    private String tipo, contenido;
    private final Evento evento;
    private final Set<Integer> asistentesRegistrados = new HashSet<>();

    public Reporte(int idReporte, String tipo, Evento evento) {
        this.idReporte = idReporte;
        this.tipo = tipo;
        this.evento = Objects.requireNonNull(evento);
        fechaGeneracion = LocalDateTime.now();
    }

    /** Se marca asistencia solamente a participantes realmente presentes. */
    public void registrarAsistencia(Participante participante) {
        Objects.requireNonNull(participante);
        if (!evento.tieneInscripcionConfirmada(participante))
            throw new IllegalArgumentException("El participante no tiene una inscripcion confirmada");
        asistentesRegistrados.add(participante.getId());
    }
    public String generarReporteAsistencia(Evento evento) {
        if (evento != this.evento) throw new IllegalArgumentException("Reporte de otro evento");
        fechaGeneracion = LocalDateTime.now();
        contenido = "Evento: " + evento.getNombre()
            + System.lineSeparator() + "Inscripciones confirmadas: " + evento.contarConfirmadas()
            + System.lineSeparator() + "Asistentes: " + asistentesRegistrados.size()
            + System.lineSeparator() + "Porcentaje de asistencia: "
            + String.format(java.util.Locale.US, "%.2f%%", calcularPorcentajeAsistencia());
        return contenido;
    }
    public double calcularPorcentajeAsistencia() {
        int inscritos = evento.contarConfirmadas();
        return inscritos == 0 ? 0.0 : (100.0 * asistentesRegistrados.size()) / inscritos;
    }
    public void exportar() {
        generarReporteAsistencia(evento);
        Path destino = Path.of("reporte_evento_" + evento.getIdEvento() + ".txt");
        try { Files.writeString(destino, contenido); }
        catch (IOException e) { throw new UncheckedIOException("No se pudo exportar el reporte", e); }
    }
}
