package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * La clase actividad contiene datos básicos como nombre, descripción, precio y duración.
 * Además de listas limitadas de recursos y comentarios.
 */
public class Actividad {

    /**
     * Constante que indica que la operación se ha realizado correcta
     */
    public static final int EXITO = 0;
    /**
     * Constante que indica que el valor introducido no es válido (nulo o vacío)
     */
    public static final int ERROR_VALOR_INVALIDO = 1;
    /**
     * Constante que indica que se ha alcanzado el límite máximo de recursos
     */
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    /**
     * Código que indica que se ha alcanzado el límite máximo de comentarios
     */
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;

    /** Nombre de la actividad*/
    private String nombre;
    /** Descripción de la actividad*/
    private String descripcion;
    /**
     * Precio en euros
     */
    private double precio;
    /**
     * Duración en minutos
     */
    private int duracionMinutos;
    /**
     * Recursos asociados
     */
    private String[] recursos;
    /**
     * Comentarios asociados
     */
    private String[] comentarios;
    /**
     * Número actual de recursos registrados
     */
    private int numRecursos;
    /**
     * Numero actual de comentarios registrados
     */
    private int numComentarios;


    /**
     * Es el constructor.
     * Crea una nueva actividad con los límites indicados.
     * @param nombre nombre de la actividad
     * @param maxRecursos numero máximo de recursos asociados
     * @param maxComentarios numero máximo de comentarios asociados
     * @author Kai Wei Jiang
     */
    public Actividad(String nombre, int maxRecursos, int maxComentarios) {
        this.nombre = nombre;
        this.recursos = new String[maxRecursos];
        this.comentarios = new String[maxComentarios];
        this.numRecursos = 0;
        this.numComentarios = 0;
    }

    /**
     * @return nombre de la actividad
     * @author Paloma Martínez-Tizón
     */
    public String getNombre() {
        return nombre; }

    /**
     * @return descripcion de la actividad
     * @author Paloma Martínez-Tizón
     */
    public String getDescripcion() {
        return descripcion; }

    /**
     * @param descripcion nueva descripcion
     * @author Paloma Martínez-Tizón
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; }

    /**
     * @return precio de la actividad
     * @author Paloma Martínez-Tizón
     */
    public double getPrecio() {
        return precio; }

    /**
     * @param precio nuevo precio en euros
     * @author Paloma Martínez-Tizón
     */
    public void setPrecio(double precio) {
        this.precio = precio; }

    /**
     * @return duracion en minutos
     * @author Paloma Martínez-Tizón
     */
    public int getDuracionMinutos() {
        return duracionMinutos; }

    /**
     * @param duracionMinutos nueva duracion en minutos
     * @author Paloma Martínez-Tizón
     */
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos; }

    /**
     * @return numero actual de recursos registrados
     * @author Paloma Martínez-Tizón
     */
    public int getMaxRecursos() {
        return recursos.length; }

    /**
     * @return numero actual de comentarios registrados
     * @author Paloma Martínez-Tizón
     */
    public int getMaxComentarios() {
        return comentarios.length; }

    /**
     * Añade un nuevo recurso si hay espacio disponible
     * @param recurso texto del recurso
     * @return código del resultado (0=válido, 1=inválido, 2= lleno)
     * @author Paloma Martínez-Tizón
     * @author Kai Wei Jiang
     */
    public int agregarRecurso(String recurso) {
        if (recurso == null || recurso.trim().isEmpty()) {
            return ERROR_VALOR_INVALIDO;
        }
        if (recursosCompletos()) {
            return ERROR_RECURSOS_COMPLETOS;
        }
        recursos[numRecursos] = recurso;
        numRecursos++;
        return EXITO;
    }

    /**
     * Añade un nuevo comentario si hay espacio disponible
     * @param comentario texto del comentario
     * @return código de resultado (0=válido, 1=inválido, 2= lleno)
     * @author Paloma Martínez-Tizón
     * @author Kai Wei Jiang
     */
    public int agregarComentario(String comentario) {
        if (comentario == null || comentario.trim().isEmpty()) {
            return ERROR_VALOR_INVALIDO;
        }
        if (comentariosCompletos()) {
            return ERROR_COMENTARIOS_COMPLETOS;
        }
        comentarios[numComentarios] = comentario;
        numComentarios++;
        return EXITO;
    }

    /**
     * @return valor de los comentarios
     * @author Paloma Martínez-Tizón
     */
    public String[] getComentarios() {
        return comentarios; }

    /**
     * @return cantidad de recursos de la actividad
     * @author Paloma Martínez-Tizón
     */
    public int getNumRecursos() {
        return numRecursos; }

    /**
     * @return cantida de comentarios de la actividad
     * @author Paloma Martínez-Tizón
     */
    public int getNumComentarios() {
        return numComentarios; }

    /**
     * @return valor de recursos
     */
    public String[] getRecursos() {
        return recursos; }

    /**
     * @return boleano que define si la cantidad de recursos es maxima
     */
    public boolean recursosCompletos() {
        return numRecursos >= recursos.length;
    }

    /**
     * @return
     */
    public boolean comentariosCompletos() {
        return numComentarios >= comentarios.length;
    }

    /**
     * Devuelve la información completa de la actividad en formato
     * legible
     *
     * @return texto con todos los datos introducidos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String precioFmt = Utilidades.formatearPrecio(precio);
        String duracionFmt = Utilidades.formatearDuracion(duracionMinutos);
        sb.append("Actividad: ").append(nombre).append("\n");
        sb.append("Descripción: ").append(descripcion).append("\n");
        sb.append("Precio: ").append(precioFmt).append("\n");
        sb.append("Duración: ").append(duracionFmt).append("\n");
        sb.append("Recursos:\n");
        for (int i = 0; i < numRecursos; i++) {
            sb.append("- ").append(recursos[i]).append("\n");
        }
        sb.append("Comentarios:\n");
        for (int i = 0; i < numComentarios; i++) {
            sb.append(i + 1).append(". ").append(comentarios[i]).append("\n");
        }
        return sb.toString();
    }

    /**
     * Devuelve una versión compacta de la informacion de la actividad
     * apta para ser alamacenada en un fichero de texto
     *
     * @return texto en formato compacto
     */
    public String toRawString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append("\n");
        sb.append(descripcion).append("\n");
        sb.append(precio).append("\n");
        sb.append(duracionMinutos).append("\n");
        for (int i = 0; i < numRecursos; i++) {
            sb.append(recursos[i]).append("\n");
        }
        sb.append("COMENTARIOS").append("\n");
        for (int i = 0; i < numComentarios; i++) {
            sb.append(comentarios[i]).append("\n");
        }

        sb.append("-----").append("\n");

        return sb.toString();
    }

    /**
     * Construye una actividad leyendo su información desde un flujo
     * de texto
     *
     * @param reader fuente de datos
     * @param maxRecursos numero maximo de recursos qwue se cargarán
     * @param maxComentarios numero maximo de comentarios que se cargaran
     * @return
     * @throws IOException si hay error de lectura
     */
    public static Actividad fromBufferedReader(BufferedReader reader, int maxRecursos, int maxComentarios) throws IOException {
        String nombre = reader.readLine();
        while (nombre != null && nombre.trim().isEmpty()) {
            nombre = reader.readLine();
        }
        if (nombre == null) return null;
        String descripcion = reader.readLine();
        double precio = 0.0;
        int duracion = 0;
        try {
            String lineaPrecio = reader.readLine();
            if (lineaPrecio != null) precio = Double.parseDouble(lineaPrecio.trim());
            String lineaDuracion = reader.readLine();
            if (lineaDuracion != null) duracion = Integer.parseInt(lineaDuracion.trim());
        } catch (NumberFormatException | NullPointerException e) {
        }
        Actividad actividad = new Actividad(nombre, maxRecursos, maxComentarios);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracion);
        String linea;
        boolean leyendoComentarios = false;
        boolean seguir = false;
        while ((linea = reader.readLine()) != null && !seguir) {
            String lineaLimpia = linea.trim();
            if (lineaLimpia.equals("-----")) {
                seguir = true;}
            else {
                if (lineaLimpia.equals("COMENTARIOS")) {
                    leyendoComentarios = true;
                }else if (!lineaLimpia.isEmpty()){
                    if (leyendoComentarios) {
                        actividad.agregarComentario(linea);
                    } else {
                        actividad.agregarRecurso(linea);
                    }
                }
            }
        }
        return actividad;
    }
}