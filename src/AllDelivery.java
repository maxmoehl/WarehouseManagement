

import javax.swing.JPanel;
import javax.swing.*;

public class AllDelivery extends JPanel {
	public AllDelivery() {
		super(null);
		JFrame AllDelivery = new JFrame("Delivery");
		AllDelivery.setSize(500, 500);
		AllDelivery.setVisible(true);
		AllDelivery.add(this);
	}

	public static void main(String[] args) {
		new AllDelivery();
	}

}
