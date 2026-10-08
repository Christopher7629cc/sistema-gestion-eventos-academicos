package utp.eventos.domain;

public class Espacio {
    private int idEspacio;
    private String nombre;
    private String ubicacion;
    private int capacidad;
    private boolean disponible;

    public boolean validarDisponibilidad() { return disponible; }
    public void reservar() { disponible = false; }
    public void liberar() { disponible = true; }
    public boolean tieneCapacidad(int cantidad) { return cantidad <= capacidad; }
}
