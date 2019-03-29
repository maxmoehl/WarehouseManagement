import java.util.ArrayList;

/**
 * Diese Klasse simuliert die Verbindung zu einer Datenbank mit einfachen Listen und entsrprechenden Gettern und Settern
 */
class DataConnection {
    ArrayList<Shipment> shipments;

    DataConnection() {
        shipments = new ArrayList<>();
        shipments.add(new Shipment(0, 100, 180, 10, 0));
    }
}
