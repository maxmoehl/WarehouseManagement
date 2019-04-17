package warehousemanagment.navigation;

import java.awt.*;

/**
 * Erweitert die {@link StorageNode} um die Moeglichkeit Spezifikationen aus einem {@link warehousemanagment.Shipment} zu laden
 * und diese an das System aktiv abzugeben/anzufragen.
 */
public class DeliveryNode extends StorageNode {

    private boolean loading;

    public DeliveryNode(int id) {
        super(id);

        //TODO remove
        loading = true;
    }

    public DeliveryNode(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);

        //TODO remove
        loading = true;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isUnloading() {
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
    public void loadItems(int materialType, int amount) {
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
    public void unloadItems(int amount) {
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
     * Aendert den Modus von abladen in einladen und meldet beim Router an das Waren an diese warehousemanagment.navigation.Node gesendet werden sollen
     */
    private void requestItems() {
        //TODO implementieren
    }

    @Override
    public void paintComponent(Graphics g) {

    }
}
