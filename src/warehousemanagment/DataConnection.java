package warehousemanagment;

import java.util.ArrayList;

/**
 * Diese Klasse simuliert die Verbindung zu einer Datenbank mit einfachen Listen und entsprechenden Gettern und Settern
 */
public class DataConnection {

    private ArrayList<String> materialTypes;

    private ArrayList<Shipment> shipments;

    private DataConnection() {
        materialTypes = new ArrayList<>();
        materialTypes.add("Leer");
        materialTypes.add("Holz");
        materialTypes.add("Steine");
        materialTypes.add("Nahrung");
        materialTypes.add("Gold");

        shipments = new ArrayList<>();
        shipments.add(new Shipment(0, 100, 180, 10, 1));
        shipments.add(new Shipment(0, 150, 180, 10, 3));
        shipments.add(new Shipment(0, 210, 180, 10, 2));
        shipments.add(new Shipment(0, 250, 180, 10, 4));
    }

    public static DataConnection getDataConnection() {
        return DataConnectionHolder.INSTANCE;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }

    public Shipment getNextShipment() {
        for (Shipment s : shipments) {
            if (s.getEta() >= Controller.getController().getTime() && !s.isArrived()) return s;
        }
        return null;
    }

    public boolean isValidMaterialType(int materialType) {
        return (materialType > 0 && materialType < materialTypes.size());
    }

    public String getMaterialType(int materialType) {
        return materialTypes.get(materialType);
    }

    private static class DataConnectionHolder {
        private static DataConnection INSTANCE = new DataConnection();
    }
}
