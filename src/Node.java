import java.util.ArrayList;

/**
 * Represäntiert einen Wegpunkt auf der Karte zu dem die Roboter fahren können und der mit anderen Wegpunkten verbunden ist
 */
class Node {

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
     * Enthält alle Nodes mit denen diese Node verbunden ist
     */
    private ArrayList<Node> neighbourNodes;

    /**
     * erzeugt eine neue Alleinstehende Node
     *
     * @param id eindeutige ID
     */
    Node(int id) {
        this.id = id;
        //nodeType = 0;
        neighbourNodes = new ArrayList<>();
    }

    /**
     * Erzeugt einen neue Node mit einer Position
     *
     * @param id eindeutige id
     * @param x  x Position auf der Karte
     * @param y  y Position auf der Karte
     */
    Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        //nodeType = 0;
        neighbourNodes = new ArrayList<>();
    }

    /**
     * Fügt eine Node zu den Nachbarnodes dieser Node hinzu
     *
     * @param n die Node die als Nachbar hinzugefügt werden soll
     */
    void addNeighbour(Node n) {
        neighbourNodes.add(n);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Node.class.isAssignableFrom(obj.getClass())) return false;
        Node n = (Node) obj;
        return n.getId() == this.getId();
    }

    @Override
    public String toString() {
        StringBuilder neighbours = new StringBuilder();
        for (Node n : neighbourNodes) {
            neighbours.append(n.getId());
            neighbours.append(", ");
        }
        return "Node " + getId() + ", connected to: " + neighbours.toString();
    }
}
