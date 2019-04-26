package warehousemanagement.navigation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represäntiert einen Wegpunkt auf der Karte zu dem die Roboter fahren können und der mit anderen Wegpunkten verbunden ist
 */
public class Node extends JComponent {

    /**
     * Eindeutige ID zum Vergleichen
     */
    private final int id;

    /**
     * Speichert während der Pfadberechnung den kürzesten Weg zu dieser Node
     */
    ArrayList<Node> shortestPath;

    /**
     * Speichert während der Pfadberechnung die Länge des gespeicherten kürzesten Weges
     */
    int distance;

    /**
     * Interner Wert der kontrolliert ob Roboter an der Node sind um die Node entsprechend zu zeichnen
     */
    int robots;
    /**
     * Enthält alle Nodes mit denen diese Node verbunden ist
     */
    private ArrayList<Node> neighbourNodes;

    /**
     * Erzeugt einen neue Node mit einer Position
     *
     * @param id     eindeutige id
     * @param x      x Position auf der Karte
     * @param y      y Position auf der Karte
     * @param width  grafische Breite auf der Karte
     * @param height grafische Höhe auf der Karte
     */
    public Node(int id, int x, int y, int width, int height) {
        super();
        setSize(width, height);
        setLocation(x, y);
        this.id = id;
        neighbourNodes = new ArrayList<>();
        shortestPath = new ArrayList<>();
        distance = Integer.MAX_VALUE;
        robots = 0;
    }

    /**
     * Fügt eine Node zu den Nachbarnodes dieser Node hinzu
     *
     * @param n die Node die als Nachbar hinzugefügt werden soll
     */
    public void addNeighbour(Node n) {
        neighbourNodes.add(n);
    }

    ArrayList<Node> getNeighbourNodes() {
        return neighbourNodes;
    }

    public int getId() {
        return id;
    }

    public void register() {
        robots++;
    }

    public void unregister() {
        robots = robots > 0 ? robots - 1 : 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (robots > 0) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) return false;
        Node n = (Node) obj;
        return this.getId() == n.getId();
    }

    @Override
    public String toString() {
        StringBuilder neighbours = new StringBuilder();
        for (Node n : neighbourNodes) {
            neighbours.append(n.getId());
            neighbours.append(", ");
        }
        return this.getClass() + " " + getId() + ", connected to: " + neighbours.toString();
    }
}
