import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Map extends JComponent {

    ArrayList<StorageNode> storageNodes;

    ArrayList<DeliveryNode> deliveryNodes;

    ArrayList<Node> wayPointNodes;

    private Map() {
        super();
        setPreferredSize(new Dimension(1000, 1000));

        storageNodes = new ArrayList<>();
        deliveryNodes = new ArrayList<>();
        wayPointNodes = new ArrayList<>();

        storageNodes.add(new StorageNode(0, 80, 120, 320, 160));
        storageNodes.add(new StorageNode(1, 80, 280, 320, 160));
        storageNodes.add(new StorageNode(2, 80, 440, 320, 160));
        storageNodes.add(new StorageNode(3, 80, 600, 320, 160));
        storageNodes.add(new StorageNode(4, 80, 760, 320, 160));

        deliveryNodes.add(new DeliveryNode(0, 800, 100, 75, 150));
        deliveryNodes.add(new DeliveryNode(1, 800, 400, 75, 150));
        deliveryNodes.add(new DeliveryNode(2, 800, 700, 75, 150));

        wayPointNodes.add(new Node(0));
        wayPointNodes.add(new Node(1));
        wayPointNodes.add(new Node(2));
        wayPointNodes.add(new Node(3));
        wayPointNodes.add(new Node(4));

        connectNodes(wayPointNodes.get(0), storageNodes.get(0));
        connectNodes(wayPointNodes.get(0), wayPointNodes.get(1));

        connectNodes(wayPointNodes.get(1), storageNodes.get(1));
        connectNodes(wayPointNodes.get(1), wayPointNodes.get(2));

        connectNodes(wayPointNodes.get(2), storageNodes.get(2));
        connectNodes(wayPointNodes.get(2), wayPointNodes.get(3));

        connectNodes(wayPointNodes.get(3), storageNodes.get(3));
        connectNodes(wayPointNodes.get(3), deliveryNodes.get(0));
        connectNodes(wayPointNodes.get(3), wayPointNodes.get(4));

        connectNodes(wayPointNodes.get(4), storageNodes.get(4));
        connectNodes(wayPointNodes.get(4), deliveryNodes.get(1));

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
        throw new RuntimeException("Keine Lagereinheit mit benötigtem Material-Typ gefunden");
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.setColor(Color.GRAY);
        g.fillRect(40, 80, 680, 880);

    }

    @Override
    public void paintChildren(Graphics g) {
        for (StorageNode n : storageNodes) {
            g.setClip(n.getX(), n.getY(), n.getWidth(), n.getHeight());
            n.paint(g);
        }
        for (DeliveryNode n : deliveryNodes) {
            n.paint(g);
        }
    }

    private static class MapHolder {
        private static Map INSTANCE = new Map();
    }
}
