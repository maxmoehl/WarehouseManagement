package warehousemanagementtest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import warehousemanagement.Shipment;
import warehousemanagement.navigation.DeliveryNode;
import warehousemanagement.navigation.Node;
import warehousemanagement.navigation.StorageNode;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    static Node nodeOne;
    static Node nodeTwo;

    static StorageNode storageNode;

    static DeliveryNode deliveryNode;

    static Shipment shipment;

    @BeforeAll
    static void setUp() {
        nodeOne = new Node(0, 0, 0, 0, 0);
        nodeTwo = new Node(1, 0, 0, 0, 0);

        storageNode = new StorageNode(2, 0, 0, 0, 0);

        storageNode.setMaterialType(1);

        deliveryNode = new DeliveryNode(3, 0, 0, 0, 0);

        shipment = new Shipment(0, 150, 50, 10, 2, true, 1, "SupplierOne");
    }

    /*
     * NODE
     */

    @Test
    void addNeighbour() {
        nodeOne.addNeighbour(nodeTwo);
        assertTrue(nodeOne.getNeighbourNodes().contains(nodeTwo));
    }

    @Test
    void getNeighbourNodes() {
        assertEquals(0, nodeTwo.getNeighbourNodes().size());
    }

    @Test
    void getId() {
        assertEquals(0, nodeOne.getId());
        assertEquals(1, nodeTwo.getId());
    }

    /*
     * STORAGE-NODE
     */

    @Test
    void getMaterialType() {
        assertEquals(1, storageNode.getMaterialType());
    }

    @Test
    void setMaterialType() {
        storageNode.setMaterialType(2);
        assertEquals(2, storageNode.getMaterialType());
        storageNode.setMaterialType(1);
    }

    @Test
    void resetMaterialType() {
        storageNode.resetMaterialType();
        assertEquals(0, storageNode.getMaterialType());
        storageNode.setMaterialType(1);
    }

    @Test
    void getAmount() {
        assertEquals(0, storageNode.getAmount());
    }

    @Test
    void un_loadItems() {
        assertDoesNotThrow(() -> storageNode.loadItems(1, 10));
        assertEquals(10, storageNode.getAmount());
        assertThrows(RuntimeException.class, () -> storageNode.unloadItems(11));
        assertDoesNotThrow(() -> storageNode.unloadItems(10));
        assertEquals(0, storageNode.getAmount());

        assertThrows(RuntimeException.class, () -> storageNode.loadItems(2, 10));
        assertThrows(RuntimeException.class, () -> storageNode.unloadItems(10));
    }

    /*
     * DELIVERY-NODE
     */

    @Test
    void shipmentLogic() {
        //loadShipment(Shipment)
        deliveryNode.loadShipment(shipment);
        assertTrue(deliveryNode.isUnloading());
        assertEquals(deliveryNode.getMaterialType(), shipment.getMaterialTypeInbound());

        //unloadItems(int)
        assertThrows(RuntimeException.class, () -> deliveryNode.loadItems(1, 10));
        assertDoesNotThrow(() -> deliveryNode.unloadItems(shipment.getSize()));

        //check if switching from loading to unloading works
        assertTrue(deliveryNode.isLoading());
        assertEquals(0, deliveryNode.getAmount());
        assertEquals(shipment.getMaterialTypeOutbound(), deliveryNode.getMaterialType());
        assertThrows(RuntimeException.class, () -> deliveryNode.unloadItems(10));

        //check if removing shipment after it's done works
        assertDoesNotThrow(() -> deliveryNode.loadItems(shipment.getMaterialTypeOutbound(), shipment.getSize()));
        assertEquals(0, deliveryNode.getMaterialType());
    }
}