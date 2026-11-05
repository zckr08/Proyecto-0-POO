package logic;

import java.io.Serializable;

public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descripcion;

    public Servicio(String descripcion) throws Exception {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new Exception("La descripción del servicio no puede estar vacía.");
        }
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override 
    public String toString() {
        return descripcion;
    }
}
