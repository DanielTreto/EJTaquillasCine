package cuatrovientos.dam.psp.java.EjTaquillasCine.V1;

public class Taquilla implements Runnable {

	// CONSTANTES y configuración
	private static final int TIEMPO_MIN_VENTA = 20000;
	private static final int TIEMPO_MAX_VENTA = 30000;
	private static int asientos = 200;

	private String nombre;
	private ColaCine colaCine;

	private long timestampInicio;

	public Taquilla(String nombre, ColaCine cola) {
		this.nombre = nombre;
		this.colaCine = cola;
		this.timestampInicio = System.currentTimeMillis();
	}

	@Override
	public void run() {
		try {
			log("Taquilla abierta");
			// Mientras queden asientos, seguimos trabajando
			while (getAsientos() > 0) {

				// Intentamos coger un cliente
				Cliente cliente = colaCine.recuperarSiguiente();

				if (cliente != null) {
					log("Atendiendo a " + cliente.getId() + "...");

					// Simulamos el tiempo que cuesta cobrar
					long duracion = (long) (Math.random() * (TIEMPO_MAX_VENTA - TIEMPO_MIN_VENTA) + TIEMPO_MIN_VENTA);
					Thread.sleep(duracion);

					// Confirmamos la venta y restamos asiento si hay asientos
					if (getAsientos() > 0) {
						restarAsiento();
						colaCine.finalizarVenta(cliente);
						log("Venta cobrada a " + cliente.getId() + ". Asientos restantes: " + getAsientos());
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
