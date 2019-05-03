package warehousemanagement.gui;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Frame extends JFrame {

    private JMenuBar menuBar;

    public Frame(Panel panel) {
        super("Warehouse Management Software");
        setSize(500, 300);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initMenuBar();
        setJMenuBar(menuBar);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        JMenu exportMenu = new JMenu("Exportieren");
        menuBar.add(exportMenu);

        JMenuItem exportCapacity = new JMenuItem("Lagerbestände");
        JMenuItem exportNextDeliveries = new JMenuItem("Anstehende Lieferungen");

        ActionListener mL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int option = fc.showSaveDialog(Frame.this);
                if(option == JFileChooser.APPROVE_OPTION) {
                    String filename = fc.getSelectedFile().getName();
                    String path = fc.getSelectedFile().getParentFile().getPath();
                    int len = filename.length();
                    String ext = "";
                    String file = "";

                    if (len > 4) {
                        ext = filename.substring(len - 4, len);
                    }

                    if (ext.equals(".xls")) {
                        file = path + "\\" + filename;
                    } else {
                        file = path + "\\" + filename + ".xls";
                    }

                    if (e.getSource() == exportCapacity) {
                        Capacity c = new Capacity();
                        toCSV(c.getTable(), new File(file));
                    } else if (e.getSource() == exportNextDeliveries) {
                        NextDeliveries n = new NextDeliveries();
                        toCSV(n.getTable(), new File(file));
                    }
                }
            }
        };

        exportCapacity.addActionListener(mL);
        exportNextDeliveries.addActionListener(mL);
        exportMenu.add(exportCapacity);
        exportMenu.add(exportNextDeliveries);
    }

    public void toCSV(JTable table, File file) {
        try {
            TableModel model = table.getModel();
            FileWriter excel = new FileWriter(file);

            for (int i = 0; i < model.getColumnCount(); i++) {
                excel.write(model.getColumnName(i) + "\t");
            }
            excel.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i, j).toString() + "\t");
                }
                excel.write("\n");
            }
            excel.close();
        } catch (IOException e) {
            System.out.println("Fehler bei Exportierung");
        }
    }
}
