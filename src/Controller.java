import java.util.Timer;
import java.util.TimerTask;

/**
 * Hauptkontrolleinheit die alle wichtigen Componenten initialisiert und verwaltet, stellt wichtige Grundfunktionalitäten zur Verfügung
 */
class Controller {

    private static class ControllerHolder {
        private static final Controller INSTANCE = new Controller();
    }

    public static int time = 0;

    /**
     * Das Hauptpanel auf dem alles gezeichnet und angezeigt wird
     */
    private Panel panel;
    /**
     * Hauptframe mit der JMenuBar
     */
    private Frame frame;

    /**
     * Übernimmt alle routing Aufgaben und kommuniziert mit den Robotern
     */
    private Router router;

    /**
     * Initialisiert eine neue Instanz des gesamten Programms
     */
    Controller() {
        Timer clock = new Timer(true);
        clock.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
            }
        },0,1000);

        panel = new Panel();
        frame = new Frame(panel);

        router = Router.getRouter();
    }

    static Controller getController() {
        return ControllerHolder.INSTANCE;
    }
}
