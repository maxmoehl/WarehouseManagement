package warehousemanagment.gui;

import warehousemanagment.DataConnection;
import warehousemanagment.navigation.StorageNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StorageNodeConfiguration extends JPanel implements ActionListener {

    private StorageNode storageNode;

    private JComboBox<String> jCBMaterial;

    private JFrame frame;

    public StorageNodeConfiguration(StorageNode storageNode) {
        super(new GridLayout(5, 2));

        this.storageNode = storageNode;

        add(new JLabel("ID:"));

        JTextField jTFId = new JTextField("" + storageNode.getId());
        jTFId.setEnabled(false);
        add(jTFId);

        add(new JLabel("Materialart:"));

        jCBMaterial = new JComboBox<>(DataConnection.getDataConnection().getMaterialTypes());
        jCBMaterial.setSelectedIndex(storageNode.getMaterialType());
        jCBMaterial.setEditable(false);
        add(jCBMaterial);

        add(new JLabel("Lagergröße:"));

        JTextField jTFStorageSize = new JTextField("" + storageNode.getStorageSize());
        jTFStorageSize.setEnabled(false);
        add(jTFStorageSize);

        add(new JLabel("Füllstand"));

        JTextField jTFItemCount = new JTextField("" + storageNode.getAmount());
        jTFItemCount.setEnabled(false);
        add(jTFItemCount);

        add(new JLabel());

        JButton submit = new JButton("Speichern");
        submit.addActionListener(this);
        add(submit);

        frame = new JFrame("Konfiguration für StorageNode " + storageNode.getId());
        frame.add(this);
        frame.setSize(new Dimension(200, 150));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (storageNode.getMaterialType() == jCBMaterial.getSelectedIndex()) {
            JOptionPane.showMessageDialog(this, "Kann Materialtyp nur ändern wenn keine Materialien im Lager sind");
        } else {
            if (jCBMaterial.getSelectedIndex() == 0) {
                storageNode.resetMaterialType();
            } else {
                storageNode.setMaterialType(jCBMaterial.getSelectedIndex());
            }
            frame.dispose();
        }
    }
}
