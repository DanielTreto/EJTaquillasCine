package cuatrovientos.dam.psp.java.EjTaquillasCine.V1;

import java.util.ArrayList;

public class Cine4V {

    public static void main(String[] args) {
        
        // CONSTANTES
        int NUM_TAQUILLAS = 2;
    	int ASIENTOS = 200;
        long DURACION_TOTAL = 30 * 60 * 1000;

        // Variables de Hilos
        ArrayList<Thread> hilosTaquillas = new ArrayList<>();
        Thread hGenerador = null;
        GeneradorClientes generador = null;

        try {
            System.out.println("--- INICIO CINE 4V ---");
            
            ColaCine cola = new ColaCine("Cola-General", ASIENTOS);
            
            generador = new GeneradorClientes(cola);
            hGenerador = new Thread(generador);
            hGenerador.start();

            // Crear Taquillas 
            for (int i = 1; i <= NUM_TAQUILLAS; i++) {
                Taquilla t = new Taquilla("Taq-" + i, cola);
                Thread hilo = new Thread(t);
                hilosTaquillas.add(hilo);
                hilo.start();
            }
           
            
            // Esperar el tiempo de simulación
            System.out.println("El cine 4V está abierto");
            long tiempoInicio = System.currentTimeMillis();
            Thread.sleep(DURACION_TOTAL);
            
            // Cerramos generador de clientes y taquillas
            System.out.println("\n--- FIN DEL TIEMPO: CERRANDO ---");
            generador.detener();
            hGenerador.interrupt();
            
            for(Thread h : hilosTaquillas) {
                if(h.isAlive()) h.interrupt();
            }
            
            long tiempoFin = System.currentTimeMillis();

            // Info simulación
            Thread.sleep(2000);
            System.out.println("\nRESULTADOS FINALES");
            System.out.println("-------------------------------------------");
            System.out.println("1. Clientes que vieron la película: " + cola.getVendidas());
            System.out.println("2. Clientes sin entrada:  " + cola.getGenteEnCola());
            System.out.println("3. Tiempo total de venta: " + (tiempoFin - tiempoInicio) + " ms");
            System.out.println("-------------------------------------------");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
