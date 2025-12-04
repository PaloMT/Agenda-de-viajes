package es.upm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class Actividad {

    public static final int EXITO = 0;
    public static final int ERROR_VALOR_INVALIDO = 1;
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;

    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
    private String[] recursos;
    private String[] comentarios;
    private int numRecursos;
    private int numComentarios;

    // ---------------------------
    // Constructor
    // ---------------------------
    public Actividad(String nombre, int maxRecursos, int maxComentarios) {
        this.nombre = nombre;
        this.recursos = new String[maxRecursos];
        this.comentarios = new String[maxComentarios];
        this.numRecursos = 0;
        this.numComentarios = 0;
    }

    public String getNombre() {
        return nombre; }

    public String getDescripcion() {
        return descripcion; }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; }

    public double getPrecio() {
        return precio; }

    public void setPrecio(double precio) {
        this.precio = precio; }

    public int getDuracionMinutos() {
        return duracionMinutos; }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos; }

    public int getMaxRecursos() {
        return recursos.length; }

    public int getMaxComentarios() {
        return comentarios.length; }

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

    public String[] getComentarios() {
        return comentarios; }

    public int getNumRecursos() {
        return numRecursos; }

    public int getNumComentarios() {
        return numComentarios; }

    public String[] getRecursos() {
        return recursos; }

    public boolean recursosCompletos() {
        return numRecursos >= recursos.length;
    }

    public boolean comentariosCompletos() {
        return numComentarios >= comentarios.length;
    }

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