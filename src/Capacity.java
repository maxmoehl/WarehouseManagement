import javax.swing.*;
import java.util.ArrayList;

class Capacity extends JFrame {

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
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        pack();
        setVisible(true);
    }
}
