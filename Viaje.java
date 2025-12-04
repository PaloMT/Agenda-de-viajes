package es.upm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Viaje {

    public static final int EXITO = 0;
    public static final int ERROR_DIA_INVALIDO = 1;
    public static final int ERROR_DIA_COMPLETO = 2;
    public static final int ERROR_SOLAPAMIENTO = 3;


    private int numDias;
    private int maxActividadesPorDia;

    private Actividad[][] actividades;
    private String[][] horasInicio;
    private int[] numActividadesDia;

    public Viaje(int numDias, int maxActividades) {
        this.numDias = numDias;
        this.maxActividadesPorDia = maxActividades;

        this.actividades = new Actividad[numDias][maxActividadesPorDia];
        this.horasInicio = new String[numDias][maxActividadesPorDia];
        this.numActividadesDia = new int[numDias];

    }

    public int getNumDias() {
        return numDias;
    }

    public int getNumActividadesDia(int dia) {
        if (dia < 0 || dia >= numDias) {
            return 0;
        }
        return numActividadesDia[dia];
    }


    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {

         if (dia < 0 || dia >= numDias) {
            return ERROR_DIA_INVALIDO;
        }

        if (numActividadesDia[dia] >= maxActividadesPorDia) {
            return ERROR_DIA_COMPLETO;
        }

        int inicioNuevo = Utilidades.horaAMinutos(horaInicio);
        int finNuevo = inicioNuevo + actividad.getDuracionMinutos();

        for (int i = 0; i < numActividadesDia[dia]; i++) {
            int inicioExistente = Utilidades.horaAMinutos(horasInicio[dia][i]);
            int finExistente = inicioExistente + actividades[dia][i].getDuracionMinutos();


            boolean compatibles = (finExistente <= inicioNuevo) || (finNuevo <= inicioExistente);
            if (!compatibles) {
                return ERROR_SOLAPAMIENTO;
            }
        }

        int pos = numActividadesDia[dia];
        actividades[dia][pos] = actividad;
        horasInicio[dia][pos] = horaInicio;
        numActividadesDia[dia]++;

        ordenarActividadesDia(dia);

        return EXITO;
    }

    private void ordenarActividadesDia(int dia) {
        int n = numActividadesDia[dia];
        boolean cambiado;

        do {
            cambiado = false;
            for (int i = 0; i < n - 1; i++) {
                int h1 = Utilidades.horaAMinutos(horasInicio[dia][i]);
                int h2 = Utilidades.horaAMinutos(horasInicio[dia][i + 1]);

                if (h1 > h2) {

                    String tmpHora = horasInicio[dia][i];
                    horasInicio[dia][i] = horasInicio[dia][i + 1];
                    horasInicio[dia][i + 1] = tmpHora;

                    Actividad tmpAct = actividades[dia][i];
                    actividades[dia][i] = actividades[dia][i + 1];
                    actividades[dia][i + 1] = tmpAct;

                    cambiado = true;
                }
            }
        } while (cambiado);
    }


    public boolean eliminarActividad(int dia, String horaInicio) {
        if (dia < 0 || dia >= numDias) {
            return false;
        }

        int n = numActividadesDia[dia];
        int indice = -1;

        for (int i = 0; i < n; i++) {
            if (horasInicio[dia][i].equals(horaInicio)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return false;
        }

        for (int i = indice; i < n - 1; i++) {
            actividades[dia][i] = actividades[dia][i + 1];
            horasInicio[dia][i] = horasInicio[dia][i + 1];
        }

        actividades[dia][n - 1] = null;
        horasInicio[dia][n - 1] = null;
        numActividadesDia[dia]--;

        return true;
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        if (dia < 0 || dia >= numDias) {
            return null;
        }

        int n = numActividadesDia[dia];
        Actividad[] copia = new Actividad[n];
        for (int i = 0; i < n; i++) {
            copia[i] = actividades[dia][i];
        }
        return copia;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int totalActividades = 0;
        double precioTotal = 0.0;

        for (int d = 0; d < numDias; d++) {
            sb.append("-------------------------------------------------------------------\n");
            sb.append("Día ").append(d + 1).append("\n");
            sb.append("-------------------------------------------------------------------\n");

            int n = numActividadesDia[d];

            if (n == 0) {
                sb.append("(No hay actividades)\n");
            } else {
                for (int i = 0; i < n; i++) {
                    Actividad act = actividades[d][i];
                    sb.append(horasInicio[d][i])
                            .append(" ")
                            .append(act.getNombre())
                            .append("\n");

                    totalActividades++;
                    precioTotal += act.getPrecio();
                }
            }
            sb.append("\n");
        }

        sb.append("-------------------------------------------------------------------\n");
        sb.append("Resumen:\n");
        sb.append("- Días: ").append(numDias).append("\n");
        sb.append("- Actividades: ").append(totalActividades).append("\n");
        sb.append("- Precio: ").append(Utilidades.formatearPrecio(precioTotal)).append("\n");

        return sb.toString();
    }


    public void guardarItinerario(String nombreArchivo) throws IOException{
        int totalActividades = 0;
        double precioTotal = 0.0;

        try (PrintWriter writer = new PrintWriter(nombreArchivo)){

            for (int d = 0; d < numDias; d++){
                writer.print("Día " + (d + 1) + ": ");

                int n = numActividadesDia[d];

                if (n == 0){
                    writer.print("---");
                } else{
                    for (int i = 0; i < n; i++){
                        Actividad act=actividades[d][i];
                        String hora=horasInicio[d][i];

                        writer.print(hora + " " + act.getNombre()
                                + " (dur " + Utilidades.formatearDuracion(act.getDuracionMinutos())
                                + ", " + Utilidades.formatearPrecio(act.getPrecio()) + ")");

                        if (i < n - 1){
                            writer.print("; ");
                        }

                        totalActividades++;
                        precioTotal+=act.getPrecio();
                    }
                }

                writer.print("\n");
            }

            writer.print("Resumen: Días: "+numDias
                    + "; Actividades: "+totalActividades
                    + "; Precio total: "+Utilidades.formatearPrecio(precioTotal)+"\n");
        }
    }
    }
