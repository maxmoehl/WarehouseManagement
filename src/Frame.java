import javax.swing.*;
import java.awt.*;

class Frame extends JFrame {

    private JMenuBar menuBar;

    Frame(Panel panel) {
        super("Warehouse Management Software");
        setSize(500, 500);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initMenuBar();
        setJMenuBar(menuBar);
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
