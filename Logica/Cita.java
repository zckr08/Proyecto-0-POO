package Logica;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cita implements Serializable  {
    private static final long serialVersionUID = 1L;

    private LocalDateTime fechaHora;
    private Cliente cliente;
    private Servicio servicio;
    private EstadoCita estado;

    public Cita(LocalDateTime fechaHora, Cliente cliente, Servicio servicio, EstadoCita estado) throws Exception {
        if (fechaHora == null) {
            throw new Exception("La fecha y hora de la cita no puede ser nula ni anterior a la fecha actual.");
        }
        if (cliente == null) {
            throw new Exception("El cliente no puede ser nulo.");
        }
        if (servicio == null) {
            throw new Exception("El servicio no puede ser nulo.");
        }
        if (estado == null) {
            throw new Exception("El estado de la cita no puede ser nulo.");
        }
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.servicio = servicio;
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaHora.format(formato) + " | " + cliente.getNombre()
                + " | " + servicio.getDescripcion() + " | " + estado;
    }
}
