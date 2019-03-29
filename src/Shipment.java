/**
 * Repräsentiert eine Lieferung die per LKW kommt
 */
public class Shipment {

    /**
     * Identifier (wird eventuell noch entfernt)
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

    Shipment(int id, int eta, int loadingTime, int amount, int materialType) {
        this.id = id;
        this.eta = eta;
        this.loadingTime = loadingTime;
        this.amount = amount;
        this.materialType = materialType;
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
}
