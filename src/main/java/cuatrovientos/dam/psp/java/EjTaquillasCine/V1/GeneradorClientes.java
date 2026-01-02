package cuatrovientos.dam.psp.java.EjTaquillasCine.V1;

import java.util.Random;

public class GeneradorClientes implements Runnable {

	// CONSTANTES
	private static final int TIEMPO_LLEGADA = 60000;
	private static final int MIN_GENTE = 10;
	private static final int MAX_GENTE = 15;

	private ColaCine colaCine;
	private boolean activo = true;

	private long timestampInicio;

	public GeneradorClientes(ColaCine cola) {
		this.colaCine = cola;
		this.timestampInicio = System.currentTimeMillis();
	}

	@Override
	public void run() {
		int idGlobal = 1;
		Random rnd = new Random();

		try {
			// Esperamos 1s para que no lleguen primero los clientes antes de abrir las taquillas
			Thread.sleep(1000);
			// Genera clientes mientras que este activo y haya asientos libres
			while (activo && Taquilla.getAsientos() > 0) {

				// Decidimos cuántos vienen
				int cantidad = rnd.nextInt(MAX_GENTE - MIN_GENTE + 1) + MIN_GENTE;
				log("LLEGAN " + cantidad + " PERSONAS A LA COLA");

				// Creamos los clientes y los añadimos a la cola
				for (int i = 0; i < cantidad; i++) {
					Cliente c = new Cliente("Cli-" + idGlobal++);
					colaCine.llegarACola(c);
				}
				
				Thread.sleep(TIEMPO_LLEGADA);
				
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void detener() {
		activo = false;
	}

	private void log(String msg) {
		long tiempoActual = System.currentTimeMillis() - this.timestampInicio;
		System.out.println("\n[T:" + tiempoActual + "ms]: " + msg);
	}
}
