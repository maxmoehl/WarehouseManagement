class Controller {

    private Panel panel;
    private Frame frame;

    private Router router;

    Controller() {
        initGUI();

        router = new Router();
    }

    private void initGUI() {
        panel = new Panel();
        frame = new Frame(panel);
    }
}
