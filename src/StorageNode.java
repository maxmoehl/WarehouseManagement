class StorageNode extends Node {

    protected int materialType;
    protected int amount;
    private int size;

    StorageNode(int id) {
        super(id);
        materialType = 0;
        size = 100;
        amount = 0;
    }

    int getMaterialType() {
        return materialType;
    }

    int getAmount() {
        return amount;
    }

    int getSize() {
        return size;
    }

    public boolean setMaterialType(int materialType) {
        if (DataConnection.isValidMaterialType(materialType)) {
            if(amount == 0) {
                this.materialType = materialType;
                return true;
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("Kann Storage Einheit nicht auf ungültigen materialType setzen");
        }
    }

    void loadItems(int materialType, int amount) {
        if (materialType != getMaterialType()) {
            throw new RuntimeException("Wrong ItemType");
        }
        if(this.amount + amount <= size) {
            this.amount += amount;
        }
    }

    void unloadItems(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
        } else {
            throw new RuntimeException("Lager hat nicht genug Items");
        }
    }
}
