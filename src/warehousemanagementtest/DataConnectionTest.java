package warehousemanagementtest;

import org.junit.jupiter.api.Test;
import warehousemanagement.DataConnection;
import warehousemanagement.Shipment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataConnectionTest {

    @Test
    void getShipments() {
        List<Shipment> shipments = DataConnection.getDataConnection().getShipments();
        assertEquals(4, shipments.size());
        for (Shipment s : shipments) {
            assertNotNull(s);
        }
    }

    @Test
    void getNextShipment() {
        Shipment s = DataConnection.getDataConnection().getNextShipment();
        assertEquals(0, s.getId());
    }

    @Test
    void getShipment() {
        assertNull(DataConnection.getDataConnection().getShipment(0));
        assertNotNull(DataConnection.getDataConnection().getShipment(10));
    }

    @Test
    void isValidMaterialType() {
        assertTrue(DataConnection.getDataConnection().isValidMaterialType(1));
        assertFalse(DataConnection.getDataConnection().isValidMaterialType(0));
    }

    @Test
    void getMaterialType() {
        assertEquals("Holz", DataConnection.getDataConnection().getMaterialType(1));
    }

    @Test
    void getMaterialTypes() {
        String[] materialTypes = DataConnection.getDataConnection().getMaterialTypes();
        assertEquals(5, materialTypes.length);
    }
}