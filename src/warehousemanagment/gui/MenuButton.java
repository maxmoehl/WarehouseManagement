package warehousemanagment.gui;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {

    private String text;

    public MenuButton(String text) {
        super();
        this.text = text;

        setBorderPainted(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, getHeight() - 20));
        g.drawString(text, 10, getHeight() - 10);
    }
}
