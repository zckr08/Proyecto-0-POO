package Logica;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;
    private String telefono;

    public Cliente(String nombre, String email, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del cliente no puede estar vacío.");
        }
        if (email == null || !email.trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new Exception("El email no es válido.");
        }
        if (telefono == null || !telefono.trim().matches("^[0-9()\\-]+$")) {
            throw new Exception("El teléfono no es válido (solo números, paréntesis y guiones).");
        }
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override 
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
