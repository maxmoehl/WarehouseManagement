package warehousemanagment.navigation;

import warehousemanagment.DataConnection;

import java.awt.*;
import java.util.ArrayList;

/**
 * Erweitert die {@link Node} um die Moeglichkeit Waren zu lagern. Roboter können an diesen Nodes Waren
 * abholen oder abgeben
 */
public class StorageNode extends Node {

    private int materialType;
    private int amount;
    private int storageSize;

    private ArrayList<warehousemanagment.navigation.Robot> robotQueue;

    private boolean blocked;

    public StorageNode(int id) {
        super(id);
        materialType = 0;
        storageSize = 100;
        amount = 0;
        blocked = false;
        robotQueue = new ArrayList<>();
    }

    public StorageNode(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        materialType = 0;
        storageSize = 100;
        amount = 0;
        blocked = false;
        robotQueue = new ArrayList<>();
    }

    public int getMaterialType() {
        return materialType;
    }

    public int getAmount() {
        return amount;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public boolean accessNode(Robot robot) {
        if (blocked) {
            robotQueue.add(robot);
            return false;
        } else {
            blocked = true;
            return true;
        }
    }

    public void leaveNode() {
        blocked = false;
        if (robotQueue.size() != 0) {
            robotQueue.get(0).notify();
        }
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
    public void loadItems(int materialType, int amount) {
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
    public void unloadItems(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
        } else {
            throw new RuntimeException("Lager hat nicht genug Material");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = (int) g.getClipBounds().getX();
        int y = (int) g.getClipBounds().getY();
        int width = (int) g.getClipBounds().getWidth();
        int height = (int) g.getClipBounds().getHeight();

        g.setColor(Color.BLACK);
        g.fillRect(x, (int) (y + 0.5 * height), width, (int) (0.5 * height));

        if (blocked) {
            g.fillRect((int) (x + 0.45 * width), (int) (y + 0.15 * height), (int) (0.1 * width), (int) (0.2 * height));
        }
    }
}
