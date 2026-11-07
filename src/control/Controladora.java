package control;
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
import logic.Cliente;
import logic.Cita;
import logic.Servicio;
import logic.Dia;
import logic.EstadoCita;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.time.format.DateTimeFormatter;


public class Controladora implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Controladora instance = null;
    private List<Cita> citas;
    private List<Servicio> servicios;
    private List<Dia> dias;
    private List<Cliente> listaEspera;
    private Map<String, Cliente> clientes;
    private static final String CORREO_EMISOR = "mmmusecuts@gmail.com";
    private static final String CLAVE_CORREO = "zciryncmvqhwpego";

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

    private Cliente obtenerCliente(Integer posCliente) throws Exception {
        if (posCliente == null || posCliente < 0 || posCliente >= clientes.size()) {
            throw new Exception("La posición del cliente no es válida.");
        }
        return new ArrayList<>(clientes.values()).get(posCliente);
    }

    public List<String> obtenerDatosCliente(Integer posCliente) throws Exception {
        List<String> result = new ArrayList<>();
        Cliente cliente = obtenerCliente(posCliente);
        result.add(cliente.getNombre());
        result.add(cliente.getTelefono());
        result.add(cliente.getEmail());
        return result;
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

    // Indica si la cita sigue pendiente: no está cancelada y su fecha no ha pasado
    private boolean estaProgramada(Cita cita) {
        return cita.getEstado() != EstadoCita.NO_CONFIRMADA
                && !cita.getFechaHora().isBefore(LocalDateTime.now());
    }

    // Indica si hay un horario de atención establecido para el día de la semana de la fecha
    private boolean hayHorarioEnDia(LocalDateTime fechaHora) {
        return dias.size() == 7 && dias.get(fechaHora.getDayOfWeek().getValue() - 1) != null;
    }

    private void validarFechaHora(LocalDateTime fechaHora, Cita citaActual) throws Exception {
        if (fechaHora == null) {
            throw new Exception("La fecha y hora de la cita no puede ser nula.");
        }
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new Exception("La fecha y hora de la cita no puede ser anterior a la fecha actual.");
        }
        if (!hayHorarioEnDia(fechaHora)) {
            throw new Exception("La fecha de la cita está fuera del horario de atención (ese día no hay atención).");
        }
        if (!estaDentroDelHorario(fechaHora)) {
            throw new Exception("La hora de la cita está fuera del horario de atención.");
        }
        for (Cita cita : citas) {
            if (cita != citaActual && cita.getEstado() != EstadoCita.NO_CONFIRMADA
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

    private String armarCalendarioMensual(LocalDate fecha) {
        List<Cita> delMes = new ArrayList<>();
        for (Cita cita : citas) {
            LocalDate fechaCita = cita.getFechaHora().toLocalDate();
            if (fechaCita.getYear() == fecha.getYear() && fechaCita.getMonth() == fecha.getMonth()) {
                delMes.add(cita);
            }
        }
        return armarCalendario("Calendario mensual (" + fecha.getMonthValue() + "/" + fecha.getYear() + ")", delMes);
    }

    private String armarCalendarioSemanal(LocalDate fecha) {
        LocalDate inicio = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate fin = inicio.plusDays(6);
        List<Cita> deLaSemana = new ArrayList<>();
        for (Cita cita : citas) {
            LocalDate fechaCita = cita.getFechaHora().toLocalDate();
            if (!fechaCita.isBefore(inicio) && !fechaCita.isAfter(fin)) {
                deLaSemana.add(cita);
            }
        }
        return armarCalendario("Calendario semanal (" + inicio + " a " + fin + ")", deLaSemana);
    }

    public void crearCliente(String nombreCliente, String emailCliente, String numCliente) throws Exception {
        Cliente cliente = new Cliente(nombreCliente, emailCliente, numCliente);
        String clave = cliente.getEmail().trim().toLowerCase();
        if (clientes.containsKey(clave)) {
            throw new Exception("Ya existe un cliente con ese email.");
        }
        clientes.put(clave, cliente);
    }

    public List<String> listaServicios() {
        List<String> myList = new ArrayList<>();
        for (Servicio s : servicios) {
            myList.add(s.toString());
        }
        return myList;
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
            if (cita.getCliente() == cliente && estaProgramada(cita)) {
                throw new Exception("No se puede borrar el cliente porque tiene citas programadas.");
            }
        }
        if (listaEspera.contains(cliente)) {
            throw new Exception("No se puede borrar el cliente porque se encuentra en la lista de espera.");
        }
        clientes.values().remove(cliente);
    }

    public String consultarCliente(Integer posCliente) throws Exception {
        return obtenerCliente(posCliente).toString();
    }

    public String listarClientes() {
        if (clientes.isEmpty()) {
            return "No hay clientes registrados.";
        }
        StringBuilder resultado = new StringBuilder();
        int i = 0;
        for (Cliente cliente : clientes.values()) {
            resultado.append(i).append(" - ").append(cliente).append("\n");
            i++;
        }
        return resultado.toString();
    }

    public List<String> getListaClientes() {
       List<String> resultado = new ArrayList<>();
       for (Cliente cliente : clientes.values()) {
           resultado.add(cliente.toString());
       }
       return resultado;
   }

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

    public String listarCitas() {
        if (citas.isEmpty()) {
            return "No hay citas registradas.";
        }
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < citas.size(); i++) {
            resultado.append(i).append(" - ").append(citas.get(i)).append("\n");
        }
        return resultado.toString();
    }

    public List<String> getListaCitas() {
        List<String> resultado = new ArrayList<>();
        for (Cita cita : citas) {
            resultado.add(cita.toString());
        }
        return resultado;
    }

    public void confirmarCita(Integer posCita) throws Exception {
        Cita cita = obtenerCita(posCita);
        if (cita.getEstado() == EstadoCita.NO_CONFIRMADA) {
            throw new Exception("No se puede confirmar una cita cancelada.");
        }
        if (cita.getEstado() == EstadoCita.CONFIRMADA) {
            throw new Exception("La cita ya está confirmada.");
        }
        cita.setEstado(EstadoCita.CONFIRMADA);
    }

    public void cancelarCita(Integer posCita) throws Exception {
        Cita cita = obtenerCita(posCita);
        cita.setEstado(EstadoCita.NO_CONFIRMADA);
    }

    public void enviarNotificacion() throws Exception {
        LocalDate manana = LocalDate.now().plusDays(1);
        List<Cita> porConfirmar = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getEstado() == EstadoCita.NO_CONFIRMADA
                    && cita.getFechaHora().toLocalDate().equals(manana)
                    && estaDentroDelHorario(cita.getFechaHora())) {
                porConfirmar.add(cita);
            }
        }
        if (porConfirmar.isEmpty()) {
            throw new Exception("No hay citas sin confirmar programadas para el día siguiente.");
        }
        for (Cita cita : porConfirmar) {
            System.out.println("Notificación enviada a " + cita.getCliente().getEmail()
                    + ": por favor comuníquese con el local para confirmar su cita (" + cita + ").");
        }
    }

    public String verCalendarioMensual() {
        return armarCalendarioMensual(LocalDate.now());
    }

    // Muestra el mes que contiene la fecha indicada
    public String verCalendarioMensual(LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha no puede ser nula.");
        }
        return armarCalendarioMensual(fecha);
    }

    public String verCalendarioSemanal() {
        return armarCalendarioSemanal(LocalDate.now());
    }

    // Muestra la semana (lunes a domingo) que contiene la fecha indicada
    public String verCalendarioSemanal(LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha no puede ser nula.");
        }
        return armarCalendarioSemanal(fecha);
    }

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
            if (cita.getServicio() == servicio && estaProgramada(cita)) {
                throw new Exception("No se puede borrar el servicio porque está siendo utilizado en una cita programada.");
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

    public String listarServicios() {
        if (servicios.isEmpty()) {
            return "No hay servicios registrados.";
        }
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < servicios.size(); i++) {
            resultado.append(i).append(" - ").append(servicios.get(i)).append("\n");
        }
        return resultado.toString();
    }

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

    public void guardarDatos(String rutaArchivo) throws Exception {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            throw new Exception("La ruta del archivo no es válida.");
        }

        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(rutaArchivo))) {

            salida.writeObject(this);

        } catch (IOException e) {
            throw new Exception("No se pudieron guardar los datos.", e);
        }
    }
    public boolean cargarDatos(String rutaArchivo) throws Exception {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            throw new Exception("La ruta del archivo no es válida.");
        }

        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            return false;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(archivo))) {

            Controladora datos = (Controladora) entrada.readObject();

            if (datos == null || datos.citas == null
                    || datos.servicios == null || datos.dias == null
                    || datos.listaEspera == null || datos.clientes == null) {
                throw new Exception("El archivo contiene datos incompletos.");
            }

            citas = datos.citas;
            servicios = datos.servicios;
            dias = datos.dias;
            listaEspera = datos.listaEspera;
            clientes = datos.clientes;

            return true;

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new Exception("No se pudieron cargar los datos.", e);
        }
    }

    public void enviarCorreo(String destinatario, String asunto, String contenido) throws Exception {

        Properties propiedades = new Properties();

        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CORREO_EMISOR, CLAVE_CORREO);
            }
        });

        try {
            MimeMessage correo = new MimeMessage(sesion);

            correo.setFrom(new InternetAddress(CORREO_EMISOR));
            correo.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            correo.setSubject(asunto, "UTF-8");
            correo.setText(contenido, "UTF-8");

            Transport.send(correo);

        } catch (MessagingException e) {
            throw new Exception("No se pudo enviar el correo electrónico.", e);
        }
    }

    public void enviarNotificacion(Cita cita) throws Exception {

        if (cita == null) {
            throw new Exception("La cita no puede ser nula.");
        }

        String destinatario = cita.getCliente().getEmail();

        String asunto = "Recordatorio de cita - Barbería";

        String contenido = "Estimado/a " + cita.getCliente().getNombre() + ",\n\n"
                + "Le recordamos que tiene una cita programada para el día "
                + cita.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + ".\n\n"
                + "Por favor, comuníquese con la barbería para confirmar su asistencia.\n\n"
                + "Saludos,\nBarbería";

        enviarCorreo(destinatario, asunto, contenido);
    }

}