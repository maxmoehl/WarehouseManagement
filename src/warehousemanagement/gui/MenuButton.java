package warehousemanagement.gui;

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
        Font font = new Font("Arial", Font.BOLD, 40);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(font);
        //TODO noch zentrieren oder in beliebigen Ort festlegen
        g.drawString(text, 10 , getHeight() - 10);
    }
}
