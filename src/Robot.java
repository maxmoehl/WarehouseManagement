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
    private int inventoryMaterialType;
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
        inventoryMaterialType = 0;
        inventoryAmount = 0;
        graph = new ArrayList<>();
        thread = new Thread(this);
        thread.start();
    }

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
            StorageNode current = (StorageNode) getCurrentNode();
            lock(1000);
            // wenn die node delivery oder storage ist muss geprüft werden ob im inventar waren sind
            if (inventoryMaterialType == 0) {
                inventoryMaterialType = current.getMaterialType();
                current.unloadItems(1);
                inventoryAmount += 1;
            } else {
                current.loadItems(inventoryMaterialType, inventoryAmount);
                inventoryMaterialType = 0;
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
        //TODO remove
        printStatus();
        // Is eine weitere Node vorhanden?
        if (hasNextNode()) {
            move();
            //TODO remove
            System.out.println("moved");
        } else {
            // Prüfen ob es eine StorageNode ist oder ein Objekt das von StorageNode erbt (DeliveryNode)
            if (StorageNode.class.isAssignableFrom(getCurrentNode().getClass())) {
                // umladen (wie auch immer)
                load();
                //TODO remove
                System.out.println("loaded");
                // einen neuen Graphen vom Router beziehen
                updateGraph();
            } else {
                // Andernfalls gibt es einen Fehler weil eine Endnode eine StorageNode sein muss
                throw new RuntimeException("Roboter hat keine weiteren Nodes, steht aber an einer Node an der er nicht verladen kann");
            }
        }
    }

    //TODO remove
    private void printStatus() {
        //System.out.println("From Current Node " + getCurrentNode().getId() + " to " + (graph.size() == 0 ? "-" : graph.get(graph.size() - 1).getId()) + " with inventory: " + inventoryMaterialType + ", " + inventoryAmount);
        System.out.println("+--------+--------+--------+-----+-----+");
        System.out.println("|akt.Node|zielNode|R-Invent|Node0|Node7|");
        System.out.println("+--------+--------+--------+-----+-----+");
        System.out.println("|" + getCurrentNode().getId() + "       |" + (graph.size() == 0 ? "-" : graph.get(graph.size() - 1).getId()) + "       |" + inventoryMaterialType + ", " + inventoryAmount + "    |" +
                ((StorageNode) Router.getRouter().nodes.get(0)).materialType + ", " + ((StorageNode) Router.getRouter().nodes.get(0)).amount + " |" + ((StorageNode) Router.getRouter().nodes.get(7)).materialType + ", " + ((StorageNode) Router.getRouter().nodes.get(7)).amount + "|");
        System.out.println("+--------+--------+--------+-----+-----+");
    }
}
