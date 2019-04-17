import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

class Panel extends JPanel {

    private Timer painter;

    Panel() {
        super(new GridLayout(1, 2));

        ((GridLayout) getLayout()).setVgap(10);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane mapContainer = new JScrollPane(Map.getMap());
        mapContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mapContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(mapContainer);

        JPanel buttonContainer = new JPanel(new GridLayout(3, 1));
        ((GridLayout) buttonContainer.getLayout()).setVgap(10);
        buttonContainer.add(new MenuButton("Capacity"));
        buttonContainer.add(new MenuButton("Shipments"));
        buttonContainer.add(new MenuButton("Low Space"));
        add(buttonContainer);

        painter = new Timer(true);
        painter.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 100);
    }
}
