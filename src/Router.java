import java.util.ArrayList;

/**
 * Enthält die gesamte Routing-Logik sowie alle Nodes die verfügbar sind
 */
class Router {

    /**
     * Nodes die von Robotern angesteuert werden können (werden demnächst in die Map umgezogen)
     */
    private ArrayList<Node> nodes;

    /**
     * Initialisiert die Nodes (in Zukunft sollen die Nodes aus der Map geladen werden)
     */
    Router() {
        nodes = new ArrayList<>();

        nodes.add(new Node(0));
        nodes.add(new Node(1));
        nodes.add(new Node(2));
        nodes.add(new Node(3));
        nodes.add(new Node(4));
        nodes.add(new Node(5));
        nodes.add(new Node(6));
        nodes.add(new Node(7));

        connectNodes(nodes.get(0), nodes.get(1));
        connectNodes(nodes.get(1), nodes.get(3));
        connectNodes(nodes.get(3), nodes.get(2));
        connectNodes(nodes.get(3), nodes.get(4));
        connectNodes(nodes.get(3), nodes.get(6));
        connectNodes(nodes.get(5), nodes.get(6));
        connectNodes(nodes.get(7), nodes.get(6));
    }

    private void connectNodes(Node n1, Node n2) {
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);
    }

    /**
     * Eine Methode die die optimale Route zwischen zwei Wegpunkten sucht
     * @param start Wegpunkt von dem aus gestartet wird
     * @param destination Wegpunkt zu dem die Route gehen soll
     * @return Eine Liste mit Nodes die den Weg zum Ziel beschreibt
     */
    ArrayList<Node> getRoute(Node start, Node destination) {
        return null;
    }
}
