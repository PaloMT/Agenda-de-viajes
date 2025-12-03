package es.upm;

import java.io.IOException;
import java.util.Scanner;

public class InterfazUsuario {
    private CatalogoActividades catalogo;
    private Viaje viaje;

    private int maxRecursosPorActividad;
    private int maxComentariosPorActividad;

    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje,
                           int maxRecursosPorActividad, int maxComentariosPorActividad) {
        this.catalogo =catalogo;
        this.viaje= viaje;
        this.maxRecursosPorActividad = maxRecursosPorActividad;
        this.maxComentariosPorActividad=maxComentariosPorActividad;
    }

    /**
     * Método para arrancar el menú principal
     */
    public void iniciar(Scanner scanner) {
        menuPrincipal(scanner);
    }

    /**
     * Bucle principal del programa
     */
    private void menuPrincipal(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("--- Menú Principal ---");
            System.out.println("1. Agregar Actividad");
            System.out.println("2. Consultar/Editar Actividad");
            System.out.println("3. Guardar Actividades");
            System.out.println("4. Cargar Actividades");
            System.out.println("5. Planificar Viaje");
            System.out.println("6. Guardar Itinerario");
            System.out.println("7. Salir");

            int opcion = Utilidades.leerNumero(scanner, "Elige una opción: ", 1, 7);

            switch (opcion) {
                case 1:
                    agregarActividad(scanner);
                    break;
                case 2:
                    consultarActividad(scanner);
                    break;
                case 3:
                    guardarActividades(scanner);
                    break;
                case 4:
                    cargarActividades(scanner);
                    break;
                case 5:
                    planificarViaje(scanner);
                    break;
                case 6:
                    guardarItinerario(scanner);
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    break;
            }
            System.out.println();
        }
    }

    /* --- GESTIÓN DE ACTIVIDADES --- */

    private void agregarActividad(Scanner scanner) {
        // Datos básicos
        String nombre = Utilidades.leerCadena(scanner, "Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner, "Descripción: ");

        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0.0, Double.MAX_VALUE);
        int duracionMin = Utilidades.leerNumero(scanner, "Duración (minutos): ", 1, Integer.MAX_VALUE);

        // CORRECCIÓN: Quitamos los tipos de datos (String, double) dentro del new
        Actividad actividad = new Actividad(nombre,maxRecursosPorActividad, maxComentariosPorActividad);

        System.out.println("Introduce los recursos (una línea por recurso, escribe 'fin' para terminar):");
        while (!actividad.recursosCompletos()) {
            String linea = scanner.nextLine();
            if (linea.equals("fin")) {
                break;
            }
            int resultado = actividad.agregarRecurso(linea);
            if (resultado == Actividad.ERROR_VALOR_INVALIDO) {
                System.out.println("El recurso no puede estar vacío.");
            } else if (resultado == Actividad.ERROR_RECURSOS_COMPLETOS) {
                System.out.println("No se pueden añadir más recursos.");
                break;
            }
        }

        // Comentarios
        System.out.println("Introduce los comentarios (una línea por comentario, escribe 'fin' para terminar):");
        while (!actividad.comentariosCompletos()) {
            String linea = scanner.nextLine();
            if (linea.equals("fin")) {
                break;
            }
            int resultado = actividad.agregarComentario(linea);
            if (resultado == Actividad.ERROR_VALOR_INVALIDO) {
                System.out.println("El comentario no puede estar vacío.");
            } else if (resultado == Actividad.ERROR_COMENTARIOS_COMPLETOS) {
                System.out.println("No se pueden añadir más comentarios.");
                break;
            }
        }

        // Añadir al catálogo
        int resCat = catalogo.agregarActividad(actividad);
        if (resCat == CatalogoActividades.EXITO) {
            System.out.println("¡Actividad agregada exitosamente!");
        } else {
            System.out.println("No se pueden añadir más actividades.");
        }
    }

    private void consultarActividad(Scanner scanner) {
        Actividad seleccionada = buscarActividadPorNombre(scanner);
        if (seleccionada != null) {
            editarActividad(scanner, seleccionada);
        }
    }

    private Actividad buscarActividadPorNombre(Scanner scanner) {
        while (true) {
            System.out.print("Introduce el texto de la actividad a buscar (-FIN- para volver): ");
            String texto = scanner.nextLine();

            if (texto.equals("-FIN-")) {
                return null;
            }

            Actividad[] actividades = catalogo.buscarActividadPorNombre(texto);

            if (actividades == null || actividades.length == 0) {
                System.out.println("No se han encontrado actividades.");
                continue;
            }
            return seleccionarActividad(scanner, actividades);
        }
    }

    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        System.out.println("Actividades encontradas:");
        for (int i = 0; i < actividades.length; i++) {
            if(actividades[i] != null) { // Pequeña seguridad por si el array tiene nulos
                System.out.println((i + 1) + ". " + actividades[i].getNombre());
            }
        }

        int opcion = Utilidades.leerNumero(scanner, "Elige una actividad: ", 1, actividades.length);
        return actividades[opcion - 1];
    }

    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println(seleccionada.toString());
            System.out.println();
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
                    volver = true;
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    break;
            }
        }
    }

    /* --- PERSISTENCIA DE ACTIVIDADES (GUARDAR/CARGAR) --- */

    private void guardarActividades(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo donde guardar las actividades: ");
        try {
            // Asumo que tu clase CatalogoActividades tiene este método
            catalogo.guardarActividades(nombreArchivo);
            System.out.println("Actividades guardadas en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private void cargarActividades(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo desde donde cargar: ");
        try {
            // Asumo que tu clase CatalogoActividades tiene este método
            catalogo.cargarActividades(nombreArchivo,  maxRecursosPorActividad, maxComentariosPorActividad);
            System.out.println("Actividades cargadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    /* --- PLANIFICACIÓN DE VIAJE --- */

    private void planificarViaje(Scanner scanner) {
        System.out.println("Planificación del viaje:");
        System.out.print(viaje.toString());
        System.out.println();

        int numDias = viaje.getNumDias();

        int diaUsuario = Utilidades.leerNumero(scanner,
                "Introduce el día del viaje (" + 1 + "-" + numDias + "): ",
                1, numDias);

        String horaInicio = Utilidades.leerHora(scanner,
                "Introduce la hora de inicio (HH:MM): ");

        // Reutilizamos buscarActividad para obtener la actividad que queremos agendar
        Actividad seleccionada = buscarActividadPorNombre(scanner);
        if (seleccionada == null) {
            return;
        }

        int diaIndice = diaUsuario - 1; // Viaje trabaja con 0..numDias-1
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

    private void guardarItinerario(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo donde guardar el itinerario: ");
        try {
            viaje.guardarItinerario(nombreArchivo);
            System.out.println("Itinerario guardado en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

} // ESTA ES LA LLAVE FINAL IMPORTANTE