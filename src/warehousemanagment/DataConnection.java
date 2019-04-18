package warehousemanagment;

import java.util.ArrayList;

/**
 * Diese Klasse simuliert die Verbindung zu einer Datenbank mit einfachen Listen und entsprechenden Gettern und Settern
 */
public class DataConnection {

    /**
     * Speichert alle gueltigen Materialtypen
     */
    private ArrayList<String> materialTypes;

    /**
     * Speichert alle {@link Shipment} die im laufe der Zeit anfallen werden
     */
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

    /**
     * Gibt das naechste Shipment zurueck, dass nach der akutellen Uhrzeit ankommt
     *
     * @return naechstes, faelliges Shipment
     */
    public Shipment getNextShipment() {
        for (Shipment s : shipments) {
            if (s.getEta() >= Controller.getController().getTime() && !s.isArrived()) return s;
        }
        return null;
    }

    public boolean isValidMaterialType(int materialType) {
        return (materialType > 0 && materialType < materialTypes.size());
    }

    /**
     * Konvertiert einen Integer in einen String, dient zum anzeigen in diversen Frames
     *
     * @param materialType id des Materialtyps
     * @return String der den Namen des Materialtyps enthaelt
     */
    public String getMaterialType(int materialType) {
        return materialTypes.get(materialType);
    }

    private static class DataConnectionHolder {
        private static DataConnection INSTANCE = new DataConnection();
    }
}
