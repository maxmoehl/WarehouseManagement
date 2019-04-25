package warehousemanagment;

import warehousemanagment.navigation.DeliveryNode;
import warehousemanagment.navigation.Node;
import warehousemanagment.navigation.StorageNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map extends JComponent {

    public List<StorageNode> storageNodes;

    public List<DeliveryNode> deliveryNodes;

    public List<Node> wayPointNodes;

    private Map() {
        super();
        setPreferredSize(new Dimension(1000, 1000));

        storageNodes = Collections.synchronizedList(new ArrayList<>());
        deliveryNodes = Collections.synchronizedList(new ArrayList<>());
        wayPointNodes = Collections.synchronizedList(new ArrayList<>());

        storageNodes.add(new StorageNode(0, 80, 120, 320, 160));
        storageNodes.add(new StorageNode(1, 80, 280, 320, 160));
        storageNodes.add(new StorageNode(2, 80, 440, 320, 160));
        storageNodes.add(new StorageNode(3, 80, 600, 320, 160));
        storageNodes.add(new StorageNode(4, 80, 760, 320, 160));

        deliveryNodes.add(new DeliveryNode(0, 650, 445, 150, 150));
        deliveryNodes.add(new DeliveryNode(1, 650, 605, 150, 150));
        deliveryNodes.add(new DeliveryNode(2, 650, 765, 150, 150));

        wayPointNodes.add(new Node(0, 450, 140, 20, 20));
        wayPointNodes.add(new Node(1, 450, 300, 20, 20));
        wayPointNodes.add(new Node(2, 450, 460, 20, 20));
        wayPointNodes.add(new Node(3, 450, 620, 20, 20));
        wayPointNodes.add(new Node(4, 450, 780, 20, 20));

        connectNodes(wayPointNodes.get(0), storageNodes.get(0));
        connectNodes(wayPointNodes.get(0), wayPointNodes.get(1));

        connectNodes(wayPointNodes.get(1), storageNodes.get(1));
        connectNodes(wayPointNodes.get(1), wayPointNodes.get(2));

        connectNodes(wayPointNodes.get(2), storageNodes.get(2));
        connectNodes(wayPointNodes.get(2), deliveryNodes.get(0));
        connectNodes(wayPointNodes.get(2), wayPointNodes.get(3));

        connectNodes(wayPointNodes.get(3), storageNodes.get(3));
        connectNodes(wayPointNodes.get(3), deliveryNodes.get(1));
        connectNodes(wayPointNodes.get(3), wayPointNodes.get(4));

        connectNodes(wayPointNodes.get(4), storageNodes.get(4));
        connectNodes(wayPointNodes.get(4), deliveryNodes.get(2));

        StorageNode sN = storageNodes.get(0);
        DeliveryNode dN = deliveryNodes.get(1);
        sN.setMaterialType(1);
        dN.setMaterialType(1);

        dN.setLoading(true);
        dN.loadItems(1, 100);
        dN.setLoading(false);

        for (StorageNode n : storageNodes) {
            add(n);
        }

        for (DeliveryNode n : deliveryNodes) {
            add(n);
        }

        for (Node n : wayPointNodes) {
            add(n);
        }
    }

    public static Map getMap() {
        return MapHolder.INSTANCE;
    }

    private void connectNodes(Node n1, Node n2) {
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);
    }

    public StorageNode getStorageNode(int materialType) {
        for (StorageNode n : storageNodes) {
            if (n.getMaterialType() == materialType) {
                return n;
            }
        }
        throw new RuntimeException("Keine Lagereinheit mit benötigtem Material-Typ gefunden");
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(35, 75, 690, 890);
        g.setColor(Color.GRAY);
        g.fillRect(40, 80, 680, 880);

    }

    private static class MapHolder {
        private static Map INSTANCE = new Map();
    }
}
