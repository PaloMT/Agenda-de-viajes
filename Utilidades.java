package es.upm;

import java.util.Locale;
import java.util.Scanner;
/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 */
public class Utilidades {

    // =========================================================================
    // Métodos de entrada por teclado
    // =========================================================================

    public static String leerCadena(Scanner teclado, String s) {
        // Muestra un mensaje por pantalla y lee una cadena de texto introducida por el usuario
        System.out.print(s);
        return teclado.nextLine();
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        // Muestra un mensaje y lee un número entero en el rango [minimo, maximo]
        int numero;
        do {
            System.out.print(mensaje);
            numero = teclado.nextInt();
        } while (numero < minimo || numero > maximo);
        return numero;
    }

    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        // Muestra un mensaje y lee un número decimal en el rango [minimo, maximo]
        double numero;
        do {
            System.out.print(mensaje);
            numero = teclado.useLocale(Locale.US).nextDouble();
        } while (numero < minimo || numero > maximo);
        return numero;
    }

    public static String leerHora(Scanner teclado, String mensaje) {
        // Muestra un mensaje y lee una hora en formato "HH:MM"y
        int hora = 0, minutos = 0;
        String horaIntroducida;
        boolean esValida = false;
        do {System.out.print(mensaje);
            horaIntroducida = teclado.nextLine();

            if (horaIntroducida.length() != 5 || horaIntroducida.charAt(2) != ':') {
                System.out.println("Error: Formato incorrecto. Debe ser HH:MM (ej. 09:30).");
            }

            try {
                String[] partes = horaIntroducida.split(":");
                hora = Integer.parseInt(partes[0]);
                minutos = Integer.parseInt(partes[1]);
                if (hora >= 0 && hora <= 23 && minutos >= 0 && minutos <= 59) {
                    esValida = true;
                } else {
                    System.out.println("Error: Hora fuera de rango. Debe ser de 00:00 a 23:59.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: La hora y los minutos deben ser numéricos.");
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("Error: Formato incorrecto");
            }
        } while (!esValida);
        return String.format("%02d:%02d",hora,minutos);
    }

    // =========================================================================
    // Métodos de conversión de formatos
    // =========================================================================

    public static int horaAMinutos(String hora) {
        // Convierte una hora en formato "HH:MM" a minutos desde medianoche
        String[] dato = hora.split(":");
        int horaConvertir = Integer.parseInt(dato[0]);
        int minutos = Integer.parseInt(dato[1]);
        return (horaConvertir*60)+minutos; // @todo MODIFICAR PARA DEVOLVER LOS MINUTOS
    }

    public static String minutosAHora(int minutos) {
        // Convierte minutos desde medianoche a formato "HH:MM"
        int horas = minutos/60;
        int minutosnew = minutos % 60;
        return String.format("%02d:%02d",horas,minutosnew); // @todo MODIFICAR PARA DEVOLVER LA HORA EN FORMATO HH:MM
    }

    public static String formatearDuracion(int duracionMinutos) {
        // Formatea una duración en minutos a formato legible (ej: 90 -> "1h 30min")
        int horas = duracionMinutos/60;
        int minutosnew = duracionMinutos % 60;
        String formato;
        if (horas <= 0){
            formato = minutosnew+"min";
        }
        else if (minutosnew <= 0){
            formato = horas+"h";
        }else {
            formato = horas+"h "+minutosnew+"min";
        }
        return formato; // @todo MODIFICAR PARA DEVOLVER LA DURACIÓN FORMATEADA
    }

    public static String formatearPrecio(double precio) {
        // Formatea un precio a formato legible (ej: 12.50 -> "12.50 €")
        return String.format(Locale.US,"%.2f €", precio); // @todo MODIFICAR PARA DEVOLVER EL PRECIO FORMATEADO
    }

    public static double cadenaAPrecio(String precioStr) {
        // Convierte una cadena con precio (ej: "12.50 €") a double
        String[] datos  = precioStr.split(" ");
        double  precioDbl = Double.parseDouble(datos[0]);
        return precioDbl; // @todo MODIFICAR PARA DEVOLVER EL PRECIO COMO DOUBLE
    }
}
