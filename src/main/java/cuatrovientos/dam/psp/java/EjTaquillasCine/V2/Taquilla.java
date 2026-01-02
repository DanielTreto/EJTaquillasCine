package cuatrovientos.dam.psp.java.EjTaquillasCine.V2;

import java.util.ArrayList;
import java.util.Collections;

public class Taquilla implements Runnable {

	// CONSTANTES y configuración
	private static final int TIEMPO_MIN_VENTA = 20000;
	private static final int TIEMPO_MAX_VENTA = 30000;
	private static int asientos = 200;

	private String nombre;
	private ArrayList<ColaCine> colas;

	private long timestampInicio;

	public Taquilla(String nombre, ArrayList<ColaCine> colas) {
		this.nombre = nombre;
		this.colas = colas;
		this.timestampInicio = System.currentTimeMillis();
	}

	@Override
	public void run() {
		try {
			log("Taquilla abierta");
			// Mientras queden asientos, seguimos trabajando
			while (getAsientos() > 0) {

				Cliente cliente = null;
				ColaCine colaOrigen = null;

				// Hacemos una copia de la lista de colas para esta venta
				ArrayList<ColaCine> ordenAleatorio = new ArrayList<>(this.colas);

				// Barajamos el orden de las colas para evitar priorizar siempre a la Cola 1
				// Esto da las mismas oportunidades de ser atendidos a todos los clientes
				Collections.shuffle(ordenAleatorio);

				// Buscar cliente en CUALQUIER cola
				for (ColaCine cola : ordenAleatorio) {
					cliente = cola.recuperarSiguiente();
					if (cliente != null) {
						colaOrigen = cola;
						break;
					}
				}

				if (cliente != null) {
					log("Atendiendo a " + cliente.getId() + " de la " + colaOrigen.getNombre() + "...");

					// Simulamos el tiempo que cuesta cobrar
					long duracion = (long) (Math.random() * (TIEMPO_MAX_VENTA - TIEMPO_MIN_VENTA) + TIEMPO_MIN_VENTA);
					Thread.sleep(duracion);

					// Confirmamos la venta y restamos asiento si hay asientos
					if (getAsientos() > 0) {
						restarAsiento();
						colaOrigen.finalizarVenta(cliente);
						log("Venta cobrada a " + cliente.getId() + " de la " + colaOrigen.getNombre()
								+ ". Asientos restantes: " + getAsientos());
					}

				} else {
					// Si la cola está vacía, esperamos un segundo
					Thread.sleep(1000);
				}
			}
			log("Cerrando (No quedan asientos).");

		} catch (InterruptedException e) {
			log("Interrumpida");
		}
	}

	public static synchronized int getAsientos() {
		return asientos;
	}

	private static synchronized void restarAsiento() {
		asientos--;
	}

	private void log(String msg) {
		long tiempoActual = System.currentTimeMillis() - this.timestampInicio;
		System.out.println("[" + nombre + "][T:" + tiempoActual + "ms]: " + msg);
	}
}
