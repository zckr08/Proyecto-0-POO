package Controladora;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import Logica.*;

public class Controladora implements Serializable {
    private static Controladora instance = null;
    private List<Cita> citas;
    private List<Servicio> servicios;
    private List<Dia> dias;
    private List<Cliente> listaEspera;
    private Map<String, Cliente> clientes;

    private Controladora(){
        citas = new ArrayList<>();
        servicios = new ArrayList<>();
        dias = new ArrayList<>();
        listaEspera = new ArrayList<>();
        clientes = new LinkedHashMap<>();
    }

    public static Controladora getInstance() {
        if (instance == null) instance = new Controladora();
        return instance;
    }

    // ---------- Métodos auxiliares (validación de posiciones y referencias) ----------

    private Cliente obtenerCliente(Integer posCliente) throws Exception {
        if (posCliente == null || posCliente < 0 || posCliente >= clientes.size()) {
            throw new Exception("La posición del cliente no es válida.");
        }
        return new ArrayList<>(clientes.values()).get(posCliente);
    }

    private void validarCita(Cita cita) throws Exception {
        if (cita == null || !citas.contains(cita)) {
            throw new Exception("La cita no existe.");
        }
    }

    private void validarServicio(Servicio servicio) throws Exception {
        if (servicio == null) {
            throw new Exception("El servicio no puede ser nulo.");
        }
        if (!servicios.contains(servicio)) {
            throw new Exception("El servicio no está registrado.");
        }
    }

    private void validarNumDia(Integer numDia) throws Exception {
        if (numDia == null || numDia < 1 || numDia > 7) {
            throw new Exception("El número de día debe estar entre 1 (lunes) y 7 (domingo).");
        }
    }

    private void validarFechaHora(LocalDateTime fechaHora, Cita citaActual) throws Exception {
        if (fechaHora == null) {
            throw new Exception("La fecha y hora de la cita no puede ser nula.");
        }
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new Exception("La fecha y hora de la cita no puede ser anterior a la fecha actual.");
        }
        if (!estaDentroDelHorario(fechaHora)) {
            throw new Exception("La fecha y hora de la cita está fuera del horario de atención.");
        }
        for (Cita cita : citas) {
            if (cita != citaActual && cita.getEstado() != EstadoCita.CANCELADA
                    && cita.getFechaHora().equals(fechaHora)) {
                throw new Exception("Ya existe una cita en esa fecha y hora.");
            }
        }
    }

    private String armarCalendario(String titulo, List<Cita> lista) {
        lista.sort(Comparator.comparing(Cita::getFechaHora));
        StringBuilder resultado = new StringBuilder(titulo + "\n");
        if (lista.isEmpty()) {
            resultado.append("No hay citas en este periodo.");
        } else {
            for (Cita cita : lista) {
                resultado.append(cita).append("\n");
            }
        }
        return resultado.toString();
    }

    // ---------- Clientes ----------

    public void crearCliente(String nombreCliente, String emailCliente, String numCliente) throws Exception {
        Cliente cliente = new Cliente(nombreCliente, emailCliente, numCliente);
        String clave = cliente.getEmail().trim().toLowerCase();
        if (clientes.containsKey(clave)) {
            throw new Exception("Ya existe un cliente con ese email.");
        }
        clientes.put(clave, cliente);
    }

    public void modificarCliente(Integer posCliente, String nombreCliente, String emailCliente, String numCliente) throws Exception {
        Cliente cliente = obtenerCliente(posCliente);
        // Los setters de Cliente no validan, se usa el constructor para reutilizar sus validaciones
        Cliente validado = new Cliente(nombreCliente, emailCliente, numCliente);
        String claveNueva = validado.getEmail().trim().toLowerCase();
        Cliente existente = clientes.get(claveNueva);
        if (existente != null && existente != cliente) {
            throw new Exception("Ya existe otro cliente con ese email.");
        }
        cliente.setNombre(nombreCliente);
        cliente.setEmail(emailCliente);
        cliente.setTelefono(numCliente);
        // Se reconstruye el mapa para actualizar la clave sin cambiar el orden de las posiciones
        Map<String, Cliente> nuevoMapa = new LinkedHashMap<>();
        for (Map.Entry<String, Cliente> entrada : clientes.entrySet()) {
            if (entrada.getValue() == cliente) {
                nuevoMapa.put(claveNueva, cliente);
            } else {
                nuevoMapa.put(entrada.getKey(), entrada.getValue());
            }
        }
        clientes = nuevoMapa;
    }

    public void borrarCliente(Integer posCliente) throws Exception {
        Cliente cliente = obtenerCliente(posCliente);
        for (Cita cita : citas) {
            if (cita.getCliente() == cliente) {
                throw new Exception("No se puede borrar el cliente porque tiene citas asociadas.");
            }
        }
        listaEspera.remove(cliente);
        clientes.values().remove(cliente);
    }

    public String consultarCliente(Integer posCliente) throws Exception {
        return obtenerCliente(posCliente).toString();
    }

    // ---------- Citas ----------

    public void crearCita(LocalDateTime fechaHora, Integer posCliente, Servicio servicio) throws Exception {
        Cliente cliente = obtenerCliente(posCliente);
        validarServicio(servicio);
        validarFechaHora(fechaHora, null);
        citas.add(new Cita(fechaHora, cliente, servicio, EstadoCita.NO_CONFIRMADA));
    }

    public void modificarCita(Cita cita, LocalDateTime fechaHora, Servicio servicio) throws Exception {
        validarCita(cita);
        validarServicio(servicio);
        validarFechaHora(fechaHora, cita);
        cita.setFechaHora(fechaHora);
        cita.setServicio(servicio);
    }

    public void modificarCita(Cita cita, Servicio servicio) throws Exception {
        validarCita(cita);
        validarServicio(servicio);
        cita.setServicio(servicio);
    }

    public void modificarCita(Cita cita, LocalDateTime fechaHora) throws Exception {
        validarCita(cita);
        validarFechaHora(fechaHora, cita);
        cita.setFechaHora(fechaHora);
    }

    public void borrarCita(Integer posCita) throws Exception {
        Cita cita = obtenerCita(posCita);
        citas.remove(cita);
    }

    public String consultarCita(Integer posCita) throws Exception {
        return obtenerCita(posCita).toString();
    }

    public Cita obtenerCita(Integer posCita) throws Exception {
        if (posCita == null || posCita < 0 || posCita >= citas.size()) {
            throw new Exception("La posición de la cita no es válida.");
        }
        return citas.get(posCita);
    }

    public void confirmarCita(Integer posCita) throws Exception {
        Cita cita = obtenerCita(posCita);
        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new Exception("No se puede confirmar una cita cancelada.");
        }
        cita.setEstado(EstadoCita.CONFIRMADA);
    }

    public void cancelarCita(Integer posCita) throws Exception {
        Cita cita = obtenerCita(posCita);
        cita.setEstado(EstadoCita.CANCELADA);
    }

    public void enviarNotificacion() {
        LocalDateTime ahora = LocalDateTime.now();
        for (Cita cita : citas) {
            if (cita.getEstado() == EstadoCita.NO_CONFIRMADA && !cita.getFechaHora().isBefore(ahora)) {
                System.out.println("Notificación enviada a " + cita.getCliente().getEmail()
                        + ": por favor confirme su cita (" + cita + ").");
            }
        }
    }

    public String verCalendarioMensual() {
        LocalDate hoy = LocalDate.now();
        List<Cita> delMes = new ArrayList<>();
        for (Cita cita : citas) {
            LocalDate fecha = cita.getFechaHora().toLocalDate();
            if (fecha.getYear() == hoy.getYear() && fecha.getMonth() == hoy.getMonth()) {
                delMes.add(cita);
            }
        }
        return armarCalendario("Calendario mensual (" + hoy.getMonthValue() + "/" + hoy.getYear() + ")", delMes);
    }

    public String verCalendarioSemanal() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate fin = inicio.plusDays(6);
        List<Cita> deLaSemana = new ArrayList<>();
        for (Cita cita : citas) {
            LocalDate fecha = cita.getFechaHora().toLocalDate();
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                deLaSemana.add(cita);
            }
        }
        return armarCalendario("Calendario semanal (" + inicio + " a " + fin + ")", deLaSemana);
    }

    // ---------- Servicios ----------

    public void crearServicio(String descripcion) throws Exception {
        servicios.add(new Servicio(descripcion));
    }

    public void modificarServicio(Integer posServicio, String descripcion) throws Exception {
        Servicio servicio = obtenerServicio(posServicio);
        // El setter de Servicio no valida, se usa el constructor para reutilizar su validación
        new Servicio(descripcion);
        servicio.setDescripcion(descripcion);
    }

    public void borrarServicio(Integer posServicio) throws Exception {
        Servicio servicio = obtenerServicio(posServicio);
        for (Cita cita : citas) {
            if (cita.getServicio() == servicio) {
                throw new Exception("No se puede borrar el servicio porque tiene citas asociadas.");
            }
        }
        servicios.remove(servicio);
    }

    public String consultarServicio(Integer posServicio) throws Exception {
        return obtenerServicio(posServicio).toString();
    }

    public Servicio obtenerServicio(Integer posServicio) throws Exception {
        if (posServicio == null || posServicio < 0 || posServicio >= servicios.size()) {
            throw new Exception("La posición del servicio no es válida.");
        }
        return servicios.get(posServicio);
    }

    // ---------- Lista de espera ----------

    public String listaEspera() {
        if (listaEspera.isEmpty()) {
            return "La lista de espera está vacía.";
        }
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < listaEspera.size(); i++) {
            resultado.append(i).append(" - ").append(listaEspera.get(i)).append("\n");
        }
        return resultado.toString();
    }

    public void agregarAListaEspera(Integer posCliente) throws Exception {
        Cliente cliente = obtenerCliente(posCliente);
        if (listaEspera.contains(cliente)) {
            throw new Exception("El cliente ya está en la lista de espera.");
        }
        listaEspera.add(cliente);
    }

    public void borrarDeListaEspera(Integer posListaEspera) throws Exception {
        if (posListaEspera == null || posListaEspera < 0 || posListaEspera >= listaEspera.size()) {
            throw new Exception("La posición de la lista de espera no es válida.");
        }
        listaEspera.remove(posListaEspera.intValue());
    }

    // ---------- Horario ----------

    public void establecerHorario(Integer numDia, LocalDateTime horaApertura, LocalDateTime horaCierre) throws Exception {
        validarNumDia(numDia);
        Dia dia = new Dia(horaApertura, horaCierre);
        while (dias.size() < 7) {
            dias.add(null);
        }
        dias.set(numDia - 1, dia);
    }

    public String consultarHorario(Integer numDia) throws Exception {
        validarNumDia(numDia);
        if (dias.size() < 7 || dias.get(numDia - 1) == null) {
            return "No hay horario establecido para ese día.";
        }
        return dias.get(numDia - 1).toString();
    }

    public boolean estaDentroDelHorario(LocalDateTime fechaHora) {
        if (fechaHora == null || dias.size() < 7) {
            return false;
        }
        Dia dia = dias.get(fechaHora.getDayOfWeek().getValue() - 1);
        if (dia == null) {
            return false;
        }
        return dia.estaDentroDelHorario(fechaHora.toLocalTime());
    }

}
