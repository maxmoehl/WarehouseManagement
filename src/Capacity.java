import javax.swing.*;
import java.awt.*;

public class Capacity extends JPanel {

    public Capacity() {

        super(null);
        JFrame capacity = new JFrame("Gesamtkapazit�t");
        capacity.setSize(1500, 1000);
        capacity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        capacity.add(this);

        String[] columnNames = {"Regalnummer", "Materialtyp", "Auslastung",};
        Object[][] data = {{"Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe", "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black", "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White", "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}};

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        capacity.setVisible(true);
    }

    public static void main(String[] args) {

        new Capacity();

    }
}
