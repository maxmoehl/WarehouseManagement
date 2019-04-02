import javax.swing.*;
import java.awt.*;

class Frame extends JFrame {

    private JMenuBar menuBar;

    Frame(Panel panel) {
        super("Warehouse Management Software");
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

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem exportItem = new JMenuItem("Export data");
        fileMenu.add(exportItem);
    }
}
