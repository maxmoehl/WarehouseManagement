import javax.swing.*;
import java.util.ArrayList;

class Map extends JComponent {

    private ArrayList<StorageNode> storageNodes;

    private ArrayList<DeliveryNode> deliveryNodes;

    private ArrayList<Node> wayPointNodes;

    private Map() {
        super();

        storageNodes = new ArrayList<>();
        deliveryNodes = new ArrayList<>();
        wayPointNodes = new ArrayList<>();

        storageNodes.add(new StorageNode(0));
        storageNodes.add(new StorageNode(2));
        storageNodes.add(new StorageNode(5));

        deliveryNodes.add(new DeliveryNode(4));
        deliveryNodes.add(new DeliveryNode(7));

        wayPointNodes.add(new Node(1));
        wayPointNodes.add(new Node(3));
        wayPointNodes.add(new Node(6));

        StorageNode sN = storageNodes.get(0);
        DeliveryNode dN = deliveryNodes.get(1);
        sN.setMaterialType(1);
        dN.setMaterialType(1);

        dN.setLoading(true);
        dN.loadItems(1, 100);
        dN.setLoading(false);
    }

    public static Map getMap() {
        return MapHolder.INSTANCE;
    }

    ArrayList<Node> getWayPointNodes() {
        return wayPointNodes;
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
