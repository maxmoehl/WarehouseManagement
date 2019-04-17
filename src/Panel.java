import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class Panel extends JPanel {

    Panel() {
        super(new GridLayout(1, 2));

        ((GridLayout) getLayout()).setVgap(10);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane mapContainer = new JScrollPane();
        add(mapContainer);

        JPanel buttonContainer = new JPanel(new GridLayout(3, 1));
        ((GridLayout) buttonContainer.getLayout()).setVgap(10);
        buttonContainer.add(new MenuButton("Capacity"));
        buttonContainer.add(new MenuButton("Shipments"));
        buttonContainer.add(new MenuButton("Low Space"));
        add(buttonContainer);
    }
}
