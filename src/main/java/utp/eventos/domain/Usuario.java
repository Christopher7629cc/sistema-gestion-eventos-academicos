package utp.eventos.domain;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;

/** Clase base para participantes y organizadores. */
public class Usuario {
    private final int id;
    private String nombre, correo, contrasena, rol;
    private boolean activo;
    private boolean sesionIniciada;

    public Usuario(int id, String nombre, String correo, String contrasena, String rol, boolean activo) {
        this.id = id;
        this.nombre = obligatorio(nombre, "Nombre");
        this.correo = obligatorio(correo, "Correo");
        this.contrasena = codificarContrasena(obligatorio(contrasena, "Contrasena"));
        this.rol = obligatorio(rol, "Rol");
        this.activo = activo;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        sesionIniciada = activo && Objects.equals(this.correo, correo)
            && contrasena != null && verificarContrasena(contrasena, this.contrasena);
        return sesionIniciada;
    }

    public void cerrarSesion() { sesionIniciada = false; }

    public void actualizarPerfil(String nombre, String correo) {
        this.nombre = obligatorio(nombre, "Nombre");
        this.correo = obligatorio(correo, "Correo");
    }

    public boolean tieneAcceso() { return activo && sesionIniciada; }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }

    private static String obligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " obligatorio");
        return valor.trim();
    }

    // Prototipo local: PBKDF2 evita almacenar la contrasena en texto plano.
    private static String codificarContrasena(String original) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt) + ":"
            + Base64.getEncoder().encodeToString(derivar(original, salt));
    }
    private static boolean verificarContrasena(String intento, String almacenado) {
        String[] partes = almacenado.split(":", 2);
        byte[] salt = Base64.getDecoder().decode(partes[0]);
        byte[] esperado = Base64.getDecoder().decode(partes[1]);
        return java.security.MessageDigest.isEqual(esperado, derivar(intento, salt));
    }
    private static byte[] derivar(String clave, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(clave.toCharArray(), salt, 120_000, 256);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("No se pudo proteger la contrasena", e);
        } finally { spec.clearPassword(); }
    }
}
