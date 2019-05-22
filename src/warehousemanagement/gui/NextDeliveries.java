package warehousemanagement.gui;

import warehousemanagement.DataConnection;
import warehousemanagement.Shipment;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Dieses Frame zeigt alle anstehende {@link warehousemanagement.Shipment}s an und wird, solange es offen ist, sekuendlich aktualisiert
 */
class NextDeliveries extends JFrame {

    private final JTable table;

    private Timer update;

    NextDeliveries() {
        super("Anstehende Lieferungen");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {"Lieferungstyp", "Anzahl Artikel", "Spedition", "Uhrzeit"};


        ArrayList<Shipment> shipments = DataConnection.getDataConnection().getShipments();

        String[][] data = new String[shipments.size()][columnNames.length];

        for (int i = 0; i < shipments.size(); i++) {
            data[i][0] = DataConnection.getDataConnection().getMaterialType(shipments.get(i).getMaterialTypeInbound());
            data[i][1] = shipments.get(i).getSize() + "Stück";
            data[i][2] = shipments.get(i).getSupplier();
            data[i][3] = "Um" + shipments.get(i).getEta();
        }

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        pack();
        setVisible(true);

        update = new Timer("NextDeliveries Updater", true);
        update.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshData();
            }
        }, 0, 1000);
    }

    private void refreshData() {
        ArrayList<Shipment> shipments = DataConnection.getDataConnection().getShipments();
        for (int i = 0; i < shipments.size(); i++) {
            table.setValueAt(DataConnection.getDataConnection().getMaterialType(shipments.get(i).getMaterialTypeInbound()), i, 0);
            table.setValueAt(shipments.get(i).getSize() + " Stück", i, 1);
            table.setValueAt(shipments.get(i).getSupplier(), i, 2);
            table.setValueAt("Um " + shipments.get(i).getEta(), i, 3);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        update.cancel();
        update = null;
    }
}
