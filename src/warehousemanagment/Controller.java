package warehousemanagment;

import warehousemanagment.gui.Frame;
import warehousemanagment.gui.Panel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Hauptkontrolleinheit die alle wichtigen Componenten initialisiert und verwaltet, stellt wichtige Grundfunktionalitäten zur Verfuegung
 */
class Controller {

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

    private Controller() {
        panel = new Panel();
        frame = new Frame(panel);
        initClock();
    }

    static Controller getController() {
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
            }
        }, 0, 1000);
    }

    int getTime() {
        return time;
    }

    private static class ControllerHolder {
        private static final Controller INSTANCE = new Controller();
    }
}
