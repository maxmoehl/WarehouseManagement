package warehousemanagment.navigation;

import javax.swing.*;
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
     * x-Koordinate auf der Karte
     */
    private int x;
    /**
     * y-Koordinate auf der Karte
     */
    private int y;

    /**
     * Enthält alle Nodes mit denen diese warehousemanagment.navigation.Node verbunden ist
     */
    private ArrayList<Node> neighbourNodes;

    /**
     * erzeugt eine neue Alleinstehende warehousemanagment.navigation.Node
     *
     * @param id eindeutige ID
     */
    public Node(int id) {
        this.id = id;
        neighbourNodes = new ArrayList<>();
    }

    /**
     * Erzeugt einen neue warehousemanagment.navigation.Node mit einer Position
     *
     * @param id eindeutige id
     * @param x  x Position auf der Karte
     * @param y  y Position auf der Karte
     * @param width grafische Breite auf der Karte
     * @param height grafische Höhe auf der Karte
     */
    //müssen width und height haben
    public Node(int id, int x, int y, int width, int height) {
        super();
        setSize(width, height);
        setLocation(x, y);
        this.id = id;
        // this.x = x;
        //this.y = y;
        neighbourNodes = new ArrayList<>();
    }

    /**
     * Fügt eine warehousemanagment.navigation.Node zu den Nachbarnodes dieser warehousemanagment.navigation.Node hinzu
     *
     * @param n die warehousemanagment.navigation.Node die als Nachbar hinzugefügt werden soll
     */
    public void addNeighbour(Node n) {
        neighbourNodes.add(n);
    }

    public ArrayList<Node> getNeighbourNodes() {
        return neighbourNodes;
    }
    public int getId() {
        return id;
    }

    public boolean isNeighbourNode(Node n) {
        for (Node neighbour : neighbourNodes) {
            if (neighbour.equals(n)) {
                return true;
            }
        }
        return false;
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
        return "warehousemanagment.navigation.Node " + getId() + ", connected to: " + neighbours.toString();
    }
}
