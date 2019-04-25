package warehousemanagement.gui;

import warehousemanagement.DataConnection;
import warehousemanagement.navigation.StorageNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class StorageNodeConfiguration extends JPanel implements ActionListener, WindowListener {

    private StorageNode storageNode;

    private JComboBox<String> jCBMaterial;

    private JFrame frame;

    public StorageNodeConfiguration(StorageNode storageNode, int x, int y) {
        super(new GridLayout(5, 2));

        ((GridLayout) getLayout()).setVgap(10);
        ((GridLayout) getLayout()).setHgap(10);

        setBorder(new EmptyBorder(5, 5, 5, 5));

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

        frame = new JFrame("Konfiguration");
        frame.add(this);
        frame.setResizable(false);
        frame.setLocation(x, y);
        frame.setSize(new Dimension(250, 200));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (storageNode.getMaterialType() == jCBMaterial.getSelectedIndex()) {
            frame.dispose();
            return;
        }

        if (storageNode.getAmount() != 0) {
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

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        frame.dispose();
    }
}
