package cuatrovientos.dam.psp.java.EjTaquillasCine.V2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ColaCine {

	// CONSTANTES
	private static final int CAPACIDAD = 10;

	private String nombre;

	private List<Cliente> clientesEnCola;
	private int clientesEnAtencion = 0;
	private int contadorVendidas = 0;

	private long tiempoUltimaVenta = 0;
	private long tiempoInicio;

	private Semaphore semaforo = new Semaphore(1);

	public ColaCine(String nombre) {
		this.nombre = nombre;
		this.clientesEnCola = new ArrayList<>();
		this.tiempoInicio = System.currentTimeMillis();
		this.tiempoUltimaVenta = System.currentTimeMillis();
	}

	// Método para añadir clientes
	public boolean llegarACola(Cliente cliente) throws InterruptedException {
		boolean entro = false;
		semaforo.acquire();
		if (clientesEnCola.size() < CAPACIDAD) {
			this.clientesEnCola.add(cliente);
			entro = true;
			log("Entra nuevo cliente: " + cliente.getId());
		}
		semaforo.release();
		return entro;
	}

	// Método para sacar clientes
	public Cliente recuperarSiguiente() throws InterruptedException {
		Cliente cliente = null;

		semaforo.acquire();

		// Recuperamos un cliente si hay asientos y hay gente
		if (!this.clientesEnCola.isEmpty() && Taquilla.getAsientos() > 0) {
			cliente = this.clientesEnCola.get(0);
			this.clientesEnCola.remove(0);
			this.clientesEnAtencion++;
		}

		semaforo.release();

		return cliente;
	}

	// Registra la finalización exitosa de una venta y actualiza las estadísticas
	public void finalizarVenta(Cliente cliente) throws InterruptedException {
		semaforo.acquire();

		this.clientesEnAtencion--;
		contadorVendidas++;
		this.tiempoUltimaVenta = System.currentTimeMillis();

		semaforo.release();
	}

	public String getNombre() {
		return nombre;
	}

	public int getVendidas() {
		return contadorVendidas;
	}

	public int getGenteEnCola() {
		return clientesEnCola.size() + clientesEnAtencion;
	}

	public long getTiempoUltimaVenta() {
		return tiempoUltimaVenta;
	}

	private void log(String msg) {
		long tiempoActual = System.currentTimeMillis() - this.tiempoInicio;
		System.out.println("\t[" + nombre + "][T:" + tiempoActual + "ms]: " + msg);
	}
}
