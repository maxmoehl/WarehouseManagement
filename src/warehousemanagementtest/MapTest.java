package warehousemanagementtest;

import org.junit.jupiter.api.Test;
import warehousemanagement.Map;
import warehousemanagement.navigation.DeliveryNode;
import warehousemanagement.navigation.Node;
import warehousemanagement.navigation.StorageNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void getStorageNode() {
        assertNotNull(Map.getMap().getStorageNode(1));
    }

    @Test
    void wayPointNodes() {
        List<Node> nodes = Map.getMap().wayPointNodes;
        assertEquals(5, nodes.size());

        assertTrue(nodes.get(0).getNeighbourNodes().contains(nodes.get(1)));
        assertTrue(nodes.get(3).getNeighbourNodes().contains(nodes.get(4)));
    }

    @Test
    void storageNodes() {
        List<StorageNode> storageNodes = Map.getMap().storageNodes;
        assertEquals(5, storageNodes.size());

        assertFalse(storageNodes.get(0).getNeighbourNodes().contains(storageNodes.get(1)));
        assertFalse(storageNodes.get(3).getNeighbourNodes().contains(storageNodes.get(4)));
    }

    @Test
    void deliveryNodes() {
        List<DeliveryNode> deliveryNodes = Map.getMap().deliveryNodes;
        assertEquals(3, deliveryNodes.size());

        assertFalse(deliveryNodes.get(0).getNeighbourNodes().contains(deliveryNodes.get(1)));
        assertFalse(deliveryNodes.get(1).getNeighbourNodes().contains(deliveryNodes.get(2)));
    }
}