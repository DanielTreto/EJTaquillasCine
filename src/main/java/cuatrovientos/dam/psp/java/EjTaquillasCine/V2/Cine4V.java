package cuatrovientos.dam.psp.java.EjTaquillasCine.V2;

import java.util.ArrayList;

public class Cine4V {

	public static void main(String[] args) {

		// CONSTANTES
		int NUM_TAQUILLAS = 2;
		int NUM_COLAS = 4;
		long DURACION_TOTAL = 30 * 60 * 1000;

		// Variables de Hilos
		ArrayList<ColaCine> colas = new ArrayList<>();
		ArrayList<Thread> hilosTaquillas = new ArrayList<>();

		try {
			System.out.println("--- INICIO CINE 4V ---");

			// Crear Colas
			for (int i = 1; i <= NUM_COLAS; i++) {
				colas.add(new ColaCine("Cola" + i));
			}

			// Crear Generador
			GeneradorClientes generador = new GeneradorClientes(colas);
			Thread hGenerador = new Thread(generador);
			hGenerador.start();

			// Crear Taquillas
			for (int i = 1; i <= NUM_TAQUILLAS; i++) {
				Taquilla t = new Taquilla("Taq-" + i, colas);
				Thread hilo = new Thread(t);
				hilosTaquillas.add(hilo);
				hilo.start();
			}

			// Esperar el tiempo de simulación
			long tiempoInicio = System.currentTimeMillis();
			Thread.sleep(DURACION_TOTAL);

			// Cerramos generador de clientes y taquillas
			System.out.println("\n--- FIN DEL TIEMPO: CERRANDO ---");
			generador.detener();
			hGenerador.interrupt();

			for (Thread h : hilosTaquillas) {
				if (h.isAlive())
					h.interrupt();
			}

			// Calculamos de entre todas las colas la informacion a mostrar
			int totalVendidas = 0;
			int totalSinEntrada = 0;
			long ultimaVentaGlobal = tiempoInicio;

			for (ColaCine c : colas) {
				totalVendidas += c.getVendidas();

				totalSinEntrada += c.getGenteEnCola();

				// Buscamos el tiempo de la última venta
				if (c.getTiempoUltimaVenta() > ultimaVentaGlobal)
					ultimaVentaGlobal = c.getTiempoUltimaVenta();
			}

			long tiempoFin = ultimaVentaGlobal - tiempoInicio;
			if (totalVendidas == 0) {
				tiempoFin = 0;
			}

			// Info simulación
			Thread.sleep(2000);
			System.out.println("\nRESULTADOS FINALES");
			System.out.println("-------------------------------------------");
			System.out.println("1. Clientes que vieron la película: " + totalVendidas);
			System.out.println("2. Clientes sin entrada:  " + totalSinEntrada);
			System.out.println("3. Tiempo total de venta: " + tiempoFin + " ms");
			System.out.println("-------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
