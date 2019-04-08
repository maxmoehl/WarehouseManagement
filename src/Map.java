import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Map extends JComponent {

    ArrayList<StorageNode> storageNodes;

    ArrayList<DeliveryNode> deliveryNodes;

    ArrayList<Node> wayPointNodes;

    private Map() {
   // public Map() {
        super();

        setSize(1000, 1000);

        storageNodes = new ArrayList<>();
        deliveryNodes = new ArrayList<>();
        wayPointNodes = new ArrayList<>();

        storageNodes.add(new StorageNode(0, 200, 100, 200, 100));
        storageNodes.add(new StorageNode(1, 200, 400, 200, 100 ));
        storageNodes.add(new StorageNode(2, 200, 700, 200, 100));

        deliveryNodes.add(new DeliveryNode(0, 800, 100, 75, 150));
        deliveryNodes.add(new DeliveryNode(1, 800, 400, 75, 150));
        deliveryNodes.add(new DeliveryNode(2, 800, 700, 75, 150));

        wayPointNodes.add(new Node(0));
        wayPointNodes.add(new Node(1));
        wayPointNodes.add(new Node(2));

        connectNodes(wayPointNodes.get(0), storageNodes.get(0));
        connectNodes(wayPointNodes.get(0), wayPointNodes.get(1));
        connectNodes(wayPointNodes.get(1), storageNodes.get(1));
        connectNodes(wayPointNodes.get(1), wayPointNodes.get(2));
        connectNodes(wayPointNodes.get(1), deliveryNodes.get(0));
        connectNodes(wayPointNodes.get(2), storageNodes.get(2));
        connectNodes(wayPointNodes.get(2), deliveryNodes.get(1));

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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(50, 950, 1000,1000 );
    }
    private static class MapHolder {
        private static Map INSTANCE = new Map();
    }
}
