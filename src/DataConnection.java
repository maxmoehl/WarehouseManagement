import java.util.ArrayList;

/**
 * Diese Klasse simuliert die Verbindung zu einer Datenbank mit einfachen Listen und entsprechenden Gettern und Settern
 */
class DataConnection {

    private static ArrayList<String> materialTypes;

    private static ArrayList<Shipment> shipments;

    static void initData() {
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

    public static ArrayList<Shipment> getShipments() {
        return shipments;
    }

    static Shipment getNextShipment() {
        for (Shipment s : shipments) {
            if (s.getEta() >= Controller.getController().getTime() && !s.isArrived()) return s;
        }
        return null;
    }

    static boolean isValidMaterialType(int materialType) {
        return (materialType > 0 && materialType < materialTypes.size());
    }
}
