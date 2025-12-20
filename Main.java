package es.upm;

import java.util.Scanner;

/**
 * Clase principal del programa
 * Se encarga de leer los parámetros iniciales, crear los objetos
 * necesarios para la aplicación y lanzar la interacción con el usuario
 *
 */
public class Main {


    /**
     ** Método principal de la aplicacion
     * Los valores se reciben por línea de comandos y permiten configurar
     * Los límites del sistema (número de actividades, días del viaje, recursos y comentarios)
     *
     *  @param args argumentos pasados al ejecutar el programa:
     *   - args[0]: número máximo de recursos por actividad
     *   - args[1]: número máximo de comentarios por actividad
     *   - args[2]: número máximo de actividades en el catálogo
     *   - args[3]: número de días del viaje
     *   - args[4]: número máximo de actividades por día
     *   - args[5]: (opcional) nombre del archivo con actividades iniciales
     */

    public static void main(String[] args) {
        if (args.length < 5 || args.length > 6) {
            System.out.println("Error: Número incorrecto de argumentos.");
            System.out.println("Uso: java Main <maxRecursos> <maxComentarios> <maxActividades> <numDiasViaje> <maxActividadesPorDia> [archivoActividades]");
            return;
        }

        try {
            int maxRecursosPorActividad = Integer.parseInt(args[0]);
            int maxComentariosPorActividad = Integer.parseInt(args[1]);
            int maxActividadesEnCatalogo = Integer.parseInt(args[2]);
            int numDiasViaje = Integer.parseInt(args[3]);
            int maxActividadesPorDia = Integer.parseInt(args[4]);
            String nombreArchivoActividades = null;
            if (args.length == 6) {
                nombreArchivoActividades = args[5];
            }
            CatalogoActividades catalogo = new CatalogoActividades(maxActividadesEnCatalogo);
            Viaje viaje = new Viaje(numDiasViaje, maxActividadesPorDia);
            InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, maxRecursosPorActividad, maxComentariosPorActividad);
            Scanner scanner = new Scanner(System.in);
            if (nombreArchivoActividades != null) {
                System.out.println("Intentando cargar actividades desde " + nombreArchivoActividades + "...");
                try {
                    catalogo.cargarActividades(nombreArchivoActividades, maxRecursosPorActividad, maxComentariosPorActividad);
                    System.out.println("Actividades cargadas exitosamente al inicio.");
                } catch (Exception e) {
                    System.out.println("Error al cargar el archivo de actividades inicial: " + e.getMessage());
                }
            }
            interfaz.iniciar(scanner);
            scanner.close();

        } catch (NumberFormatException e) {
            System.out.println("Error: Los primeros cinco argumentos deben ser números enteros.");
        } catch (Exception e) {
            System.out.println("Se ha producido un error inesperado durante la ejecución: " + e.getMessage());
        }
    }
}
