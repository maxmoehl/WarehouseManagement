import javax.swing.*;
import java.awt.*;

class Panel extends JPanel {

    Panel() {
        super(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = getWidth();
        int height = getHeight();
        drawMap(g, width, height);
        drawMenu(g, width, height);
    }

    private void drawMap(Graphics g, int width, int height) {
        g.setColor(Color.GRAY);
        g.fillRect((int) Math.round(width * 0.01), (int) Math.round(height * 0.01), (int) Math.round(width * 0.69), (int) Math.round(height * 0.98));
        g.setColor(Color.BLACK);
        g.fillRect((int) Math.round(width * 0.03), (int) Math.round(height * 0.1), (int) Math.round(width * 0.45), (int) Math.round(height * 0.86));
        g.setColor(Color.WHITE);
        g.fillRect((int) Math.round(width * 0.04), (int) Math.round(height * 0.11), (int) Math.round(width * 0.43), (int) Math.round(height * 0.84));
    }

    private void drawMenu(Graphics g, int width, int height) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) Math.round(width * 0.72), (int) Math.round(height * 0.03), (int) Math.round(width * 0.26), (int) Math.round(height * 0.3));
        g.fillRect((int) Math.round(width * 0.72), (int) Math.round(height * 0.35), (int) Math.round(width * 0.26), (int) Math.round(height * 0.3));
        g.fillRect((int) Math.round(width * 0.72), (int) Math.round(height * 0.67), (int) Math.round(width * 0.26), (int) Math.round(height * 0.3));
    }

}
