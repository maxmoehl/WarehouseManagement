import java.util.ArrayList;

class Robot extends Thread {

    /**
     * eindeutige id zum vergleichen mit anderen Robotern
     */
    private int id;

    /**
     * Die Nodes die der Roboter als nächstes abfahren muss (von 0 beginnend)
     */
    private ArrayList<Node> graph;

    /**
     * Die aktuelle Node auf der der Robotter sich befindet
     */
    private Node currentNode;

    /**
     * Initialisiert einen neuen Roboter an der gegebenen Node
     * @param id
     * @param start
     */
    Robot(int id, Node start) {
        this.id = id;
        currentNode = start;
    }

    /**
     * Überschreibt den aktuellen path und setzt einen neuen
     * @param path path mit Nodes die abgegangen werden sollen
     */
    void setPath(ArrayList<Node> path) {
        this.graph = path;
    }

    /**
     * zum überprüfen ob der Roboter noch Nodes hat die er abarbeiten muss
     * @return sind im graph mehr als 0 Nodes?
     */
    boolean hasNextNode() {
        return graph.size() != 0;
    }

    Node getCurrentNode() {
        return currentNode;
    }

    Node getNextNode() {
        return graph.get(0);
    }

    void removeFirstNode() {
        graph.remove(0);
    }

    @Override
    public void run() {

    }
}
