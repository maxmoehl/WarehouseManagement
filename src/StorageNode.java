class StorageNode extends Node {

    protected int itemType;
    protected int amount;
    private int size;

    public StorageNode(int id) {
        super(id);
        itemType = 0;
        size = 100;
        amount = 0;
    }

    int getItemType() {
        return itemType;
    }

    int getAmount() {
        return amount;
    }

    int getSize() {
        return size;
    }

    public boolean setItemType(int itemType) {
        if(isValidItemType(itemType)) {
            if(amount == 0) {
                this.itemType = itemType;
                return true;
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("Kann Storage Einheit nicht auf ungültigen itemType setzen");
        }
    }

    boolean isValidItemType(int itemType) {
        return true;
    }

    void loadItems(int itemType, int amount) {
        if(itemType != getItemType()) {
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
