package warehousemanagement.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MenuButton extends JComponent implements MouseListener {
    private ArrayList<ActionListener> eventListeners;
    private String text;
    private boolean hasProgressBar;
    private String subText;
    private boolean hasSubText;

    MenuButton(String text) {
        super();
        this.text = text;
        hasSubText = false;
        hasProgressBar = false;
        eventListeners = new ArrayList<>();
        addMouseListener(this);
    }

    MenuButton(String text, int min, int max) {
        this(text);
        setLayout(null);
        hasProgressBar = true;
    }

    MenuButton(String text, String subText) {
        this(text);
        this.subText = subText;
        setLayout(null);
        hasSubText = true;
    }

    public void addActionListener(ActionListener a) {
        eventListeners.add(a);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(text, (int) (getWidth() * 0.3), (int) (getHeight() * 0.15));

        if (hasSubText) {
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(subText, (int) (getWidth() * 0.3), (int) (getHeight() * 0.3));
        }
        if (hasProgressBar) {
            double percent = 0.5;
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.WHITE);
            g.drawRect((int) (getWidth() * 0.5), (int) (getHeight() * 0.4), (int) (getWidth()* 0.2), (int) (getHeight()* 0.2));
            g.setColor(Color.RED);
            g.fillRect((int) (getWidth() * 0.5), (int) (getHeight() * 0.4), (int) ((getWidth() * 0.2) * percent) ,(int) (getHeight()* 0.2));
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        for (ActionListener a : eventListeners) {
            a.actionPerformed(new ActionEvent(this, 0, "MenuButton Pressed"));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
