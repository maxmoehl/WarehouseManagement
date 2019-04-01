import java.util.ArrayList;

class Robot implements Runnable {

    /**
     * eindeutige id zum vergleichen mit anderen Robotern
     */
    private final int id;

    /**
     * Die Nodes die der Roboter als nächstes abfahren muss (von 0 beginnend)
     */
    private ArrayList<Node> graph;

    /**
     * Die aktuelle Node auf der der Robotter sich befindet
     */
    private Node currentNode;

    /**
     * Speichert was der Roboter aktuell in seinem Inventar hat, 0 = nichts, > 0 = irgendeine ware
     */
    private int inventoryItemType;
    private int inventoryAmount;

    private Router router;

    private Thread thread;

    /**
     * Initialisiert einen neuen Roboter an der gegebenen Node
     *
     * @param id
     * @param start
     */
    Robot(int id, Node start, Router router) {
        this.id = id;
        currentNode = start;
        this.router = router;
        inventoryItemType = 0;
        inventoryAmount = 0;
        graph = new ArrayList<>();
        thread = new Thread(this);
        thread.start();
    }

    /* --- Nicht benötigt da Roboter Weg aktiv anfragt ---
     * Überschreibt den aktuellen path und setzt einen neuen
     *
     * @param path path mit Nodes die abgegangen werden sollen
     *
     *
    void setPath(ArrayList<Node> path) {
        this.graph = path;
    }
     */

    /**
     * zum überprüfen ob der Roboter noch Nodes hat die er abarbeiten muss
     *
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

    private void move() {
        lock(1000);
        currentNode = getNextNode();
        graph.remove(0);
    }

    private void load() {
        //Lock access to this node and lock it for one second then unload/load items
        synchronized (getCurrentNode()) {
            lock(1000);
            // wenn die node delivery oder storage ist muss geprüft werden ob im inventar waren sind
            if (inventoryItemType == 0) {

            } else {
                StorageNode current = (StorageNode) getCurrentNode();
                current.loadItems(inventoryItemType, inventoryAmount);
                inventoryItemType = 0;
                inventoryAmount = 0;
            }
        }
    }

    private void updateGraph() {
        if (graph.size() == 0) {
            graph = router.calculateRoute(getCurrentNode(), router.getNextDestination());
        } else {
            throw new RuntimeException("Graph hat noch Elemente und kann nicht geupdated werden");
        }
    }

    private void lock(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Sleep aufruf fehlgeschlagen, Thread wurde interrupted");
        }
    }

    @Override
    public void run() {
        while (router.isAlive()) {
            work();
        }
    }

    private void work() {
        printGraph();
        // Is eine weitere Node vorhanden?
        if (hasNextNode()) {
            move();
            System.out.println("moved");
        } else {
            //wenn nicht muss wahrscheinlich verladen werden
            // TODO: Nach dem verladen muss unbedingt ein neuer Weg beantragt werden sonst lädt der Roboter ewig ein und aus
            if (getCurrentNode().getClass() == StorageNode.class || getCurrentNode().getClass() == DeliveryNode.class) {
                load();
                System.out.println("loaded");
                updateGraph();
            } else {
                // Andernfalls gibt es einen Fehler weil eine Endnode keine WAYPOINT Node sein soll
                throw new RuntimeException("Roboter hat keine weiteren Nodes, steht aber an einer Node an der er nicht verladen kann");
            }
        }
    }

    private void printGraph() {
        System.out.println("Current Graph (" + getCurrentNode().getId() + "):");
        for (Node n : graph) {
            System.out.println(n.toString());
        }
    }
}
