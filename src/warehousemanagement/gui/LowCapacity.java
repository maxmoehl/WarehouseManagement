package warehousemanagement.gui;

import warehousemanagement.DataConnection;
import warehousemanagement.Map;
import warehousemanagement.navigation.StorageNode;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class LowCapacity extends JFrame {

    private final JTable table;

    private Timer updater;

    LowCapacity() {
        super("Niedrige Lagerbestände");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {"Regalnummer", "Materialtyp", "Auslastung (Niedrig!)"};

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

        updater = new Timer("Low Capacity Updater", true);
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
            if (storageNodes.get(i).getAmount() < 0.05 * storageNodes.get(i).getStorageSize()) {
                table.setValueAt(storageNodes.get(i).getAmount() + " / " + storageNodes.get(i).getStorageSize(), i, 2);
            } else {
                table.setValueAt(null, i, 0);
                table.setValueAt(null, i, 1);
                table.setValueAt(null, i, 2);
            }
            if (table.getValueAt(i, 1) == "Leer") {
                table.setValueAt(null, i, 0);
                table.setValueAt(null, i, 1);
                table.setValueAt(null, i, 2);
            }

        }
    }

    @Override
    public void dispose() {
        super.dispose();
        updater.cancel();
        updater = null;
    }
}
