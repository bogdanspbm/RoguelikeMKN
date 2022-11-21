/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventoryTests.window;


import inventory.Inventory;
import inventory.ui.PanelInventory;

public class InventoryWindow extends javax.swing.JFrame {


    Inventory inventory;

    public InventoryWindow(Inventory inventory) {
        this.inventory = inventory;
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pInventory = new PanelInventory(inventory);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(500, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout pInventoryLayout = new javax.swing.GroupLayout(pInventory);
        pInventory.setLayout(pInventoryLayout);
        pInventoryLayout.setHorizontalGroup(
                pInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        pInventoryLayout.setVerticalGroup(
                pInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(pInventory, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String[] args) {
        InventoryWindow window = new InventoryWindow(new Inventory());
        window.setVisible(true);
    }

    private javax.swing.JPanel pInventory;

}
