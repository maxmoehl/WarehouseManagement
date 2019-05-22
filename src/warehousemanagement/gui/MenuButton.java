package warehousemanagement.gui;

import warehousemanagement.DataConnection;
import warehousemanagement.Map;
import warehousemanagement.Shipment;
import warehousemanagement.navigation.StorageNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;

class MenuButton extends JComponent implements MouseListener {
    private final ArrayList<ActionListener> eventListeners;
    private final String text;
    private boolean hasProgressBar;
    private boolean hasCapacityBar;
    private String subText;
    private boolean hasSubText;

    private MenuButton(String text) {
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

    MenuButton(String text, int min, int max, boolean b) {
        this(text);
        setLayout(null);
        hasCapacityBar = true;
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
            ArrayList<Shipment> shipments = DataConnection.getDataConnection().getShipments();
            Shipment nextShipment = DataConnection.getDataConnection().getNextShipment();

            subText = nextShipment.getSize() + " Stück " + DataConnection.getDataConnection().getMaterialType(nextShipment.getMaterialTypeInbound());
            String subText2 = "Spedition: " + nextShipment.getSupplier();
            String subText3 = "Um: " + nextShipment.getEta() + "s";

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(subText, (int) (getWidth() * 0.3), (int) (getHeight() * 0.4));
            g.drawString(subText2, (int) (getWidth() * 0.3), (int) (getHeight() * 0.5));
            g.drawString(subText3, (int) (getWidth() * 0.3), (int) (getHeight() * 0.6));
        }

        if (hasProgressBar || hasCapacityBar) {
            paintProgressBar(g);
        }
    }

    private void paintProgressBar(Graphics g) {
        List<StorageNode> storageNodes = Map.getMap().storageNodes;
        if (hasProgressBar) {
            StorageNode lowest = null;
            for (int i = 0; i < storageNodes.size(); i++) {
                if (storageNodes.get(i).getMaterialType() == 0) {
                    continue;
                }
                if (lowest == null) {
                    lowest = storageNodes.get(i);
                } else if (lowest.getAmount() > storageNodes.get(i).getAmount()) {
                    lowest = storageNodes.get(i);
                }
            }
            double percent = 0;
            if (lowest != null) {
                percent = ((double) (lowest.getAmount()) / lowest.getStorageSize());
            }
            if (lowest != null) {
                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.drawString(DataConnection.getDataConnection().getMaterialType(lowest.getMaterialType()), (int) (getWidth() * 0.3), (int) (getHeight() * 0.5));
            }
            drawPercentage(g, percent);
        }
        if (hasCapacityBar) {
            double totalDenominator = 0;
            double totalNumerator = 0;
            double totalCapacity;
            for (int i = 0; i < storageNodes.size(); i++) {
                totalDenominator += storageNodes.get(i).getStorageSize();
                totalNumerator += storageNodes.get(i).getAmount();
            }

            totalCapacity = (totalNumerator / totalDenominator);
            drawPercentage(g, totalCapacity);
        }
    }

    private void drawPercentage(Graphics g, double percentage) {
        g.setColor(Color.WHITE);
        g.drawRect((int) (getWidth() * 0.4), (int) (getHeight() * 0.4), (int) (getWidth() * 0.2), (int) (getHeight() * 0.2));
        g.setColor(Color.RED);
        g.fillRect((int) (getWidth() * 0.4) + 1, (int) (getHeight() * 0.4) + 1, (int) ((getWidth() * 0.2) * (percentage)) - 1, (int) (getHeight() * 0.2) - 1);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.BLACK);
        g.drawString((int) (percentage * 100) + "%", (int) (getWidth() * 0.4), (int) (getHeight() * 0.3));

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
