package citasciegas;

import citasciegas.modelo.*;
import citasciegas.algoritmo.HopcroftKarp;
import citasciegas.vista.PanelGrafo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Main extends JFrame {
    private List<Aprendiz> aprendices;
    private List<Mentor> mentores;
    private Map<String, List<String>> adj;
    private HopcroftKarp hk;
    
    private PanelGrafo panelGrafo;
    private JTextArea txtConsola;
    private JButton btnPasoAPaso;
    private JButton btnReiniciar;

    public Main() {
        setTitle("Blind Mentoring Matcher - CitasCiegas");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarDatos();
        mostrarPantallaCarga();
    }

    private void inicializarDatos() {
        aprendices = new ArrayList<>();
        mentores = new ArrayList<>();
        adj = new HashMap<>();

        aprendices.add(new Aprendiz("A1", "Alice (Estudiante)", new HashSet<>(Arrays.asList("Java", "IA"))));
        aprendices.add(new Aprendiz("A2", "Bob (Junior)", new HashSet<>(Arrays.asList("Web", "Java"))));
        aprendices.add(new Aprendiz("A3", "Charlie (Estudiante)", new HashSet<>(Arrays.asList("Python", "IA"))));
        aprendices.add(new Aprendiz("A4", "Diana (Junior)", new HashSet<>(Arrays.asList("Java"))));

        mentores.add(new Mentor("M1", "Ing. Evans (Senior)", new HashSet<>(Arrays.asList("Java"))));
        mentores.add(new Mentor("M2", "Dra. Green (Científica)", new HashSet<>(Arrays.asList("IA", "Python"))));
        mentores.add(new Mentor("M3", "Sr. Harris (Fullstack)", new HashSet<>(Arrays.asList("Web", "Java"))));
        mentores.add(new Mentor("M4", "Ing. Díaz (DevOps)", new HashSet<>(Arrays.asList("Docker"))));

        for (Aprendiz a : aprendices) {
            List<String> vecinos = new ArrayList<>();
            for (Mentor m : mentores) {
                Set<String> interseccion = new HashSet<>(a.intereses);
                interseccion.retainAll(m.intereses);
                if (!interseccion.isEmpty()) {
                    vecinos.add(m.id);
                }
            }
            adj.put(a.id, vecinos);
        }

        hk = new HopcroftKarp(adj);
    }

    private void mostrarPantallaCarga() {
        JWindow splash = new JWindow();
        JPanel panelSplash = new JPanel(new BorderLayout());
        panelSplash.setBackground(new Color(15, 15, 25));
        panelSplash.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));

        JLabel lblTitulo = new JLabel("CITAS CIEGAS: MENTORING SYSTEM", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel lblSub = new JLabel("Cargando módulos de grafos de Hopcroft-Karp...", SwingConstants.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(new Color(150, 150, 170));

        JProgressBar barraProgreso = new JProgressBar();
        barraProgreso.setIndeterminate(true);
        barraProgreso.setForeground(new Color(30, 144, 255));
        barraProgreso.setBackground(new Color(30, 30, 45));

        panelSplash.add(lblTitulo, BorderLayout.NORTH);
        panelSplash.add(lblSub, BorderLayout.CENTER);
        panelSplash.add(barraProgreso, BorderLayout.SOUTH);

        splash.setContentPane(panelSplash);
        splash.setSize(450, 200);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        javax.swing.Timer timer = new javax.swing.Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splash.dispose();
                construirInterfazPrincipal();
                setVisible(true);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void construirInterfazPrincipal() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(20, 20, 30));
        JLabel lblHeader = new JLabel("  CitasCiegas - Emparejamiento Máximo en Grafos Bipartitos", SwingConstants.LEFT);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblHeader.setForeground(Color.CYAN);
        lblHeader.setPreferredSize(new Dimension(100, 50));
        panelTop.add(lblHeader, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);

        panelGrafo = new PanelGrafo(aprendices, mentores, adj, hk);
        add(panelGrafo, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setPreferredSize(new Dimension(280, 100));
        panelDerecho.setBackground(new Color(35, 35, 45));
        panelDerecho.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(50, 50, 65)));

        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        txtConsola.setBackground(new Color(15, 15, 20));
        txtConsola.setForeground(new Color(100, 255, 100));
        txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtConsola.setMargin(new Insets(10, 10, 10, 10));
        txtConsola.setText(">> Sistema Listo.\nPresiona 'Siguiente Paso'\npara buscar caminos\nde aumento.");

        JScrollPane scrollConsola = new JScrollPane(txtConsola);
        scrollConsola.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Simulador Algorítmico",
                0, 0, null, Color.WHITE));
        panelDerecho.add(scrollConsola, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBotones.setBackground(new Color(35, 35, 45));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnPasoAPaso = new JButton("Siguiente Paso (DFS/BFS)");
        btnPasoAPaso.setBackground(new Color(30, 144, 255));
        btnPasoAPaso.setForeground(Color.WHITE);
        btnPasoAPaso.setFocusPainted(false);

        btnReiniciar = new JButton("Reiniciar Simulación");
        btnReiniciar.setBackground(new Color(80, 80, 90));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);

        panelBotones.add(btnPasoAPaso);
        panelBotones.add(btnReiniciar);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        add(panelDerecho, BorderLayout.EAST);

        btnPasoAPaso.addActionListener(e -> {
            boolean huboCambios = hk.ejecutarPaso();
            txtConsola.setText(">> ESTADO:\n" + hk.getEstadoActual() + "\n\n");
            
            if (!hk.getCaminoActual().isEmpty()) {
                txtConsola.append(">> Caminos de Aumento en esta iteración:\n");
                for (String cam : hk.getCaminoActual()) {
                    txtConsola.append(" - " + cam + "\n");
                }
            }
            
            if (!huboCambios && !hk.getEstadoActual().startsWith("Algoritmo")) {
                btnPasoAPaso.setEnabled(false);
            }
            panelGrafo.repaint();
        });

        btnReiniciar.addActionListener(e -> {
            hk.inicializar();
            txtConsola.setText(">> Sistema Reiniciado.\nGrafo restaurado al estado original.");
            btnPasoAPaso.setEnabled(true);
            panelGrafo.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}