import java.util.ArrayList;

/**
 * Enthält die gesamte Routing-Logik sowie alle Nodes die verfügbar sind
 */
class Router {

    /**
     * Nodes die von Robotern angesteuert werden können (werden demnächst in die Map umgezogen)
     */
    ArrayList<Node> nodes;
    private boolean alive;

    /**
     * Initialisiert die Nodes (in Zukunft sollen die Nodes aus der Map geladen werden)
     */
    private Router() {
        alive = true;

        nodes = new ArrayList<>();

        nodes.add(new StorageNode(0));
        nodes.add(new Node(1));
        nodes.add(new Node(2));
        nodes.add(new Node(3));
        nodes.add(new Node(4));
        nodes.add(new Node(5));
        nodes.add(new Node(6));
        nodes.add(new DeliveryNode(7));

        connectNodes(nodes.get(0), nodes.get(1));
        connectNodes(nodes.get(1), nodes.get(3));
        connectNodes(nodes.get(3), nodes.get(2));
        connectNodes(nodes.get(3), nodes.get(4));
        connectNodes(nodes.get(3), nodes.get(6));
        connectNodes(nodes.get(5), nodes.get(6));
        connectNodes(nodes.get(7), nodes.get(6));

        StorageNode sN = (StorageNode) nodes.get(0);
        DeliveryNode dN = (DeliveryNode) nodes.get(7);
        sN.setMaterialType(1);
        dN.setMaterialType(1);

        dN.setLoading(true);
        dN.loadItems(1, 100);
        dN.setLoading(false);

        new Robot(0, nodes.get(7), this);
    }

    static Router getRouter() {
        return RouterHolder.ROUTER;
    }

    boolean isAlive() {
        return alive;
    }

    private void connectNodes(Node n1, Node n2) {
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);
    }

    /**
     * Eine Methode die die optimale Route zwischen zwei Wegpunkten sucht
     *
     * @param start       Wegpunkt von dem aus gestartet wird
     * @param destination Wegpunkt zu dem die Route gehen soll
     * @return Eine Liste mit Nodes die den Weg zum Ziel beschreibt
     */
    ArrayList<Node> calculateRoute(Node start, Node destination) {
        ArrayList<Node> route = new ArrayList<>();
        if (start.equals(nodes.get(0))) {
            route.add(nodes.get(1));
            route.add(nodes.get(3));
            route.add(nodes.get(6));
            route.add(nodes.get(7));
        } else if (start.equals(nodes.get(7))) {
            route.add(nodes.get(6));
            route.add(nodes.get(3));
            route.add(nodes.get(1));
            route.add(nodes.get(0));
        }
        return route;
    }

    Node getNextDestination() {

        /* Alle verladeentscheidungen hängen von den Lieferungen ab die an den LKW-Verladepunkten ankommen
         * Es muss also bekannt sein an welchen Nodes eine Lieferung steht/ankommt
         *
         * TODO: Ist das Aufgabe des Routers oder des Controllers?
         *
         * Delivery Nodes melden sich beim Router (oder beim Controller?) wenn sie frei werden
         */

        return null;
    }

    private static class RouterHolder {
        private static final Router ROUTER = new Router();
    }
}
