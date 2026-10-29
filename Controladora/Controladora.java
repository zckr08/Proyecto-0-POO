package Controladora;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Logica.*;

public class Controladora implements Serializable {
    private static Controladora instance = null;
    private List<Cita> citas;
    private List<Servicio> servicios;
    private List<Dia> dias;

    private Controladora(){
        citas = new ArrayList<>();
        servicios = new ArrayList<>();
        dias = new ArrayList<>();
    }

    public static Controladora getInstance() {
        if (instance == null) instance = new Controladora();
        return instance;
    }

    

}
