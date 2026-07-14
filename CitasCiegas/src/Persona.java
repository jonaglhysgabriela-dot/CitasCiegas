package citasciegas.modelo;

import java.util.Set;

public class Persona {
    public String id;
    public String nombre;
    public Set<String> intereses;
    public int x, y; // Coordenadas para el dibujo en el grafo

    public Persona(String id, String nombre, Set<String> intereses) {
        this.id = id;
        this.nombre = nombre;
        this.intereses = intereses;
    }
} 