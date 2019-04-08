/**
 * Erweitert die {@link Node} um die Moeglichkeit Waren zu lagern. Roboter können an diesen Nodes Waren
 * abholen oder abgeben
 */
class StorageNode extends Node {

    protected int materialType;
    protected int amount;
    private int storageSize;

    StorageNode(int id) {
        super(id);
        materialType = 0;
        storageSize = 100;
        amount = 0;
    }

    StorageNode(int id, int x, int y) {
        super(id, x, y);
        materialType = 0;
        storageSize = 100;
        amount = 0;
    }
    int getMaterialType() {
        return materialType;
    }

    int getAmount() {
        return amount;
    }

    int getStorageSize() {
        return storageSize;
    }

    /**
     * Ueberschreibt den {@link StorageNode#materialType}, dieser kann aber nur erfolgreich ueberschrieben
     * werden wenn das Lager leer ist
     *
     * @param materialType der neue {@code materialType}
     * @return ob das aendern des {@code materialType} erfolgreich war
     * @throws RuntimeException
     */
    public boolean setMaterialType(int materialType) {
        if (DataConnection.getDataConnection().isValidMaterialType(materialType)) {
            if (amount == 0) {
                this.materialType = materialType;
                return true;
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("Kann Storage Einheit nicht auf ungültigen materialType setzen");
        }
    }

    /**
     * Kontrolliert ob die Lagereinheit den richtigen Materialtyp hat, und lädt dann die angegebene Menge ein
     *
     * @param materialType Type der Ware die eingelagert werden soll
     * @param amount       Menge der Ware die eingeladen werden soll
     * @throws RuntimeException
     */
    void loadItems(int materialType, int amount) {
        if (materialType != getMaterialType()) {
            throw new RuntimeException("Falscher Materialtyp");
        }
        if (this.amount + amount <= storageSize) {
            this.amount += amount;
        }
    }

    /**
     * Kontrolliert ob genug Material im Lager ist um die angefragte Menge auszuladen
     *
     * @param amount Menge die ausgeladen werden soll
     * @throws RuntimeException
     */
    void unloadItems(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
        } else {
            throw new RuntimeException("Lager hat nicht genug Material");
        }
    }
}
