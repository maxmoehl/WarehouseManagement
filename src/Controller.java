import java.util.Timer;
import java.util.TimerTask;

/**
 * Hauptkontrolleinheit die alle wichtigen Componenten initialisiert und verwaltet, stellt wichtige Grundfunktionalitäten zur Verfügung
 */
class Controller {

    private int time = 0;
    /**
     * Das Hauptpanel auf dem alles gezeichnet und angezeigt wird
     */
    private Panel panel;
    /**
     * Hauptframe mit der JMenuBar
     */
    private Frame frame;

    /**
     * Initialisiert eine neue Instanz des gesamten Programms
     */
    private Controller() {
        initClock();
        panel = new Panel();
        frame = new Frame(panel);
    }

    static Controller getController() {
        return ControllerHolder.INSTANCE;
    }

    private void initClock() {
        Timer clock = new Timer(true);
        clock.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
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
