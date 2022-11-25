/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventoryTests.window;


import exceptions.DatabaseException;
import inventory.Inventory;
import inventory.objects.Item;
import inventory.ui.InventoryPanel;

public class InventoryWindow extends javax.swing.JFrame {


    Inventory inventory;

    public InventoryWindow(Inventory inventory) {
        this.inventory = inventory;
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    private void initComponents() throws DatabaseException {
        java.awt.GridBagConstraints gridBagConstraints;

        pInventory = new InventoryPanel(inventory);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(500, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(pInventory, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addItem(new Item(1, 7));

        inventory.addItem(new Item(2, 1));

        InventoryWindow window = new InventoryWindow(inventory);

        window.setVisible(true);
    }

    private InventoryPanel pInventory;

}
