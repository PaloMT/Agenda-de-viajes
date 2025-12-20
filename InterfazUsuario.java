package es.upm;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Clase que se encarga de la comunicación con el usuario por consola.
 * Muestra menu, recibe entradas y gestiona operaciones sobre el catálogo y el viaje.
 * @author Kai wei Jiang Xu
 */
public class InterfazUsuario{
    private CatalogoActividades catalogo;
    private Viaje viaje;
    private int maxRecursosPorActividad;
    private int maxComentariosPorActividad;

    /**
     * Crea la interfaz de usuario a partir de un catálogo y un viaje
     * @param catalogo catalogo de actividades
     * @param viaje viaje asociado
     * @param maxRecursosPorActividad máximo de recursos por actividad
     * @param maxComentariosPorActividad máximo de comentarios por actividad
     */
    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursosPorActividad, int maxComentariosPorActividad){
        this.catalogo = catalogo;
        this.viaje = viaje;
        this.maxRecursosPorActividad = maxRecursosPorActividad;
        this.maxComentariosPorActividad = maxComentariosPorActividad;
    }

    /**
     * Inicia el programa y muestra el menu principal
     * @param scanner lector para la entrada de usuario
     */
    public void iniciar(Scanner scanner){
        scanner.useLocale(Locale.US);
        menuPrincipal(scanner);
    }

    /**
     * Muestra el menú principal y precesa las opciones seleccionadas
     * @param scanner lector de entrada
     */
    private void menuPrincipal(Scanner scanner){
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Utilidades.leerNumero(scanner, "Elige una opción: ", 1, 7);
            switch (opcion){
                case 1: agregarActividad(scanner); break;
                case 2: consultarActividad(scanner); break;
                case 3: guardarActividades(scanner); break;
                case 4: cargarActividades(scanner); break;
                case 5: planificarViaje(scanner); break;
                case 6: guardarItinerario(scanner); break;
                case 7: salir = true; break;
            }
        }
    }

    /**
     * Muestra en pantalla el menú principal de opciones
     */
    private void mostrarMenu() {
        System.out.println("--- Menú Principal ---");
        System.out.println("1. Agregar Actividad");
        System.out.println("2. Consultar/Editar Actividad");
        System.out.println("3. Guardar Actividades");
        System.out.println("4. Cargar Actividades");
        System.out.println("5. Planificar Viaje");
        System.out.println("6. Guardar Itinerario");
        System.out.println("7. Salir");
    }

    /**
     * Crea una nueva actividad pidiendo los datos al usuario y la añade al catálogo
     * @param scanner lector de entrada
     */
    private void agregarActividad(Scanner scanner) {

        String nombre = Utilidades.leerCadena(scanner, "Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner, "Descripción: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0.0, Double.MAX_VALUE);
        int duracionMin = Utilidades.leerNumero(scanner, "Duración (minutos): ", 1, Integer.MAX_VALUE);

        Actividad actividad = new Actividad(nombre, maxRecursosPorActividad, maxComentariosPorActividad);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracionMin);
        boolean seguirRecursos = false;
        System.out.println("Introduce los recursos (una línea por recurso, escribe 'fin' para terminar):");
        while (!actividad.recursosCompletos()&& !seguirRecursos) {
            String linea = scanner.nextLine();
            if (linea.equals("fin")) {
                seguirRecursos = true;
            }
            int resultado = actividad.agregarRecurso(linea);
            if (resultado == 1) {
                System.out.println("El recurso no puede estar vacío.");
            } else if (resultado == 2) {
                System.out.println("No se pueden añadir más recursos.");
                seguirRecursos = true;
            }
        }
        boolean seguirComentarios = false;
        System.out.println("Introduce los comentarios (una línea por comentario, escribe 'fin' para terminar):");
        while (!actividad.comentariosCompletos()&&!seguirComentarios) {
            String linea = scanner.nextLine();
            if (linea.equals("fin")) {
                seguirComentarios = true;
            }
            int resultado = actividad.agregarComentario(linea);
            if (resultado == 1) {
                System.out.println("El comentario no puede estar vacío.");
            } else if (resultado == 3) {
                System.out.println("No se pueden añadir más comentarios.");
                seguirComentarios = true;
            }
        }
        int agrAct = catalogo.agregarActividad(actividad);
        if (agrAct == CatalogoActividades.EXITO) {
            System.out.println("¡Actividad agregada exitosamente!");
        } else {
            System.out.println("No se pueden añadir más actividades.");
        }
    }

    /**
     * Permite consultar o editar una actividad existente
     * @param scanner instancia de la clase scanner
     */
    private void consultarActividad(Scanner scanner) {
        Actividad seleccionada = buscarActividadPorNombre(scanner);
        if (seleccionada != null) {
            editarActividad(scanner, seleccionada);
        }
    }

    /**
     * Busca actividades cuyo nombre contenga un texto introducido por el usuario
     * Muestra las coincidencias y permite al usuario selecciona una de ellas
     * @param scanner lector de entrada por consola
     * @return actividad seleccionada por el usuario
     */
    private Actividad buscarActividadPorNombre(Scanner scanner) {
        Actividad actividadSeleccionada = null;
        boolean buscando = false;
        while (!buscando) {
            String texto = Utilidades.leerCadena(scanner, "Introduce el texto de la actividad a buscar (-FIN- para volver): ");
            if (texto.equals("-FIN-")) {buscando = true;
            } else {
                Actividad[] actividades = catalogo.buscarActividadPorNombre(texto);
                if (actividades != null && actividades.length > 0) {
                    actividadSeleccionada = seleccionarActividad(scanner, actividades);
                    buscando = true;
                } else {
                    System.out.println("No se han encontrado actividades.");
                }
            }
        }
        return actividadSeleccionada;
    }

    /**
     * Muestra una lista numerada de actividades y permite escoger una por índice
     * @param scanner instancia de la clase scanner
     * @param actividades array de actividades disponibles
     * @return actividad elegida
     */
    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        System.out.println("Actividades encontradas:");
        for (int i = 0; i < actividades.length; i++) {
            if (actividades[i] != null) {
                System.out.println((i + 1) + ". " + actividades[i].getNombre());
            }
        }

        int opcion = Utilidades.leerNumero(scanner, "Elige una actividad: ", 1, actividades.length);
        return actividades[opcion - 1];
    }

    /**
     * Muestra los datos de una actividad y ofrece un pequeño menú de edición:
     * añadir recurso, añadir comentario o eliminar la actividad
     * @param scanner instancia de la clase scanner
     * @param seleccionada actividad sobre la que se va a operar
     */
    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        System.out.println();
        System.out.println(seleccionada.toString()+"\n");
        System.out.println("1. Añadir recurso");
        System.out.println("2. Añadir comentario");
        System.out.println("3. Eliminar actividad");
        System.out.println("4. Volver");
        int opcion = Utilidades.leerNumero(scanner, "Elige una opción: ", 1, 4);
        switch (opcion) {
            case 1:
                System.out.print("Introduce el recurso a añadir: ");
                String recurso = scanner.nextLine();
                int resRecurso = seleccionada.agregarRecurso(recurso);
                if (resRecurso == Actividad.EXITO) {
                    System.out.println("Recurso añadido exitosamente.");
                } else if (resRecurso == Actividad.ERROR_RECURSOS_COMPLETOS) {
                    System.out.println("No se pueden añadir más recursos.");
                } else if (resRecurso == Actividad.ERROR_VALOR_INVALIDO) {
                    System.out.println("El recurso no puede estar vacío.");
                }
                break;

            case 2:
                System.out.print("Introduce el comentario a añadir: ");
                String comentario = scanner.nextLine();
                int resComentario = seleccionada.agregarComentario(comentario);
                if (resComentario == Actividad.EXITO) {
                    System.out.println("Comentario añadido exitosamente.");
                } else if (resComentario == Actividad.ERROR_COMENTARIOS_COMPLETOS) {
                    System.out.println("No se pueden añadir más comentarios.");
                } else if (resComentario == Actividad.ERROR_VALOR_INVALIDO) {
                    System.out.println("El comentario no puede estar vacío.");
                }
                break;

            case 3:
                boolean eliminado = catalogo.eliminarActividad(seleccionada);
                if (eliminado) {
                    System.out.println("Actividad eliminada.");
                }
                break;

            case 4:
                break;
        }
    }

    /**
     * Guarda los datos del catálogo o del viaje en un fichero de texto
     * @param scanner instancia de la clase scanner
     */
    private void guardarActividades(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo donde guardar las actividades: ");
        try {
            catalogo.guardarActividades(nombreArchivo);
            System.out.println("Actividades guardadas en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo ");
        }
    }
    /**
     * Pide la ruta de un fichero y carga desde él las actividades al catálogo
     * @param scanner instancia de la clase scanner
     */
    private void cargarActividades(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo de donde cargar las actividades: ");
        try {
            catalogo.cargarActividades(nombreArchivo, maxRecursosPorActividad, maxComentariosPorActividad);
            System.out.println("Actividades cargadas desde " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo ");
        }
    }
    /**
     * Planifica actividades dentro del viaje, indicando día, hora y actividad
     * @param scanner instancia de la clase scanner
     */
    private void planificarViaje(Scanner scanner) {
        System.out.println("Planificación del viaje:");
        System.out.print(viaje.toString());
        System.out.println();
        int numDias = viaje.getNumDias();
        int diaUsuario = Utilidades.leerNumero(scanner, "Introduce el día del viaje (1-" + numDias + "): ", 1, numDias);
        String horaInicio = Utilidades.leerHora(scanner, "Introduce la hora de inicio (HH:MM): ");
        Actividad seleccionada = buscarActividadPorNombre(scanner);
        if (seleccionada == null) {
            return;
        }
        int diaIndice = diaUsuario - 1;
        int resultado = viaje.agregarActividad(diaIndice, seleccionada, horaInicio);
        if (resultado == Viaje.EXITO) {
            System.out.println("Actividad planificada para el día " + diaUsuario +
                    " a las " + horaInicio);
        } else if (resultado == Viaje.ERROR_DIA_INVALIDO) {
            System.out.println("Día inválido.");
        } else if (resultado == Viaje.ERROR_DIA_COMPLETO) {
            System.out.println("No se pueden agregar más actividades a este día.");
        } else if (resultado == Viaje.ERROR_SOLAPAMIENTO) {
            System.out.println("La actividad se solapa con otra actividad ya planificada.");
        }
    }
    /**
     * Pide al usuario la ruta de un fichero y ordena guardar en él el itinerario del viaje
     * @param scanner instancia de la clase scanner
     */
    private void guardarItinerario(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner,"Archivo donde guardar el itinerario: ");
        try {
            viaje.guardarItinerario(nombreArchivo);
            System.out.println("Itinerario guardado en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo");
        }
    }
}
