package warehousemanagement.navigation;

import warehousemanagement.DataConnection;
import warehousemanagement.Map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Ein Roboter ist eine eigenständige Einheit, die Waren zwischen den {@link Node}s transportiert.
 */
class Robot implements Runnable {

    /**
     * Die {@link Node}s die der Roboter als nächstes abfahren muss (von 0 beginnend)
     */
    private ArrayList<Node> graph;

    /**
     * Die {@link DeliveryNode} für die der Roboter arbeitet
     */
    private DeliveryNode home;

    /**
     * Die {@link Node} auf der der Roboter sich aktuell befindet
     */
    private Node currentNode;

    /**
     * Speichert was der Roboter aktuell in seinem Inventar hat: 0 = nichts, größer 0 = irgendein Warentyp
     *
     * @see DataConnection#getMaterialTypes
     */
    private int inventoryMaterialType;

    /**
     * Speichert die Menge an Materialien die der Roboter im Inventar hat
     */
    private int inventoryAmount;

    /**
     * Kontroll-Flag um den Roboter als abzuschalten zu markieren
     */
    private boolean terminated;

    /**
     * Initialisiert einen neuen Roboter an der gegebenen {@link DeliveryNode}. Jeder Roboter hat einen eigenen Thread und arbeitet deswegen völlig
     * unabhängig. Die Threads sind {@code isDaemon() = true} und werden terminiert wenn der main-Thread terminiert wird.
     *
     * @param home  {@link DeliveryNode} an der der Roboter arbeitet
     */
    Robot(DeliveryNode home) {
        graph = new ArrayList<>();
        this.home = home;
        currentNode = home;
        inventoryMaterialType = 0;
        inventoryAmount = 0;
        terminated = false;
        Thread thread = new Thread(this, "Roboter");
        thread.start();
    }

    /**
     * Zum überpruefen ob der Roboter noch {@link Node}s hat die er abarbeiten muss
     *
     * @return ob der Roboter noch {@link Node}s hat die er abarbeiten muss
     */
    private boolean hasNextNode() {
        return graph.size() != 0;
    }

    /**
     * Gibt die {@link DeliveryNode} zurück für die der Roboter arbeitet
     *
     * @return die {@link DeliveryNode} für die der Roboter arbeitet
     */
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
     * Bewegt den Roboter zur nächsten {@link Node} die auf dem Graph gegeben ist, der Vorgang sperrt den Roboter für eine Sekunde.
     *
     * @see Robot#lock
     */
    private void move() {
        lock(500);
        currentNode.unregister();
        currentNode = getNextNode();
        currentNode.register();
        graph.remove(0);
    }

    /**
     * Greift auf die {@link Robot#currentNode} zu und lädt ein oder aus beziehungsweise wartet wenn die Node aktuell belegt ist.<br>
     * Muss auf einer {@link StorageNode} ausgefuehrt werden
     * <ul>
     * <li>Wenn der Roboter Ladung in seinem Inventar hat, versucht er seine Waren in die {@link StorageNode} abzuladen</li>
     * <li>Wenn der Roboter keine Ladung hat versucht er Waren aus der {@link StorageNode} zu laden</li>
     * </ul>
     * Der Vorgang dauert eine Sekunde und sperrt die {@link Robot#currentNode}
     *
     * @see Robot#lock
     * @see StorageNode#loadItems
     * @see StorageNode#unloadItems
     */
    private void load() {
        /*
         * Versuchen auf die Node zuzugreifen und wenn sie belegt ist, warten bis man benachrichtigt wird und wieder
         * versuchen darauf zuzugreifen
         */
        StorageNode current = (StorageNode) getCurrentNode();
        while (!current.accessNode(this)) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        lock(1000);

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
     * Damit der Roboter sich von einer Node zu einer anderen bewegen kann bietet diese Methode
     * eine Implementierung des Dijkstra-Algorithmuses zur Berechnung des kürzesten Weges von
     * der {@link Robot#currentNode} zur {@code destination} Node.<br>
     * Nach Berechnung wird das Ergebnis in {@link Robot#graph} gespeichert.
     *
     * @param destination Die Node zu der der Roboter sich bewegen will
     * @see Robot#getLowestDistanceNode
     * @see Robot#calculateMinimumDistance
     */
    private void navigateTo(Node destination) {
        Map m = Map.getMap();
        synchronized (m.nodeLock) {
            Node source = getCurrentNode();

            //Zuerst erzeugen wir ein Set mit allen Nodes
            Set<Node> nodes = new HashSet<>();
            nodes.addAll(m.wayPointNodes);
            nodes.addAll(m.storageNodes);
            nodes.addAll(m.deliveryNodes);

            //der abstand zum Ziel ist immer null
            source.distance = 0;

            //jetzt haben wir nodes die fertig sind
            Set<Node> settledNodes = new HashSet<>();
            //und nodes die noch gemacht werden müssen
            Set<Node> unsettledNodes = new HashSet<>();

            //als erstes kümmern wir uns um die startnode
            unsettledNodes.add(source);

            //solange wir Nodes haben die gemacht werden müssen arbeiten wir
            while (unsettledNodes.size() != 0) {
                //die aktuelle Node ist die nähste in den unsettled nodes
                Node currentNode = getLowestDistanceNode(unsettledNodes);
                //wir bearbeiten sie jetzt also können wir sie entferenen
                unsettledNodes.remove(currentNode);
                //jetzt gehen wir alle benachbarten Nodes durch
                for (int i = 0; i < currentNode.getNeighbourNodes().size(); i++) {
                    Node n = currentNode.getNeighbourNodes().get(i);
                    //wenn sie nicht schon bearbeitet wurden
                    if (!settledNodes.contains(n)) {
                        //errechnen wir die minimale distanz
                        calculateMinimumDistance(n, currentNode);
                        //und fügen sie den unfertigen hinzu
                        unsettledNodes.add(n);
                    }
                }
                //jetzt ist die currentNode abgearbeitet und wir können mit der nächsten fortfahren
                settledNodes.add(currentNode);
            }

            this.graph = destination.shortestPath;
            graph.add(destination);

            //Alle Werte zurücksetzen
            for (Node n : nodes) {
                n.distance = Integer.MAX_VALUE;
                n.shortestPath = new ArrayList<>();
            }
        }
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.distance;
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode, Node sourceNode) {
        int sourceDistance = sourceNode.distance;
        if (sourceDistance + 1 < evaluationNode.distance) {
            evaluationNode.distance = sourceDistance + 1;
            ArrayList<Node> shortestPath = new ArrayList<>(sourceNode.shortestPath);
            shortestPath.add(sourceNode);
            evaluationNode.shortestPath = shortestPath;
        }
    }

    /**
     * Pausiert den Thread für eine gewisse Zeit, definiert in {@code timeout} und sperrt Ressourcen falls die Methode
     * in einem synchronized Block ausgeführt wird
     *
     * @param timeout Zeit in Millisekunden
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
        while (!(terminated && inventoryMaterialType == 0)) {
            work();
        }
        currentNode.unregister();
    }

    /**
     * Führt einen kompletten Arbeitsdurchlauf aus
     * <ul>
     * <li>Wenn mindestens eine weitere {@link Node} vorhanden ist bewegt sich der Roboter eine {@link Node} weiter</li>
     * <li>Andernfalls wird überprüft ob der Roboter an dieser {@link Node} umladen kann
     * <ul>
     * <li>Ja: Umladen und den Graphen erneuern</li>
     * <li>Nein: RuntimeException werfen</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @throws RuntimeException wenn der Roboter an einer {@link Node} steht die keine {@link StorageNode} ist und keinen Graphen mehr hat
     * @see Robot#move
     * @see Robot#load
     * @see Robot#navigateTo
     */
    private void work() {
        if (hasNextNode()) {
            move();
        } else {
            Map m = Map.getMap();
            if (inventoryMaterialType == 0) {
                if (DeliveryNode.class.isAssignableFrom(getCurrentNode().getClass())) {
                    if (getHomeNode().isUnloading()) {
                        //Waren ins Inventar laden
                        load();
                    } else {
                        //Zu Lager mit richtigen Waren für Home navigieren
                        navigateTo(m.getStorageNode(getHomeNode().getMaterialType()));
                    }
                } else {
                    if (((StorageNode) getCurrentNode()).getMaterialType() == getHomeNode().getMaterialType() && getHomeNode().isLoading()) {
                        //Waren aus Lager in Inventar laden
                        load();
                    } else {
                        if (getHomeNode().isLoading()) {
                            //Zu Lager mit richtigem Materialtyp für Home navigieren
                            navigateTo(m.getStorageNode(getHomeNode().getMaterialType()));
                        } else {
                            //Zur HomeNode navigieren
                            navigateTo(getHomeNode());
                        }
                    }
                }
            } else {
                if (DeliveryNode.class.isAssignableFrom(getCurrentNode().getClass())) {
                    if (getHomeNode().isLoading() && getHomeNode().getMaterialType() == inventoryMaterialType) {
                        //Waren aus Inventar in homeNode laden
                        load();
                    } else {
                        //Zu Lager mit Materialtyp aus Inventar navigieren
                        navigateTo(m.getStorageNode(inventoryMaterialType));
                    }
                } else {
                    if (((StorageNode) getCurrentNode()).getMaterialType() == getHomeNode().getMaterialType() && getHomeNode().isLoading()) {
                        //Zur HomeNode navigieren
                        navigateTo(getHomeNode());
                    } else {
                        if (((StorageNode) getCurrentNode()).getMaterialType() == inventoryMaterialType) {
                            //Waren ins Lager laden
                            load();
                        } else {
                            //Zu passendem Lager für inventarMaterial navigieren
                            navigateTo(m.getStorageNode(inventoryMaterialType));
                        }
                    }
                }
            }
        }
    }

    /**
     * Wenn ein Roboter aus dem System entfernt wird, muss diese Methode aufgerufen werden. Sie setzt ein Flag und das nächste mal wenn der Roboter
     * ein leeres Inventar hat, entfernt er sich selbst aus dem Programm.
     */
    void shutdown() {
        terminated = true;
    }

    @Override
    public String toString() {
        return "Robot at Node: " + getCurrentNode().toString() + " with homeNode: " + getHomeNode().toString();
    }
}
