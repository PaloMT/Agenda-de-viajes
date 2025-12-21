package es.upm;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;


/**
 * Almacena un conjunto de actividades. Clase que permite
 * agregar, buscar y eliminar actividades.
 * @author Sara Paloma Martínez-Tizón García, BW0100
 */
public class CatalogoActividades {
    private Actividad[] actividades;
    private int maxActividades;
    private int numActividades;

    /**
     * Valor de exito del proceso (0)
     */
    public static final int EXITO = 0;
    /**
     * Valor de error si los la actividad es nula (1)
     */
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    /**
     * Valor de error si las actividades son mas del máximo (2)
     */
    public static final int ERROR_DEMASIADOS = 2;

    /**
     * Método constructor para definir atributos
     * @param maxActividades Numero máximo de actividades pertimidas
     */
    public CatalogoActividades(int maxActividades) {
        this.maxActividades = maxActividades;
        this.actividades = new Actividad[maxActividades];
        this.numActividades = 0;
    }

    /**Verifica se si el número de actividades esta completa
     * @return confirma si el número de actividades es igual al máximo de actividades
     */
    public boolean actividadesCompletas() {
        boolean verificar = false;
        if (this.numActividades == this.maxActividades) verificar = true;
        return verificar;
    }
    /**Devuelve el valor de el atributo getNumActividades
     * @return atributo número de actividades
     */
    public int getNumActividades() {
        return numActividades;}

    /**
     * Añade una nueva actividad si hay espacio disponible y devuelve si se ha efectuado el proceso (0),
     * En caso contrario dara un valor de error (1 o 2)
     * @param actividad actividad a agregar
     * @return valor, código del resultado (0=válido, 1=inválido, 2= lleno)
     */
    public int agregarActividad(Actividad actividad) {
        int revision = EXITO;
        if (actividad == null) revision = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) revision = ERROR_DEMASIADOS;
        else {
            actividades[numActividades] = actividad;
            numActividades++;}
        return revision;
    }

    /**
     * Elimina la actividad seleccionada y devuelve si se ha efectuado el proceso (True),
     * En caso contrario dara un valor de error (False)
     * @param seleccionada actividad seleccionada para eliminar
     * @return valor, código del resultado (True o False)
     */
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

    /**Busca todas aquellas actividades que su nombre sea el texto introducido
     * @param texto Texto que hay que buscar en la actividad
     * @return Lista de todas las actividades encontradas por ese nombre
     */
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

    /**Guarda las actividades en un archivo txt
     * @param nombreArchivo nombre para el archivo de guardado
     * @throws IOException En caso de que halla un error al guardar el archivo da un mensaje y no para el codigo en su totalidad
     */
    public void guardarActividades(String nombreArchivo) throws IOException {
       BufferedWriter in = null;
        try {
            in = new BufferedWriter(new FileWriter(nombreArchivo));
            for (int i = 0; i<this.numActividades; i++){
                if(this.actividades[i] != null){
                    String actividadesGuardadas = actividades[i].toRawString();
                    in.write(actividadesGuardadas);
                }
            }
        }
        finally {
            if (in != null){
                in.close();
            }
        }
    }

    /**Carga las actividades desde un archivo txt
     * @param nombreArchivo nombre del archivo desde el que se quiere cargar
     * @param maxRecursos Máximo de recursos permitidos
     * @param maxComentarios Maximo de cometarios permitidos
     * @throws IOException En caso de que halla un error al cargar el archivo da un mensaje y no para el codigo en su totalidad
     */
    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        BufferedReader out = null;
        boolean catalogoLleno = false;
        try {
            out = new BufferedReader(new FileReader(nombreArchivo));
            while (!catalogoLleno) {
                Actividad nuevaActividad = Actividad.fromBufferedReader(out, maxRecursos, maxComentarios);
                if (nuevaActividad == null) {
                    catalogoLleno = true;
                }
                int resultado = this.agregarActividad(nuevaActividad);
                if (resultado == ERROR_DEMASIADOS) {
                    catalogoLleno = true;}
            }
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
    }
}