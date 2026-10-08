package utp.eventos.domain;

import java.time.LocalDateTime;

public class Reporte {
    private int idReporte;
    private LocalDateTime fechaGeneracion;
    private String tipo;
    private String contenido;

    public String generarReporteAsistencia(Evento evento) { return ""; }
    public double calcularPorcentajeAsistencia() { return 0.0; }
    public void exportar() {}
}
