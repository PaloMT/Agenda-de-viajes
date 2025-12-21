package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * La clase actividad contiene datos básicos como nombre, descripción, precio y duración.
 * Además de listas limitadas de recursos y comentarios.
 * @author Sara Paloma Martínez-Tizón García, BW0100
 * @author Kai Wei Jiang Xu, BW0067
 */
public class Actividad {
    /**
     * Valor de exito del proceso (0)
     */
    public static final int EXITO = 0;
    /**
     * Valor de error en el proceso por ser un día inválido (1)
     */
    public static final int ERROR_VALOR_INVALIDO = 1;
    /**
     *Valor de error si los recursos están completos (2)
     */
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    /**
     *Valor de error si los comentarios están completos (3)
     */
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
    private String[] recursos;
    private String[] comentarios;
    private int numRecursos;
    private int numComentarios;


    /**
     * Es el constructor. Crea una nueva actividad con los límites indicados.
     * @param nombre nombre de la actividad
     * @param maxRecursos numero máximo de recursos asociados
     * @param maxComentarios numero máximo de comentarios asociados
     */
    public Actividad(String nombre, int maxRecursos, int maxComentarios) {
        this.nombre = nombre;
        this.recursos = new String[maxRecursos];
        this.comentarios = new String[maxComentarios];
        this.numRecursos = 0;
        this.numComentarios = 0;
    }

    /**Devuelve el atributo nombre
     * @return atributo nombre de la actividad
     */
    public String getNombre() {
        return nombre; }

    /**Devuelve el atributo descripcion
     * @return devuelve el atributo descripcion de la actividad
     */
    public String getDescripcion() {
        return descripcion; }

    /**
     * define un nuevo valor String al atributo descripcion
     * @param descripcion valor String al que se quiere definir el atributo
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; }

    /**
     * Devuelve el atributo precio
     * @return atributo precio de la actividad
     */
    public double getPrecio() {
        return precio; }

    /**
     * define un nuevo valor int al atributo precio
     * @param precio valor al que se quiere definir el atributo
     */
    public void setPrecio(double precio) {
        this.precio = precio; }

    /**
     * Devuelve el atributo duracionMinutos
     * @return atributo duracioMinutos de la actividad
     */
    public int getDuracionMinutos() {
        return duracionMinutos; }

    /**
     * define un nuevo valor int al atributo duracionMinutos
     * @param duracionMinutos valor int al que se quiere definir el atributo
     */
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos; }

    /**
     * @return logitud del atributo recursos de la actividad (int)
     */
    public int getMaxRecursos() {
        return recursos.length; }

    /**
     * @return logitud del atributo comentarios de la actividad (int)
     */
    public int getMaxComentarios() {
        return comentarios.length; }

    /**
     * Añade un nuevo recurso si hay espacio disponible y devuelve si se ha efectuado el proceso (0),
     * en caso contrario dara un valor de error (1 o 2)
     * @param recurso texto del recurso
     * @return valor, código del resultado (0=válido, 1=inválido, 2= lleno)
     */
    public int agregarRecurso(String recurso) {
        if (recurso == null || recurso.trim().isEmpty()) {
            return   ERROR_VALOR_INVALIDO;
        }
        if (recursosCompletos()) {
            return ERROR_RECURSOS_COMPLETOS;
        }
        recursos[numRecursos] = recurso;
        numRecursos++;
        return EXITO;
    }

    /**
     * Añade un nuevo comentario si hay espacio disponible y proporcionara el valor de exito(0),
     * en caso contrario pasará un valor de error (1 o 2)
     * @param comentario texto del comentario
     * @return código de resultado (0=válido, 1=inválido, 2= lleno)
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

    /**Devuelve la lista completa de comentario
     * @return varios valores String de los comentarios 
     */
    public String[] getComentarios() {
        return comentarios; }

    /**Devuelve la cantidad de recursos que tiene la activida
     * @return cantidad Valor int de numRecursos de el objeto actividad 
     */
    public int getNumRecursos() {
        return numRecursos; }

    /**Devuelve la cantidad numerica de comentarios que tiene la actividad seleccionada
     * @return numComentarios valor int del atributo de la activida
     */
    public int getNumComentarios() {
        return numComentarios; }

    /**Devuelve una lista de todos los recursos que tiene la activida
     * @return recursos lista de valores string recursos que posee el objet
     */
    public String[] getRecursos() {
        return recursos; }

    /**devuelve True en caso que el numero de recursos sa igual o mayor  a la longitud (Cantidad) de recursos disponibles,
    * En caso contrario devolvera False
     * @return boleano que define si la cantidad de recursos es maxima
     */
    public boolean recursosCompletos() {
        return numRecursos >= recursos.length;
    }

    /**devuelve True en caso que el numero de comentarios sa igual o mayor  a la longitud (Cantidad) de comentarios disponibles,
     * En caso contrario devolvera False
     * @return boleano que define si la cantidad de recursos es maxima
     */
    public boolean comentariosCompletos() {
        return numComentarios >= comentarios.length;
    }

    /**
     * Devuelve la información completa de la actividad en formato legible
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
            System.out.println("Formato del precio o de la duración incorrecto");
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