package warehousemanagment.navigation;

import warehousemanagment.Map;

import java.util.ArrayList;
import java.util.List;

public class Robot implements Runnable {

    /**
     * Eindeutige id zum vergleichen mit anderen Robotern
     */
    private final int id;

    /** Die Nodes die der Roboter als naechstes abfahren muss (von 0 beginnend) */
    private ArrayList<Node> graph;

    /** Die {@link DeliveryNode} fuer die der Roboter arbeitet */
    private DeliveryNode home;

    /** Die aktuelle {@link Node} auf der der Roboter sich befindet */
    private Node currentNode;

    /** Speichert was der Roboter aktuell in seinem Inventar hat, 0 = nichts, groeßer 0 = irgendein Warentyp */
    private int inventoryMaterialType;
    private int inventoryAmount;

    /**
     * Initialisiert einen neuen Roboter an der gegebenen warehousemanagment.navigation.Node. Jeder Roboter hat einen eigenen Thread und arbeitet deswegen voellig
     * unabhaegig. Die Threads sind {@code isDaemmon() = true} und werden terminiert wenn der main-Thread terminiert wird.
     *
     * @param id    eindeutige id
     * @param start warehousemanagment.navigation.Node an der der Roboter initalisiert wird
     */
    public Robot(int id, Node start, DeliveryNode home) {
        this.id = id;
        graph = new ArrayList<>();
        this.home = home;
        currentNode = start;
        inventoryMaterialType = 0;
        inventoryAmount = 0;
        Thread thread = new Thread(this);
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
     * Bewegt den Roboter zur naechste {@link Node} die auf dem Graph gegeben ist, der Vorgang sperrt den Roboter für eine Sekunde
     *
     * @see Robot#lock
     */
    private void move() {
        lock(1000);
        currentNode = getNextNode();
        graph.remove(0);
    }

    /**
     * Greift auf die {@link Robot#currentNode} zu und laedt ein oder aus beziehungsweise wartet wenn die Node aktuell belegt ist,
     * muss auf einer {@link StorageNode} ausgefuehrt werden
     * <ul>
     * <li>Wenn der Roboter Ladung in seinem Inventar hat versucht seine Waren in die {@link StorageNode} abzuladen</li>
     * <li>Wenn der Roboter keine Ladung hat versucht er Waren aus der {@link StorageNode} zu laden</li>
     * </ul>
     * Der Vorgang dauert eine Sekunde und sperrt die {@link Robot#currentNode}
     *
     * @see Robot#lock
     * @see StorageNode#loadItems
     * @see StorageNode#unloadItems
     */
    private void load() {
        //Lock access to this node and lock it for one second then unload/load items
        StorageNode current = (StorageNode) getCurrentNode();


        while (!current.accessNode(this)) {
            try {
                wait();
            } catch (InterruptedException e) {
                assert true;
            }
        }

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
        current.leaveNode();
    }

    /**
     * Ueberprueft ob der Graph erneuert werden soll und erneuert diesen wenn noetig
     */
    private void updateGraph() {
        /* Ist der Roboter an einer warehousemanagment.navigation.DeliveryNode?
         * Ja  : Hat er Items im Inventar?
         *       Ja  : Suche eine warehousemanagment.navigation.StorageNode an der abladen kann
         *       Nein: Suche eine warehousemanagment.navigation.StorageNode an der er die Waren bekommt die die Lieferung braucht
         * Nein: Fahre zur zugeordneten warehousemanagment.navigation.DeliveryNode
         */
        if (graph.size() == 0) {
            if (DeliveryNode.class.isAssignableFrom(getCurrentNode().getClass())) {
                if (inventoryMaterialType != 0) {
                    StorageNode destinationNode = Map.getMap().getStorageNode(inventoryMaterialType);
                    calculateAndSetRoute(getCurrentNode(), destinationNode);
                } else {
                    //TODO bei warehousemanagment.navigation.DeliveryNode aktuell benötigte Materialen prüfen und zu entsprechender warehousemanagment.navigation.StorageNode fahren
                }
            } else {
                calculateAndSetRoute(getCurrentNode(), getHomeNode());
            }
        } else {
            throw new RuntimeException("Graph hat noch Elemente und kann nicht geupdated werden");
        }
    }

    /**
     * <i><b>Under Construction</b></i>
     * @param start Start{@link Node}
     * @param destination Ziel{@link Node}
     */
    private void calculateAndSetRoute(Node start, Node destination) {
        List<Node> wayPointNodes = Map.getMap().wayPointNodes;

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
            throw new RuntimeException("warehousemanagment.navigation.StorageNode not connected to a waypoint node");


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

            //TODO sicherstellen das das ergebnis nicht ist, dass der Roboter auf ewig zwischen zwei Nodes pendelt
            ArrayList<Node> nextNeighbourNodesWay = new ArrayList<>();
            for (int i = 0; i < nextNeighbourNodes.size(); i++) {
                if (nextNeighbourNodes.get(i).getClass() == Node.class) {
                    nextNeighbourNodesWay.add(nextNeighbourNodes.get(i));
                }
            }
        }

        if (wayPointStartNeighbours.size() > 1) {
            //TODO zweit option (in die entgegengesetzte richtung) implementieren
            ArrayList<Node> solutionTwo = new ArrayList<>();

            next = wayPointStartNeighbours.get(1);
            solutionTwo.add(wayPointStartNeighbours.get(1));
            while (next == null || !next.isNeighbourNode(destinationWayPointNode)) {

            }
        }


        result.add(startWayPointNode);
        result.addAll(solutionOne);
        result.add(destinationWayPointNode);
        result.add(destination);

        graph = result;
    }

    /**
     * Pausiert den Thread für eine gewisse Zeit, definiert in {@code timeout} und sperrt Ressourcen falls die Methode
     * in einem synchronized Block ausgefuehrt wird
     *
     * @param timeout Zeit in Millisekunden
     *
     * @throws RuntimeException wenn der Roboter waehrend er wartet benachrichtigt wird
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
     * <li>Wenn weitere Nodes Nodes vorhanden ist bewegt sich der Roboter eine warehousemanagment.navigation.Node weiter, indem er {@link Robot#move}</li>
     * <li>
     * Andernfalls wird ueberprueft ob der Roboter an dieser {@link Node} umladen kann
     * <ul>
     * <li>Ja: Umladen mit {@link Robot#load} und den Graphen erneuern {@link Robot#updateGraph}</li>
     * <li>Nein: RuntimeException werfen</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @throws RuntimeException wenn Roboter an einer {@link Node} steht die keine {@link StorageNode} ist und keinen Graphen mehr hat
     */
    private void work() {
        // Is eine weitere warehousemanagment.navigation.Node vorhanden?
        if (hasNextNode()) {
            move();
            //TODO remove
            System.out.println("moved");
        } else {
            // Prüfen ob es eine warehousemanagment.navigation.StorageNode ist oder ein Objekt das von warehousemanagment.navigation.StorageNode erbt (warehousemanagment.navigation.DeliveryNode)
            if (StorageNode.class.isAssignableFrom(getCurrentNode().getClass())) {
                // umladen (wie auch immer)
                load();
                //TODO remove
                System.out.println("loaded");
                updateGraph();
            } else {
                // Andernfalls gibt es einen Fehler weil eine Endnode eine warehousemanagment.navigation.StorageNode sein muss
                throw new RuntimeException("Roboter hat keine weiteren Nodes, steht aber an einer warehousemanagment.navigation.Node an der er nicht verladen kann");
            }
        }
    }
}
