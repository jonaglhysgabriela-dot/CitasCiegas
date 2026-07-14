package citasciegas.algoritmo;

import java.util.*;

public class HopcroftKarp {
    private static final int INF = Integer.MAX_VALUE;
    private Map<String, List<String>> adj; 
    private Map<String, String> pairAprendiz;
    private Map<String, String> pairMentor;
    private Map<String, Integer> dist;
    
    private List<String> caminoActual;
    private String estadoActual = "Esperando inicio...";

    public HopcroftKarp(Map<String, List<String>> adj) {
        this.adj = adj;
        this.pairAprendiz = new HashMap<>();
        this.pairMentor = new HashMap<>();
        this.dist = new HashMap<>();
        this.caminoActual = new ArrayList<>();
    }

    public Map<String, String> getPairAprendiz() { return pairAprendiz; }
    public List<String> getCaminoActual() { return caminoActual; }
    public String getEstadoActual() { return estadoActual; }

    public void inicializar() {
        pairAprendiz.clear();
        pairMentor.clear();
        caminoActual.clear();
        estadoActual = "Algoritmo inicializado. Estructuras limpias.";
    }

    public boolean ejecutarPaso() {
        caminoActual.clear();
        if (!bfs()) {
            estadoActual = "Fin del Algoritmo: No se encontraron más caminos de aumento. Emparejamiento máximo alcanzado.";
            return false;
        }

        boolean seAumento = false;
        estadoActual = "Caminos de aumento encontrados con BFS. Asignando parejas mediante DFS...";
        
        for (String u : adj.keySet()) {
            if (!pairAprendiz.containsKey(u) || pairAprendiz.get(u) == null) {
                if (dfs(u)) {
                    seAumento = true;
                }
            }
        }
        return seAumento;
    }

    private boolean bfs() {
        Queue<String> queue = new LinkedList<>();
        for (String u : adj.keySet()) {
            if (!pairAprendiz.containsKey(u) || pairAprendiz.get(u) == null) {
                dist.put(u, 0);
                queue.add(u);
            } else {
                dist.put(u, INF);
            }
        }
        dist.put(null, INF);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            if (dist.get(u) < dist.get(null)) {
                for (String v : adj.getOrDefault(u, new ArrayList<>())) {
                    String vPair = pairMentor.get(v);
                    if (dist.get(vPair) == INF) {
                        dist.put(vPair, dist.get(u) + 1);
                        queue.add(vPair);
                    }
                }
            }
        }
        return dist.get(null) != INF;
    }

    private boolean dfs(String u) {
        if (u != null) {
            for (String v : adj.getOrDefault(u, new ArrayList<>())) {
                String vPair = pairMentor.get(v);
                if (dist.get(vPair) == dist.get(u) + 1) {
                    if (dfs(vPair)) {
                        pairMentor.put(v, u);
                        pairAprendiz.put(u, v);
                        caminoActual.add(u + " -> " + v);
                        return true;
                    }
                }
            }
            dist.put(u, INF);
            return false;
        }
        return true;
    }
}