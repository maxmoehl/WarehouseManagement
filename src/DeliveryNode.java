class DeliveryNode extends StorageNode {

    private boolean loading;

    DeliveryNode(int id) {
        super(id);
    }

    boolean isLoading() {
        return loading;
    }

    boolean isUnloading() {
        return !loading;
    }

    @Override
    void loadItems(int itemType, int amount) {
        if(isLoading()) {
            super.loadItems(itemType, amount);
            if (getAmount() == getSize()) {
                requestNextShipment();
            }
        } else {
            throw new RuntimeException("Kann keine Items einladen wenn ausgeladen werden soll");
        }
    }

    @Override
    void unloadItems(int amount) {
        if(isUnloading()) {
            super.unloadItems(amount);
        } else {

        }
    }

    private void requestNextShipment() {

    }

    private void requestItems() {

    }
}
