package warehousemanagement.gui;

import warehousemanagement.DataConnection;
import warehousemanagement.Map;
import warehousemanagement.Shipment;
import warehousemanagement.navigation.StorageNode;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Frame extends JFrame {

    private JMenuBar menuBar;

    public Frame(Panel panel) {
        super("Warehouse Management Software");
        setSize(500, 300);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initMenuBar();
        setJMenuBar(menuBar);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        JMenu exportMenu = new JMenu("Exportieren");
        menuBar.add(exportMenu);

        JMenuItem exportCapacity = new JMenuItem("Lagerbestände");
        JMenuItem exportNextDeliveries = new JMenuItem("Anstehende Lieferungen");

        exportCapacity.addActionListener(e -> {
            List<StorageNode> storageNodes = Map.getMap().storageNodes;
            String[][] data = new String[storageNodes.size() + 1][3];
            data[0] = new String[]{"Regalnummer", "Materialtyp", "Auslastung"};
            for (int i = 1; i <= storageNodes.size(); i++) {
                data[i][0] = "" + storageNodes.get(i - 1).getId();
                data[i][1] = DataConnection.getDataConnection().getMaterialType(storageNodes.get(i - 1).getMaterialType());
                data[i][2] = storageNodes.get(i - 1).getAmount() + " / " + storageNodes.get(i - 1).getStorageSize();
            }

            writeDataToCSV(data);
        });

        exportNextDeliveries.addActionListener(e -> {
            List<Shipment> shipments = DataConnection.getDataConnection().getShipments();
            String[][] data = new String[shipments.size() + 1][4];
            data[0] = new String[]{"Lieferungstyp", "Anzahl Artikel", "Spedition", "Uhrzeit"};
            for (int i = 1; i <= shipments.size(); i++) {
                data[i][0] = DataConnection.getDataConnection().getMaterialType(shipments.get(i - 1).getMaterialTypeInbound());
                data[i][1] = shipments.get(i - 1).getSize() + "Stück";
                data[i][2] = shipments.get(i - 1).getSupplier();
                data[i][3] = "Um" + shipments.get(i - 1).getEta();
            }

            writeDataToCSV(data);
        });
        exportMenu.add(exportCapacity);
        exportMenu.add(exportNextDeliveries);
    }

    private void writeDataToCSV(String[][] data) {

        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(Frame.this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String path = fileChooser.getSelectedFile().getPath();
        if (path.contains(".")) {
            String extension = path.substring(path.lastIndexOf('.'));
            if (!extension.equals(".csv")) {
                path += ".csv";
            }
        } else {
            path += ".csv";
        }

        StringBuilder output = new StringBuilder();
        for (String[] line : data) {
            for (String s : line) {
                output.append(s);
                output.append(",");
            }
            output.append("\n");
        }

        try {
            FileWriter fileWriter = new FileWriter(new File(path));
            fileWriter.write(output.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler: Konnte Daten nicht in Datei schreiben");
        }
    }
}
