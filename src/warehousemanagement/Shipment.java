package warehousemanagement;

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
     * Größe des Laderaums
     */
    private int size;

    /**
     * Die Art des Materials, dass im LKW transportiert wird
     */
    private int materialTypeInbound;

    /**
     * Gibt an ob die Lieferung auch wieder Waren aus dem Lager mitnimmt
     */
    private boolean outbound;

    /**
     * Falls die Lieferung auch Waren aus dem Lager mitnimmt wird hier der Warentyp definiert
     */
    private int materialTypeOutbound;

    /**
     * Wenn dieses Shipment an eine {@link warehousemanagement.navigation.DeliveryNode} gegeben wird, wird dieser Wert auf {@code true}
     * gesetzt damit erkenntlich ist, dass die Lieferung bereits abgefertigt wird
     */
    private boolean arrived;

    /**
     * Gibt an, von welchem Lieferant die Lieferung stammt
     */
    private String supplier;

    public Shipment(int id, int eta, int loadingTime, int size, int materialTypeInbound, boolean outbound, int materialTypeOutbound, String supplier) {
        this.id = id;
        this.eta = eta;
        this.loadingTime = loadingTime;
        this.size = size;
        this.materialTypeInbound = materialTypeInbound;
        this.outbound = outbound;
        this.materialTypeOutbound = materialTypeOutbound;
        this.supplier = supplier;
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

    public int getSize() {
        return size;
    }

    public int getMaterialTypeInbound() {
        return materialTypeInbound;
    }

    public boolean isOutbound() {
        return outbound;
    }

    public int getMaterialTypeOutbound() {
        return materialTypeOutbound;
    }

    public boolean isArrived() {
        return arrived;
    }

    public String getSupplier() {
        return supplier;
    }
}
