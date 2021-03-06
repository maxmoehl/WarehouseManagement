package warehousemanagement.gui;

import warehousemanagement.DataConnection;
import warehousemanagement.Map;
import warehousemanagement.navigation.StorageNode;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Dieses Frame zeigt den aktuellen Lagerstand aller {@link warehousemanagement.navigation.StorageNode}s an und wird, solange es offen ist, sekündlich aktualisiert.
 */
class Capacity extends JFrame {

    private final JTable table;

    private Timer updater;

    Capacity() {
        super("Gesamtkapazität");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {"Regalnummer", "Materialtyp", "Auslastung"};

        List<StorageNode> storageNodes = Map.getMap().storageNodes;

        String[][] data = new String[storageNodes.size()][3];
        for (int i = 0; i < storageNodes.size(); i++) {
            data[i][0] = "" + storageNodes.get(i).getId();
            data[i][1] = DataConnection.getDataConnection().getMaterialType(storageNodes.get(i).getMaterialType());
            data[i][2] = storageNodes.get(i).getAmount() + " / " + storageNodes.get(i).getStorageSize();
        }

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        pack();
        setVisible(true);

        updater = new Timer("Capacity Updater", true);
        updater.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshData();
            }
        }, 0, 1000);
    }

    private void refreshData() {
        List<StorageNode> storageNodes = Map.getMap().storageNodes;
        for (int i = 0; i < storageNodes.size(); i++) {
            table.setValueAt("" + storageNodes.get(i).getId(), i, 0);
            table.setValueAt(DataConnection.getDataConnection().getMaterialType(storageNodes.get(i).getMaterialType()), i, 1);
            table.setValueAt(storageNodes.get(i).getAmount() + " / " + storageNodes.get(i).getStorageSize(), i, 2);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        updater.cancel();
        updater = null;
    }
}
