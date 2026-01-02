package cuatrovientos.dam.psp.java.EjTaquillasCine.V2;

public class Cliente {
	private String id;
	private long horaLlegada;

	public Cliente(String id) {
		this.id = id;
		this.horaLlegada = System.currentTimeMillis();
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Cliente " + id + " (Llegó a las " + horaLlegada + ")";
	}
}
