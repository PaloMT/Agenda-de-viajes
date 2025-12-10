package es.upm;
import java.io.*;

public class CatalogoActividades {
    private Actividad[] actividades;
    private int maxActividades;
    private int numActividades;

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    public CatalogoActividades(int maxActividades) {
        this.maxActividades = maxActividades;
        this.actividades = new Actividad[maxActividades];
        this.numActividades = 0;
    }

    public boolean actividadesCompletas() {
        boolean verificar = false;
        if (this.numActividades == this.maxActividades) verificar = true;
        return verificar;
    }

    public int getNumActividades() {
        return numActividades;}

    public int agregarActividad(Actividad actividad) {
        int revision = EXITO;
        if (actividad == null) revision = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) revision = ERROR_DEMASIADOS;
        else {
            actividades[numActividades] = actividad;
            numActividades++;}
        return revision;
    }

    public boolean eliminarActividad(Actividad seleccionada) {
        boolean eliminado = false;
        for (int i = 0; i < numActividades; i++) {
            if (actividades[i] == seleccionada) {
                for (int j = i; j < numActividades - 1; j++) {
                    actividades[j] = actividades[j + 1];
                }
                actividades[numActividades - 1] = null;
                numActividades--;
                eliminado = true;}}
        return eliminado;
    }

    public Actividad[] buscarActividadPorNombre(String texto) {
        if (texto == null) return new Actividad[0];
        String busqueda = texto.toLowerCase();
        Actividad[] aux = new Actividad[this.numActividades];
        int contador = 0;
        for (int i = 0; i < this.numActividades; i++) {
            if (this.actividades[i] != null && this.actividades[i].getNombre().toLowerCase().contains(busqueda)) {
                aux[contador] = this.actividades[i];
                contador++;
            }
        }
        if (contador == 0) {return new Actividad[0];}
        Actividad[] resultado = new Actividad[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = aux[i];
        }
        return resultado;
    }

    public void guardarActividades(String nombreArchivo) throws IOException {
        // Guarda todas las actividades en un archivo de texto usando su representación compacta
        PrintWriter in = null;
        try {
            in = new PrintWriter(new FileWriter(nombreArchivo));
            for (int i = 0; i<this.numActividades; i++){
                if(this.actividades[i] != null){
                    String actividadesGuardadas = actividades[i].toRawString();
                    in.println(actividadesGuardadas);
                }
            }
        } catch (IOException ex){
            System.out.println("Error al guardar el archivo: " + ex);
        }
        finally {
            if (in != null){
                in.close();
            }
        }
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        BufferedReader reader = null;
        boolean catalogoLleno = false;
        try {
            reader = new BufferedReader(new FileReader(nombreArchivo));
            while (!catalogoLleno) {
                Actividad nuevaActividad = Actividad.fromBufferedReader(reader, maxRecursos, maxComentarios);
                if (nuevaActividad == null) {
                    catalogoLleno = true;
                }
                int resultado = this.agregarActividad(nuevaActividad);
                if (resultado == ERROR_DEMASIADOS) {
                    catalogoLleno = true;}
            }
        }catch (IOException ex){
            System.out.println("Error: " + ex);
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}