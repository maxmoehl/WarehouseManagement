package warehousemanagement;

import warehousemanagement.gui.Frame;
import warehousemanagement.gui.Panel;
import warehousemanagement.navigation.DeliveryNode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hauptkontrolleinheit die alle wichtigen Componenten initialisiert und verwaltet, stellt wichtige Grundfunktionalitäten zur Verfuegung
 */
public class Controller {

    /**
     * Simuliert die Uhrzeit um sicherzustellen, dass bei jedem Programmstart {@link Shipment} ankommen, startet bei jedem Programmstart bei null
     */
    private int time;

    /**
     * Das Hauptpanel auf dem alles gezeichnet und angezeigt wird
     */
    private Panel panel;

    /**
     * Hauptframe mit der JMenuBar
     */
    private Frame frame;

    private ArrayList<DeliveryNode> deliveryNodesQueue;

    private ArrayList<Shipment> shipmentsQueue;

    private Controller() {
        panel = new Panel();
        frame = new Frame(panel);
        deliveryNodesQueue = new ArrayList<>(Map.getMap().deliveryNodes);
        shipmentsQueue = new ArrayList<>();
        initClock();
    }

    public static Controller getController() {
        return ControllerHolder.INSTANCE;
    }

    private void initClock() {
        time = 0;
        Timer clock = new Timer("Clock", true);
        clock.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
                frame.setTitle("Warehouse Management Software (" + time + ")");
                handleShipments();
            }
        }, 0, 1000);
    }

    int getTime() {
        return time;
    }

    private void handleShipments() {
        int t = getTime();
        DataConnection d = DataConnection.getDataConnection();
        Shipment s;
        if ((s = d.getShipment(t)) != null) {
            if (deliveryNodesQueue.size() > 0) {
                deliveryNodesQueue.get(0).loadShipment(s);
                deliveryNodesQueue.remove(0);
            }
        }
    }

    public Shipment requestNextShipment(DeliveryNode n) {
        if (shipmentsQueue.size() == 0) {
            deliveryNodesQueue.add(n);
            return null;
        }
        Shipment r = shipmentsQueue.get(0);
        shipmentsQueue.remove(0);
        return r;
    }

    public boolean isQueued(DeliveryNode n) {
        for (int i = 0; i < deliveryNodesQueue.size(); i++) {
            if (deliveryNodesQueue.get(i).equals(n)) {
                return true;
            }
        }
        return false;
    }

    private static class ControllerHolder {
        private static final Controller INSTANCE = new Controller();
    }
}
