package citasciegas.vista;

import citasciegas.modelo.*;
import citasciegas.algoritmo.HopcroftKarp;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelGrafo extends JPanel {
    private List<Aprendiz> aprendices;
    private List<Mentor> mentores;
    private Map<String, List<String>> adj;
    private HopcroftKarp hk;
    private Persona nodoSeleccionado = null;

    public PanelGrafo(List<Aprendiz> aprendices, List<Mentor> mentores, Map<String, List<String>> adj, HopcroftKarp hk) {
        this.aprendices = aprendices;
        this.mentores = mentores;
        this.adj = adj;
        this.hk = hk;
        setBackground(new Color(25, 25, 35));
        
        int yOffset = 80;
        int ySpacing = 90;
        
        for (int i = 0; i < aprendices.size(); i++) {
            aprendices.get(i).x = 120;
            aprendices.get(i).y = yOffset + (i * ySpacing);
        }
        for (int i = 0; i < mentores.size(); i++) {
            mentores.get(i).x = 580;
            mentores.get(i).y = yOffset + (i * ySpacing);
        }

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                nodoSeleccionado = null;
                for (Aprendiz a : aprendices) {
                    if (Math.hypot(e.getX() - a.x, e.getY() - a.y) < 25) {
                        nodoSeleccionado = a;
                        break;
                    }
                }
                if (nodoSeleccionado == null) {
                    for (Mentor m : mentores) {
                        if (Math.hypot(e.getX() - m.x, e.getY() - m.y) < 25) {
                            nodoSeleccionado = m;
                            break;
                        }
                    }
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Aristas iniciales
        g2.setStroke(new BasicStroke(1));
        for (Aprendiz a : aprendices) {
            List<String> conexiones = adj.get(a.id);
            if (conexiones != null) {
                for (String mId : conexiones) {
                    Mentor m = buscarMentor(mId);
                    if (m != null) {
                        if (nodoSeleccionado != null) {
                            if (nodoSeleccionado.id.equals(a.id) || nodoSeleccionado.id.equals(mId)) {
                                g2.setColor(new Color(255, 165, 0, 220));
                                g2.setStroke(new BasicStroke(2));
                            } else {
                                g2.setColor(new Color(60, 60, 80, 50));
                            }
                        } else {
                            g2.setColor(new Color(80, 80, 100, 150));
                        }
                        g2.drawLine(a.x, a.y, m.x, m.y);
                    }
                }
            }
        }

        // Aristas de emparejamiento
        g2.setStroke(new BasicStroke(4));
        g2.setColor(new Color(50, 255, 120));
        Map<String, String> matches = hk.getPairAprendiz();
        for (Map.Entry<String, String> entry : matches.entrySet()) {
            if (entry.getValue() != null) {
                Aprendiz a = buscarAprendiz(entry.getKey());
                Mentor m = buscarMentor(entry.getValue());
                if (a != null && m != null) {
                    g2.drawLine(a.x, a.y, m.x, m.y);
                }
            }
        }

        // Dibujar Aprendices
        for (Aprendiz a : aprendices) {
            boolean esSeleccionado = (nodoSeleccionado != null && nodoSeleccionado.id.equals(a.id));
            g2.setColor(esSeleccionado ? new Color(100, 200, 255) : new Color(30, 144, 255));
            g2.fillOval(a.x - 20, a.y - 20, 40, 40);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(a.x - 20, a.y - 20, 40, 40);
            
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(a.nombre, a.x - 90, a.y + 5);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(180, 180, 200));
            g2.drawString(a.intereses.toString(), a.x - 100, a.y + 20);
        }

        // Dibujar Mentores
        for (Mentor m : mentores) {
            boolean esSeleccionado = (nodoSeleccionado != null && nodoSeleccionado.id.equals(m.id));
            g2.setColor(esSeleccionado ? new Color(255, 120, 180) : new Color(220, 20, 60));
            g2.fillOval(m.x - 20, m.y - 20, 40, 40);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(m.x - 20, m.y - 20, 40, 40);
            
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.setColor(Color.WHITE);
            g2.drawString(m.nombre, m.x + 30, m.y + 5);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(180, 180, 200));
            g2.drawString(m.intereses.toString(), m.x + 30, m.y + 20);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.ITALIC, 11));
        g2.drawString("💡 Haz clic en cualquier nodo para aislar sus intereses y conexiones (Filtro Visual).", 20, getHeight() - 20);
    }

    private Aprendiz buscarAprendiz(String id) {
        return aprendices.stream().filter(a -> a.id.equals(id)).findFirst().orElse(null);
    }

    private Mentor buscarMentor(String id) {
        return mentores.stream().filter(m -> m.id.equals(id)).findFirst().orElse(null);
    }
}