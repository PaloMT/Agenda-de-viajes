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
     * @return mensaje itroducido por el usuario en valor String
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.nextLine();
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int numero = -1;
        do{
            try{
            System.out.print(mensaje);
            numero = teclado.nextInt();}
            catch (java.util.InputMismatchException e) {
                System.out.println("Error: Introduce un número entero válido.");
            } finally {
                teclado.nextLine();
            }
        }while (numero < minimo || numero > maximo);
        return numero;
    }

    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        double numero = -1;
        do {
            try{
            System.out.print(mensaje);
            numero = teclado.useLocale(Locale.US).nextDouble();}
            catch (java.util.InputMismatchException e) {
                System.out.println("Error: Introduce un número entero válido.");
            } finally {
                teclado.nextLine();
            }} while (numero < minimo || numero > maximo);
        return numero;
    }

    public static String leerHora(Scanner teclado, String mensaje) {
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
        String[] dato = hora.split(":");
        int horaConvertir = Integer.parseInt(dato[0]);
        int minutos = Integer.parseInt(dato[1]);
        return (horaConvertir*60)+minutos;
    }

    public static String minutosAHora(int minutos) {
        int horas = minutos/60;
        int minutosnew = minutos % 60;
        return String.format("%02d:%02d",horas,minutosnew);
    }

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

    public static String formatearPrecio(double precio) {
        return String.format(Locale.US,"%.2f €", precio);
    }

    public static double cadenaAPrecio(String precioStr) {
        String[] datos  = precioStr.split(" ");
        double  precioDbl = Double.parseDouble(datos[0]);
        return precioDbl;
    }
}
