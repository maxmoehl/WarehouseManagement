import java.util.ArrayList;

class Robot {

    private int id;

    private ArrayList<Node> graph;

    private Node currentNode;

    Robot(int id, Node start) {
        this.id = id;
        currentNode = start;
    }

    void setPath(ArrayList<Node> path) {
        this.graph = path;
    }

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
}
