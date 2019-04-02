import java.util.ArrayList;

class Robot implements Runnable {

    /**
     * Eindeutige id zum vergleichen mit anderen Robotern
     */
    private final int id;

    /**
     * Die Nodes die der Roboter als naechstes abfahren muss (von 0 beginnend)
     */
    private ArrayList<Node> graph;

    /**
     * Die aktuelle Node auf der der Roboter sich befindet
     */
    private Node currentNode;

    /**
     * Speichert was der Roboter aktuell in seinem Inventar hat, 0 = nichts, größer 0 = irgendeine ware
     */
    private int inventoryMaterialType;
    private int inventoryAmount;

    /**
     * Initialisiert einen neuen Roboter an der gegebenen Node. Jeder Roboter hat einen eigenen Thread und arbeitet deswegen voellig
     * unabhaegig. Die Threads sind {@code isDaemmon() = true} und werden terminiert wenn der main-Thread terminiert wird.
     *
     * @param id    eindeutige id
     * @param start Node an der der Roboter initalisiert wird
     */
    Robot(int id, Node start) {
        this.id = id;
        currentNode = start;
        inventoryMaterialType = 0;
        inventoryAmount = 0;
        graph = new ArrayList<>();
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Zum ueberpruefen ob der Roboter noch Nodes hat die er abarbeiten muss
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

    /**
     * Bewegt den Roboter zur naechste Node die auf dem Graph gegeben ist, der Vorgang sperrt den Roboter für eine Sekunde
     *
     * @see Robot#lock
     */
    private void move() {
        lock(1000);
        currentNode = getNextNode();
        graph.remove(0);
    }

    /**
     * Greift auf die {@link Robot#currentNode} zu und laedt ein oder aus, muss auf einer StorageNode ausgefuehrt werden
     * <ul>
     * <li>Wenn der Roboter Ladung in seinem Inventar hat versucht seine Waren in die Node abzuladen</li>
     * <li>Wenn der Roboter keine Ladung hat versucht er Waren aus der Node zu laden</li>
     * </ul>
     * Der Vorgang dauert eine Sekunde und sperrt die {@link Robot#currentNode}
     *
     * @see Robot#lock
     * @see StorageNode#loadItems
     * @see StorageNode#unloadItems
     */
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

    /**
     * Ueberprueft ob der Graph erneuert werden soll und erneuert diesen wenn noetig
     */
    private void updateGraph() {
        if (graph.size() == 0) {
            graph = Router.getRouter().calculateRoute(getCurrentNode(), Router.getRouter().getNextDestination());
        } else {
            throw new RuntimeException("Graph hat noch Elemente und kann nicht geupdated werden");
        }
    }

    /**
     * Pausiert den Thread für eine gewisse Zeit, definiert in {@code timeout} und sperrt Ressourcen falls die Methode
     * in einem synchronized Block ausgefuehrt wird
     *
     * @param timeout Zeit in Millisekunden
     */
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
        while (true) {
            work();
        }
    }

    /**
     * Fuehrt einen kompletten Arbeitsdurchlauf aus
     * <ul>
     * <li>Wenn weitere Nodes Nodes vorhanden ist bewegt sich der Roboter eine Node weiter, indem er {@link Robot#move}</li>
     * <li>
     * Andernfalls wird ueberprueft ob der Roboter an dieser Node umladen kann
     * <ul>
     * <li>Ja: Umladen mit {@link Robot#load} und einen den Graphen erneuern {@link Robot#updateGraph}</li>
     * <li>Nein: RuntimeException werfen</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @throws RuntimeException
     */
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

    private void printStatus() {
        //TODO remove
        //System.out.println("From Current Node " + getCurrentNode().getId() + " to " + (graph.size() == 0 ? "-" : graph.get(graph.size() - 1).getId()) + " with inventory: " + inventoryMaterialType + ", " + inventoryAmount);
        System.out.println("+--------+--------+--------+-----+-----+");
        System.out.println("|akt.Node|zielNode|R-Invent|Node0|Node7|");
        System.out.println("+--------+--------+--------+-----+-----+");
        System.out.println("|" + getCurrentNode().getId() + "       |" + (graph.size() == 0 ? "-" : graph.get(graph.size() - 1).getId()) + "       |" + inventoryMaterialType + ", " + inventoryAmount + "    |" +
                ((StorageNode) Router.getRouter().nodes.get(0)).materialType + ", " + ((StorageNode) Router.getRouter().nodes.get(0)).amount + " |" + ((StorageNode) Router.getRouter().nodes.get(7)).materialType + ", " + ((StorageNode) Router.getRouter().nodes.get(7)).amount + "|");
        System.out.println("+--------+--------+--------+-----+-----+");
    }
}
