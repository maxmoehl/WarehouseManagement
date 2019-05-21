package warehousemanagementtest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import warehousemanagement.Shipment;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    static Shipment shipmentOne;
    static Shipment shipmentTwo;

    @BeforeAll
    static void setUp() {
        shipmentOne = new Shipment(0, 150, 50, 10, 2, true, 1, "SupplierOne");
        shipmentTwo = new Shipment(1, 250, 30, 10, 1, false, 0, "SupplierTwo");
    }

    @Test
    void getId() {
        assertEquals(0, shipmentOne.getId());
        assertEquals(1, shipmentTwo.getId());
    }

    @Test
    void getEta() {
        assertEquals(150, shipmentOne.getEta());
        assertEquals(250, shipmentTwo.getEta());
    }

    @Test
    void getLoadingTime() {
        assertEquals(50, shipmentOne.getLoadingTime());
        assertEquals(30, shipmentTwo.getLoadingTime());
    }

    @Test
    void getSize() {
        assertEquals(10, shipmentOne.getSize());
        assertEquals(10, shipmentTwo.getSize());
    }

    @Test
    void getMaterialTypeInbound() {
        assertEquals(2, shipmentOne.getMaterialTypeInbound());
        assertEquals(1, shipmentTwo.getMaterialTypeInbound());
    }

    @Test
    void isOutbound() {
        assertTrue(shipmentOne.isOutbound());
        assertFalse(shipmentTwo.isOutbound());
    }

    @Test
    void getMaterialTypeOutbound() {
        assertEquals(1, shipmentOne.getMaterialTypeOutbound());
        assertEquals(0, shipmentTwo.getMaterialTypeOutbound());
    }

    @Test
    void isArrived() {
        assertFalse(shipmentOne.isArrived());
        assertFalse(shipmentTwo.isArrived());
    }

    @Test
    void getSupplier() {
        assertEquals("SupplierOne", shipmentOne.getSupplier());
        assertEquals("SupplierTwo", shipmentTwo.getSupplier());
    }
}