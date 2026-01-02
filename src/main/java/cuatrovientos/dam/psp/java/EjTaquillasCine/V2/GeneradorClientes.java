package cuatrovientos.dam.psp.java.EjTaquillasCine.V2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneradorClientes implements Runnable {

	// CONSTANTES
	private static final int TIEMPO_LLEGADA = 60000;
	private static final int MIN_GENTE = 10;
	private static final int MAX_GENTE = 15;

	private ArrayList<ColaCine> colas;
	private boolean activo = true;

	private long timestampInicio;

	public GeneradorClientes(ArrayList<ColaCine> colas) {
		this.colas = colas;
		this.timestampInicio = System.currentTimeMillis();
	}

	@Override
	public void run() {
		int id = 1;
		Random rnd = new Random();

		try {
			// Esperamos 1s para que no lleguen primero los clientes antes de abrir las
			// taquillas
			Thread.sleep(1000);
			// Genera clientes mientras que este activo y haya asientos libres
			while (activo && Taquilla.getAsientos() > 0) {

				// Decidimos cuántos vienen
				int cantidad = rnd.nextInt(MAX_GENTE - MIN_GENTE + 1) + MIN_GENTE;
				log("LLEGAN " + cantidad + " PERSONAS");

				// Creamos los clientes y los intentamos añadir a la cola
				for (int i = 0; i < cantidad; i++) {
					Cliente c = new Cliente("C-" + id++);
					boolean entro = false;

					// Copiamos la lista para no desordenar la original
					ArrayList<ColaCine> colasBarajadas = new ArrayList<>(this.colas);

					// Mezclamos las colas. Así el cliente puede entrar en cualquier cola disponible
					// y no solo en la primera
					Collections.shuffle(colasBarajadas);

					// Probar en todas las colas
					for (ColaCine cola : colasBarajadas) {
						if (cola.llegarACola(c)) {
							entro = true;
							break;
						}
					}

					// Si no entra imprimimos un mensaje
					if (!entro) {
						log(c.getId() + " no puede entrar en ninguna cola (Todo lleno)");
					}
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
		System.out.println("[T:" + tiempoActual + "ms]: " + msg);
	}
}
