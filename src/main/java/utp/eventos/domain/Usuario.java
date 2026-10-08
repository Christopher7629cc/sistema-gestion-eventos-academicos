package utp.eventos.domain;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private boolean activo;

    public boolean iniciarSesion(String correo, String contrasena) { return false; }
    public void cerrarSesion() {}
    public void actualizarPerfil(String nombre, String correo) {}
    public boolean tieneAcceso() { return activo; }
}
