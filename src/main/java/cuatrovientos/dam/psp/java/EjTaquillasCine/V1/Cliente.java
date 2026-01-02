package cuatrovientos.dam.psp.java.EjTaquillasCine.V1;

public class Cliente {
    private String id;
    
    public Cliente(String id) { this.id = id; }
    
    public String getId() { return id; }
    
    @Override
    public String toString() { return "Cliente[" + id + "]"; }
}
