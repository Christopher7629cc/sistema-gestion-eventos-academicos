package utp.eventos;

import utp.eventos.domain.*;

import java.time.LocalDate;
import java.time.LocalTime;

/** Pruebas demostrativas de las 10 clases del avance 2. */
public class App {
    private static void comprobar(boolean condicion, String escenario) {
        if (!condicion) throw new AssertionError("Fallo: " + escenario);
        System.out.println("OK - " + escenario);
    }
    public static void main(String[] args) {
        Organizador organizador = new Organizador(1, "Ana", "ana@utp.ac.pa", "clave123",
            true, 101, "Facultad de Sistemas");
        comprobar(organizador.iniciarSesion("ana@utp.ac.pa", "clave123"), "Inicio de sesion");
        comprobar(organizador.tieneAcceso(), "Permisos de usuario");

        Espacio aula = new Espacio(1, "Aula 101", "Edificio A", 40, true);
        Categoria categoria = new Categoria(1, "Tecnologia", "Charlas academicas");
        LocalDate fecha = LocalDate.of(2026, 11, 15);
        Evento java = new Evento(1, "Charla Java", "POO", fecha, 2, "ABIERTO");
        organizador.crearEvento(java);
        categoria.clasificarEvento(java);
        organizador.asignarEspacio(aula);
        organizador.asignarHorario(new Horario(1, fecha, LocalTime.of(9, 0), LocalTime.of(11, 0)));
        comprobar(categoria.obtenerEventos().contains(java), "Clasificacion del evento");

        Evento versionNueva = new Evento(1, "Charla Java actualizada", "Programacion POO", fecha, 2, "ABIERTO");
        organizador.modificarEvento(versionNueva);
        comprobar("Charla Java actualizada".equals(java.getNombre()), "Modificacion real del evento");

        Evento otraCharla = new Evento(2, "Redes", "Laboratorio", fecha, 3, "ABIERTO");
        organizador.crearEvento(otraCharla);
        organizador.asignarHorario(new Horario(2, fecha, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        organizador.asignarEspacio(aula); // se permite reutilizar el aula sin solapamiento
        comprobar(otraCharla.getEspacio() == aula, "Espacio libre en otra franja");

        Evento conflicto = new Evento(3, "Seguridad", "Charla", fecha, 2, "ABIERTO");
        organizador.crearEvento(conflicto);
        organizador.asignarHorario(new Horario(3, fecha, LocalTime.of(10, 0), LocalTime.of(11, 30)));
        boolean choqueDetectado = false;
        try { organizador.asignarEspacio(aula); }
        catch (IllegalStateException ex) { choqueDetectado = true; }
        comprobar(choqueDetectado, "Prevencion de cruce de horarios");
        boolean cambioHorarioRechazado = false;
        try { java.getHorario().modificarHorario(LocalTime.of(10, 0), LocalTime.of(12, 0)); }
        catch (IllegalStateException ex) { cambioHorarioRechazado = true; }
        comprobar(cambioHorarioRechazado, "Modificacion de horario sin colisiones");

        Participante participante = new Participante(2, "Luis", "luis@utp.ac.pa", "abc123", true,
            "8-123", "Ciberseguridad", "6000-0000");
        comprobar(participante.consultarEventos().contains(java), "Consulta de eventos");
        Inscripcion inscripcion = participante.inscribirse(java);
        comprobar(inscripcion.estaConfirmada() && inscripcion.getCodigoAcceso() != null,
            "Inscripcion confirmada y codigo de acceso");
        comprobar(inscripcion.getNotificaciones().size() == 1
            && inscripcion.getNotificaciones().get(0).getFechaEnvio() != null, "Notificacion enviada");
        boolean duplicadoRechazado = false;
        try { participante.inscribirse(java); }
        catch (IllegalStateException ex) { duplicadoRechazado = true; }
        comprobar(duplicadoRechazado, "Prevencion de inscripcion duplicada");

        Reporte reporte = java.generarReporte();
        reporte.registrarAsistencia(participante);
        comprobar(reporte.calcularPorcentajeAsistencia() == 100.0, "Asistencia real registrada");
        reporte.exportar();
        System.out.println(reporte.generarReporteAsistencia(java));

        participante.cancelarInscripcion(inscripcion);
        comprobar(inscripcion.estaCancelada() && java.validarCupo(), "Cancelacion y recuperacion de cupo");
        comprobar(inscripcion.getNotificaciones().size() == 2, "Notificacion por cancelacion");
        organizador.cerrarSesion();
        comprobar(!organizador.tieneAcceso(), "Cierre de sesion");
        System.out.println("Todas las pruebas finalizaron correctamente.");
    }
}
