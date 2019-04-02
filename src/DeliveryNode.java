class DeliveryNode extends StorageNode {

    private boolean loading;

    DeliveryNode(int id) {
        super(id);
        loading = true;
    }

    boolean isLoading() {
        return loading;
    }

    boolean isUnloading() {
        return !loading;
    }

    void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    void loadItems(int materialType, int amount) {
        if(isLoading()) {
            super.loadItems(materialType, amount);
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
