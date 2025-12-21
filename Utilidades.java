package es.upm;

import java.util.Locale;
import java.util.Scanner;
/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 * @author Sara Paloma Martínez-Tizón García
 */
public class Utilidades {

    // =========================================================================
    // Métodos de entrada por teclado
    // =========================================================================

    /**Metodo que emite un mensaje y guarda una respuesta
     * @param teclado instancia de la clase scanner para leer el teclado
     * @param s mensaje que se lee en la pantalla
     * @return mensaje introducido por el usuario en valor String
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.nextLine();
    }

    /**Metodo que lee solicita un numero entre dos valores y repite el número
     * hasta que este comprendido entre dos ellos
     * @param teclado instancia de la clase scanner para leer el teclado
     * @param mensaje mensaje que se lee en la pantalla
     * @param minimo valor int mínimo que puede tener el numero requerido
     * @param maximo valor int máximo que puede tener el numero requerido
     * @return valor int solicitado
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int numero = -1;
        do{
            try{
            System.out.print(mensaje);
            numero = teclado.nextInt();
            if (numero < minimo || numero > maximo){
                System.out.println("El número debe estar entre "+minimo+" y "+maximo+".");
            }}
            catch (java.util.InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
            } finally {
                teclado.nextLine();
            }
        }while (numero < minimo || numero > maximo);
        return numero;
    }

    /**Método que lee solicita un numero double entre dos valores y repite el numero
     * hasta que este comprendido entre dos ellos
     * @param teclado instancia de la clase scanner para leer el teclado
     * @param mensaje mensaje que se lee en la pantalla
     * @param minimo valor double minimo que puede tener el numero requerido
     * @param maximo valor double maximo que puede tener el numero requerido
     * @return valor double solicitado
     */
    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        double numero = -1;
        do {
            try{
            System.out.print(mensaje);
            numero = teclado.useLocale(Locale.US).nextDouble();
            if (numero < minimo || numero > maximo){
                System.out.println("El número debe estar entre "+minimo+" y "+maximo+".");
            }}
            catch (java.util.InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
            } finally {
                teclado.nextLine();
            }} while (numero < minimo || numero > maximo);
        return numero;
    }

    /**
     * Metodo que te pide una hora en un formato específico ("HH:MM")
     * En caso de que el formato no sea especificado seguirá pidiendo la hora
     * Tiene 2 catch para omitir las respuestas que no sean numericas o que se salga de rango
     * @param teclado instancia de la clase scanner para leer el teclado
     * @param mensaje mensaje que se lee en la pantalla
     * @return Hora en el formato especificado
     */
    public static String leerHora(Scanner teclado, String mensaje) {
        int hora = 0, minutos = 0;
        String horaIntroducida;
        boolean esValida = false;
        do {System.out.print(mensaje);
            horaIntroducida = teclado.nextLine();

            if (horaIntroducida.length() != 5 || horaIntroducida.charAt(2) != ':') {
                System.out.println("Formato incorrecto. Usa el formato HH:MM (por ejemplo, 09:30).");
            }
            try {
                String[] partes = horaIntroducida.split(":");
                hora = Integer.parseInt(partes[0]);
                minutos = Integer.parseInt(partes[1]);
                if (hora >= 0 && hora <= 23 && minutos >= 0 && minutos <= 59) {
                    esValida = true;
                } else if (hora <= 0 || hora >= 23){
                    System.out.println("Las horas deben estar entre 00 y 23.");
                }else if (minutos <= 0 || minutos >= 59){
                    System.out.println("Los minutos deben estar entre 00 y 59.");
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

    /**Convierte una hora como el formato del metodo anterior a minutos
     * @param hora hora en formato HH:MM
     * @return valor en minutos de la hora especificada
     */
    public static int horaAMinutos(String hora) {
        String[] dato = hora.split(":");
        int horaConvertir = Integer.parseInt(dato[0]);
        int minutos = Integer.parseInt(dato[1]);
        return (horaConvertir*60)+minutos;
    }

    /**Convierte una hora en minutos a el formato HH:MM
     * @param minutos valor int en minutos
     * @return miutos convertidos al formato HH:MM
     */
    public static String minutosAHora(int minutos) {
        int horas = minutos/60;
        int minutosnew = minutos % 60;
        return String.format("%02d:%02d",horas,minutosnew);
    }

    /**Dice exactamete cuantas horas y cuantos minutos dice una actividad
     * @param duracionMinutos valor int de la hora en minutos
     * @return cantidad de horas y minutos especificados
     */
    public static String formatearDuracion(int duracionMinutos) {
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
        return formato;
    }

    /**convierte un valor a una string con el precio
     * @param precio numero double del precio
     * @return formato con euros y formateado a US (punto en vez de coma para los decimales)
     */
    public static String formatearPrecio(double precio) {
        return String.format(Locale.US,"%.2f €", precio);
    }

    /**convierte una strig con el precio a un valor numerico (Double)
     * @param precioStr  cadena String con el precio
     * @return valor double del precio
     */
    public static double cadenaAPrecio(String precioStr) {
        String[] datos  = precioStr.split(" ");
        double  precioDbl = Double.parseDouble(datos[0]);
        return precioDbl;
    }
}
