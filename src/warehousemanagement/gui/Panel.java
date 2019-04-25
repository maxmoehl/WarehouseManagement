package warehousemanagement.gui;

import warehousemanagement.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Panel extends JPanel {

    public Panel() {
        super(new GridLayout(1, 2));

        ((GridLayout) getLayout()).setVgap(10);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane mapContainer = new JScrollPane(Map.getMap());
        mapContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mapContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(mapContainer);

        JPanel buttonContainer = new JPanel(new GridLayout(3, 1));
        ((GridLayout) buttonContainer.getLayout()).setVgap(10);
        MenuButton capacity = new MenuButton("Gesamtkapazität");
        MenuButton nextDeliveries = new MenuButton("Anstehende Lieferungen");
        MenuButton lowCapacity = new MenuButton("Niedrige Lagerbestände");

        ActionListener bL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == capacity) {
                    Capacity frame = new Capacity();
                    frame.setVisible(true);
                }
                else if(e.getSource() == nextDeliveries) {
                    NextDeliveries frame = new NextDeliveries();
                    frame.setVisible(true);
                }
            }
        };
        //TODO Für lowCapacity anpassen
        capacity.addActionListener(bL);
        nextDeliveries.addActionListener(bL);
        buttonContainer.add(capacity);
        buttonContainer.add(nextDeliveries);
        buttonContainer.add(lowCapacity);
        add(buttonContainer);

        Timer painter = new Timer("Panel repainter", true);
        painter.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 100);
    }
}
