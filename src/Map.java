import javax.swing.*;
import java.util.ArrayList;

class Map extends JComponent {

    ArrayList<StorageNode> storageNodes;

    ArrayList<DeliveryNode> deliveryNodes;

    ArrayList<Node> wayPointNodes;

    private Map() {
        super();

        storageNodes = new ArrayList<>();
        deliveryNodes = new ArrayList<>();
        wayPointNodes = new ArrayList<>();

        storageNodes.add(new StorageNode(0));
        storageNodes.add(new StorageNode(1));
        storageNodes.add(new StorageNode(2));

        deliveryNodes.add(new DeliveryNode(0));
        deliveryNodes.add(new DeliveryNode(1));

        wayPointNodes.add(new Node(0));
        wayPointNodes.add(new Node(1));
        wayPointNodes.add(new Node(2));

        connectNodes(wayPointNodes.get(0), storageNodes.get(0));
        connectNodes(wayPointNodes.get(0), wayPointNodes.get(1));
        connectNodes(wayPointNodes.get(1), storageNodes.get(1));
        connectNodes(wayPointNodes.get(1), wayPointNodes.get(2));
        connectNodes(wayPointNodes.get(1), deliveryNodes.get(0));
        connectNodes(wayPointNodes.get(2), storageNodes.get(2));
        connectNodes(wayPointNodes.get(2), storageNodes.get(1));

        StorageNode sN = storageNodes.get(0);
        DeliveryNode dN = deliveryNodes.get(1);
        sN.setMaterialType(1);
        dN.setMaterialType(1);

        dN.setLoading(true);
        dN.loadItems(1, 100);
        dN.setLoading(false);
    }

    static Map getMap() {
        return MapHolder.INSTANCE;
    }

    private void connectNodes(Node n1, Node n2) {
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);
    }

    StorageNode getStorageNode(int materialType) {
        for (StorageNode n : storageNodes) {
            if (n.getMaterialType() == materialType) {
                return n;
            }
        }
        throw new RuntimeException("Keine Lagereinheit mit benötitem Material-Typ gefunden");
    }

    private static class MapHolder {
        private static Map INSTANCE = new Map();
    }
}
