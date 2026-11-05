package logic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Dia implements Serializable {
   private static final long serialVersionUID = 1L;

    private LocalDateTime horaApertura;
    private LocalDateTime horaCierre;

    public Dia(LocalDateTime horaApertura, LocalDateTime horaCierre) throws Exception {
        if (horaApertura == null) {
            throw new Exception("La hora de apertura no puede estar vacía.");
        }
        if (horaCierre == null) {
            throw new Exception("La hora de cierre no puede estar vacía.");
        }
        if (!horaApertura.toLocalTime().isBefore(horaCierre.toLocalTime())) {
            throw new Exception("La hora de apertura debe ser anterior a la hora de cierre.");
        }
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    public LocalDateTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalDateTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalDateTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalDateTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public boolean estaDentroDelHorario(LocalTime hora) {
        return !hora.isBefore(horaApertura.toLocalTime())
            && hora.isBefore(horaCierre.toLocalTime());
    }

    @Override
    public String toString() {
        return horaApertura.toLocalTime() + " - " + horaCierre.toLocalTime();
    } 
}
