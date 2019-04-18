package warehousemanagment;

/**
 * Repräsentiert eine Lieferung die per LKW kommt
 */
public class Shipment {

    /**
     * Identifier
     */
    private int id;

    /**
     * Der Zeitpunkt zu dem die Lieferung am lager ankommt, die Zeit startet beim Start des Programmes bei null und wird in Sekuden gemessen
     */
    private int eta;

    /**
     * Die Zeit in Sekunden die zur Verfügung stehen um den LKW zu entladen
     */
    private int loadingTime;

    /**
     * Die Anzahl der Einheiten die der LKW geladen hat
     */
    private int amount;

    /**
     * Die Art des Materials, dass im LKW transportiert wird, eine Liste die den Integern einen Warentyp zuordnet
     */
    private int materialType;

    /**
     * Wenn dieses Shipment an eine {@link warehousemanagment.navigation.DeliveryNode} gegeben wird, wird dieser Wert auf {@code true}
     * gesetzt damit erkenntlich ist, dass die Lieferung bereits abgefertigt wird
     */
    private boolean arrived;

    Shipment(int id, int eta, int loadingTime, int amount, int materialType) {
        this.id = id;
        this.eta = eta;
        this.loadingTime = loadingTime;
        this.amount = amount;
        this.materialType = materialType;
        arrived = false;
    }

    public int getId() {
        return id;
    }

    public int getEta() {
        return eta;
    }

    public int getLoadingTime() {
        return loadingTime;
    }

    public int getAmount() {
        return amount;
    }

    public int getMaterialType() {
        return materialType;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void arrive() {
        arrived = true;
    }
}
