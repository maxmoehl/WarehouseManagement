import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class Capacity extends JFrame {

    private String[][] data;

    private JTable table;

    Capacity() {
        super("Gesamtkapazität");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] columnNames = {"Regalnummer", "Materialtyp", "Auslastung",};

        ArrayList<StorageNode> storageNodes = Map.getMap().storageNodes;

        String[][] data = new String[storageNodes.size()][3];
        for (int i = 0; i < storageNodes.size(); i++) {
            data[i][0] = "Storage " + storageNodes.get(i).getId();
            data[i][1] = DataConnection.getDataConnection().getMaterialType(storageNodes.get(i).getMaterialType());
            data[i][2] = storageNodes.get(i).getAmount() + " / " + storageNodes.get(i).getStorageSize();
        }

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        pack();
        setVisible(true);

        Timer updater = new Timer(true);
        updater.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshData();
            }
        }, 0, 1000);
    }

    private void refreshData() {
        ArrayList<StorageNode> storageNodes = Map.getMap().storageNodes;
        for (int i = 0; i < storageNodes.size(); i++) {
            table.setValueAt("Storage " + storageNodes.get(i).getId(), i, 0);
            table.setValueAt(DataConnection.getDataConnection().getMaterialType(storageNodes.get(i).getMaterialType()), i, 1);
            table.setValueAt(storageNodes.get(i).getAmount() + " / " + storageNodes.get(i).getStorageSize(), i, 2);
        }
    }
}
