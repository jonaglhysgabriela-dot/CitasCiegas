# 🤝 Blind Mentoring Matcher (Hopcroft-Karp Simulator)

¡Bienvenido/a! Este es un simulador interactivo de escritorio desarrollado en **Java Swing** diseñado para resolver el problema del **emparejamiento bipartito máximo** (Maximum Bipartite Matching) en un ecosistema de mentorías ciegas. 

El sistema conecta automáticamente a **Aprendices** con **Mentores** basándose en la coincidencia de sus intereses tecnológicos (Java, IA, Python, Web, etc.), utilizando el potente algoritmo de **Hopcroft-Karp**.

---

## 🚀 Características Principales

*   **Algoritmo Paso a Paso (BFS/DFS):** Visualiza en tiempo real cómo trabaja Hopcroft-Karp. La interfaz te permite avanzar iteración por iteración para observar cómo se encuentran y asignan los caminos de aumento.
*   **Representación Visual del Grafo:** Un lienzo interactivo que dibuja a los aprendices (izquierda) y a los mentores (derecha), destacando en color verde las conexiones óptimas finales.
*   **Filtro Interactivo:** Haz clic en cualquier nodo para aislar visualmente sus intereses y conexiones, facilitando el análisis del grafo.
*   **Consola de Diagnóstico:** Un panel lateral tipo terminal que te describe detalladamente qué está ocurriendo detrás de escena (caminos de aumento encontrados, estados del BFS/DFS, etc.).
*   **Pantalla de Carga (Splash Screen):** Una interfaz de inicio elegante que simula la inicialización de las estructuras de datos antes de abrir la aplicación.

---

## 🧠 ¿Cómo funciona el algoritmo aquí?

El algoritmo de **Hopcroft-Karp** resuelve el problema de emparejamiento en un grafo bipartito en un tiempo de ejecución de $O(E \sqrt{V})$, lo cual es más rápido que el método clásico de Ford-Fulkerson en grafos densos.

1.  **Fase BFS (Búsqueda en Anchura):** Agrupa los nodos no emparejados y calcula las distancias para construir un "grafo de capas" con los caminos de aumento más cortos.
2.  **Fase DFS (Búsqueda en Profundidad):** Busca y asigna múltiples caminos de aumento disjuntos a lo largo de las capas calculadas, optimizando el emparejamiento de forma masiva en cada paso.

En este simulador, cada vez que presionas **"Siguiente Paso"**, se ejecuta una iteración completa de estas dos fases y se actualizan dinámicamente las aristas en pantalla.

---

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Java 8 o superior.
*   **Librerías Gráficas:** Java Swing & AWT (con RenderingHints para un dibujado suave/antialiasing).
*   **Paradigma:** Programación Orientada a Objetos (Modelado de Personas, Aprendices, Mentores y Grafo).
