package paramsTests.window;

import exceptions.DatabaseException;
import inventory.Inventory;
import inventory.objects.Item;
import inventory.ui.InventoryPanel;
import inventoryTests.window.InventoryWindow;
import params.ParamsComponent;
import params.ui.ParamPanel;

import javax.swing.*;

public class ParamsWindow extends JFrame {

    ParamsComponent paramsComponent;

    public ParamsWindow(ParamsComponent paramsComponent) {
        this.paramsComponent = paramsComponent;
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() throws DatabaseException {
        java.awt.GridBagConstraints gridBagConstraints;

        pParam = new ParamPanel(paramsComponent);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(500, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(pParam, gridBagConstraints);

        pack();
    }

    public static void main(String[] args) {
        ParamsComponent paramsComponent = new ParamsComponent();


        ParamsWindow window = new ParamsWindow(paramsComponent);

        window.setVisible(true);
    }

    private ParamPanel pParam;
}
