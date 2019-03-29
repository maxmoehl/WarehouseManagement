/**
 * Hauptkontrolleinheit die alle wichtigen Componenten initialisiert und verwaltet, stellt wichtige Grundfunktionalitäten zur verfügung
 */
class Controller {

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
        panel = new Panel();
        frame = new Frame(panel);

        router = new Router();
    }
}
