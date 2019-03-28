import javax.swing.*;
import java.awt.*;

class Frame extends JFrame {

    JMenuBar menuBar = new JMenuBar();

    Frame(Panel panel) {
        super("Warehouse Management Software");
        setSize(500, 500);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void initMenuBar() {

    }
}
