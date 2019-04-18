package warehousemanagment.navigation;

import java.awt.*;

/**
 * Erweitert die {@link StorageNode} um die Moeglichkeit Spezifikationen aus einem {@link warehousemanagment.Shipment} zu laden
 * und diese an das System aktiv abzugeben/anzufragen.
 */
public class DeliveryNode extends StorageNode {

    /**
     * Gibt an ob die Node gerade be- oder entladen wird um es Robotern zu ermoeglichen die richtige Entscheidung zu treffen
     */
    private boolean loading;

    public DeliveryNode(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);

        //TODO remove
        loading = true;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isUnloading() {
        return !loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    /**
     * Kontrolliert ob eingeladen werden darf und ruft dann {@link StorageNode#loadItems} auf, um es
     * Robotern zu ermoeglichen Waren an DeliveryNodes auszuladen.
     * <br>
     * {@inheritDoc}
     *
     * @param materialType {@inheritDoc}
     * @param amount       {@inheritDoc}
     *
     * @throws RuntimeException wenn {@link DeliveryNode#isLoading()} }{@code == false}
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
     * Kontrolliert ob ausgeladen werden darf und ruft dann {@link StorageNode#loadItems} auf, um es
     * Robotern zu ermöglichen Waren an DeliveryNodes einzuladen.
     * <br>
     * {@inheritDoc}
     *
     * @param amount {@inheritDoc}
     *
     * @throws RuntimeException wenn {@link DeliveryNode#isUnloading()} {@code == false}
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
     * Wenn ein {@link warehousemanagment.Shipment} abgefertigt wurde laedt diese Methode das naechste beziehungsweise
     * reiht sich in eine Warteschlange ein
     */
    private void requestNextShipment() {
        //TODO implementieren
    }

    /**
     * Aendert den Modus von abladen in einladen damit zugeordnete Roboter wissen, dass sie zukuenftig Items anliefern muessen
     */
    private void requestItems() {
        //TODO implementieren
    }

    @Override
    public void paintComponent(Graphics g) {
        //TODO implementieren
    }
}
