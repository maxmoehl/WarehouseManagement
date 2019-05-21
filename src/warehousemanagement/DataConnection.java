package warehousemanagement;

import java.util.ArrayList;

/**
 * Diese Klasse simuliert die Verbindung zu einer Datenbank mit einfachen Listen und entsprechenden Gettern und Settern
 */
public class DataConnection {

    /**
     * Speichert alle gueltigen Materialtypen
     */
    private final ArrayList<String> materialTypes;

    /**
     * Speichert alle {@link Shipment} die im laufe der Zeit anfallen werden
     */
    private final ArrayList<Shipment> shipments;

    private DataConnection() {
        materialTypes = new ArrayList<>();
        materialTypes.add("Leer");
        materialTypes.add("Holz");
        materialTypes.add("Steine");
        materialTypes.add("Nahrung");
        materialTypes.add("Gold");

        shipments = new ArrayList<>();
        shipments.add(new Shipment(0, 10, 180, 10, 1, false, 0, "International Deliveries"));
        shipments.add(new Shipment(1, 20, 180, 10, 3, false, 0, "We make you move"));
        shipments.add(new Shipment(2, 25, 180, 10, 1, false, 0, "International Deliveries"));
        shipments.add(new Shipment(3, 100, 180, 10, 4, true, 1, "We move everything"));
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

    public Shipment getShipment(int time) {
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getEta() == time) {
                return shipments.get(i);
            }
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

    public String[] getMaterialTypes() {
        String[] arr = new String[materialTypes.size()];
        for (int i = 0; i < materialTypes.size(); i++) {
            arr[i] = materialTypes.get(i);
        }
        return arr;
    }

    private static class DataConnectionHolder {
        private static final DataConnection INSTANCE = new DataConnection();
    }
}
