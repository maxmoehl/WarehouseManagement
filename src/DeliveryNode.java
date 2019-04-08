/**
 * Erweitert die {@link StorageNode} um die Moeglichkeit Spezifikationen aus einem {@link Shipment} zu laden
 * und diese an das System aktiv abzugeben/anzufragen.
 */
class DeliveryNode extends StorageNode {

    private boolean loading;

    DeliveryNode(int id) {
        super(id);

        //TODO remove
        loading = true;
    }

    DeliveryNode(int id, int x, int y,int width, int height) {
        super(id, x, y, width, height);

        //TODO remove
        loading = true;
    }
    boolean isLoading() {
        return loading;
    }

    void setLoading(boolean loading) {
        this.loading = loading;
    }

    boolean isUnloading() {
        return !loading;
    }

    /**
     * Kontrolliert ob eingeladen werden darf und ruft dann {@link StorageNode#loadItems} auf<br>
     * {@inheritDoc}
     *
     * @param materialType {@inheritDoc}
     * @param amount       {@inheritDoc}
     */
    @Override
    void loadItems(int materialType, int amount) {
        if (isLoading()) {
            super.loadItems(materialType, amount);
            if (getAmount() == getStorageSize()) {
                requestNextShipment();
            }
        } else {
            throw new RuntimeException("Kann keine Items einladen wenn ausgeladen werden soll");
        }
    }

    /**
     * Kontrolliert ob ausgeladen werden darf und ruft dann {@link StorageNode#loadItems} auf<br>
     * {@inheritDoc}
     *
     * @param amount {@inheritDoc}
     */
    @Override
    void unloadItems(int amount) {
        if (isUnloading()) {
            super.unloadItems(amount);
        } else {
            throw new RuntimeException("Kann keine Items ausladen wenn eingeladen werden soll");
        }
    }

    /**
     * Fragt eine neue Lieferung an
     */
    private void requestNextShipment() {
        //TODO implementieren
    }

    /**
     * Aendert den Modus von abladen in einladen und meldet beim Router an das Waren an diese Node gesendet werden sollen
     */
    private void requestItems() {
        //TODO implementieren
    }
}
