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
     * Die DeliveryNode and der der Roboter arbeitet
     */
    private DeliveryNode home;

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
    Robot(int id, Node start, DeliveryNode home) {
        this.id = id;
        currentNode = start;
        inventoryMaterialType = 0;
        inventoryAmount = 0;
        graph = new ArrayList<>();
        Thread thread = new Thread(this);
        //thread.setDaemon(true);
        thread.start();
    }

    /**
     * Zum ueberpruefen ob der Roboter noch Nodes hat die er abarbeiten muss
     *
     * @return sind im graph mehr als 0 Nodes?
     */
    private boolean hasNextNode() {
        return graph.size() != 0;
    }

    private DeliveryNode getHomeNode() {
        return home;
    }

    private Node getCurrentNode() {
        return currentNode;
    }

    private Node getNextNode() {
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
                inventoryAmount = 1;
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
        /* Ist der Roboter an einer DeliveryNode?
         * Ja  : Hat er Items im Inventar?
         *       Ja  : Suche eine StorageNode an der abladen kann
         *       Nein: Suche eine StorageNode an der er die Waren bekommt die die Lieferung braucht
         * Nein: Fahre zur zugeordneten DeliveryNode
         */
        if (graph.size() == 0) {
            if (DeliveryNode.class.isAssignableFrom(getCurrentNode().getClass())) {
                if (inventoryMaterialType == 0) {
                    StorageNode destinationNode = Map.getMap().getStorageNode(inventoryMaterialType);
                    calculateAndSetRoute(getCurrentNode(), destinationNode);
                } else {
                    //TODO bei DeliveryNode aktuell benötigte Materialen prüfen und zu entsprechender StorageNode fahren
                }
            } else {
                calculateAndSetRoute(getCurrentNode(), getHomeNode());
            }
        } else {
            throw new RuntimeException("Graph hat noch Elemente und kann nicht geupdated werden");
        }
    }

    private void calculateAndSetRoute(Node start, Node destination) {
        ArrayList<Node> wayPointNodes = Map.getMap().wayPointNodes;

        //Wir ermittlen zuerst die WayPointNodes die direkt an der Start und Zielnode liegen
        Node startWayPointNode = null;
        Node destinationWayPointNode = null;
        for (Node n : start.getNeighbourNodes()) {
            if (n.getClass() == Node.class) {
                startWayPointNode = n;
            }
        }

        for (Node n : destination.getNeighbourNodes()) {
            if (n.getClass() == Node.class) {
                destinationWayPointNode = n;
            }
        }
        //Wenn eine der Nodes nicht entsprechend angeschlossen ist, wirft er einen Fehler
        if (destinationWayPointNode == null || startWayPointNode == null)
            throw new RuntimeException("StorageNode not connected to a waypoint node");


        ArrayList<Node> result = new ArrayList<>();
        result.add(startWayPointNode);
        result.add(destination);
        //Zuerst prüfen ob die WayPointNodes identisch sind, wenn ja lösung gefunden, return
        if (destinationWayPointNode.equals(startWayPointNode)) {
            graph = result;
            return;
        }
        //Oder vielleicht sind die beiden WayPointNodes benachbart, dann fügen wir beide hinzu, return
        result.add(1, destinationWayPointNode);
        if (startWayPointNode.isNeighbourNode(destinationWayPointNode)) {
            graph = result;
            return;
        }
        //andernfalls zurücksetzen
        result = new ArrayList<>();

        //Jetzt gehen wir von der startWayPointNode aus solange in beide richtungen bis wir auf die startWayPointNode treffen
        ArrayList<Node> wayPointStartNeighbours = new ArrayList<>();
        for (Node n : startWayPointNode.getNeighbourNodes()) {
            if (n.getClass() == Node.class) {
                wayPointStartNeighbours.add(n);
            }
        }

        ArrayList<Node> solutionOne = new ArrayList<>();

        Node next = wayPointStartNeighbours.get(0);
        solutionOne.add(wayPointStartNeighbours.get(0));
        while (next == null || !next.isNeighbourNode(destinationWayPointNode)) {
            ArrayList<Node> nextNeighbourNodes = next.getNeighbourNodes();

            ArrayList<Node> nextNeighbourNodesWay = new ArrayList<>();
            for (Node n : nextNeighbourNodes) {
                if (n.getClass() == Node.class) {
                    nextNeighbourNodes.add(n);
                }
            }
        }

        ArrayList<Node> solutionTwo = new ArrayList<>();

        next = wayPointStartNeighbours.get(1);
        solutionTwo.add(wayPointStartNeighbours.get(1));
        while (next == null || !next.isNeighbourNode(destinationWayPointNode)) {

        }


        result.add(startWayPointNode);
        result.addAll(solutionOne);
        result.add(destinationWayPointNode);
        result.add(destination);
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
                updateGraph();
            } else {
                // Andernfalls gibt es einen Fehler weil eine Endnode eine StorageNode sein muss
                throw new RuntimeException("Roboter hat keine weiteren Nodes, steht aber an einer Node an der er nicht verladen kann");
            }
        }
    }
}
