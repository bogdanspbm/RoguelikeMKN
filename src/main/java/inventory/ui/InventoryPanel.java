package inventory.ui;

import inventory.Inventory;
import java.io.File;
import objects.animations.objects.TextureSource;

public class InventoryPanel extends javax.swing.JPanel {

    Inventory inventory;
    
    TextureSource sourceSlot;

    public InventoryPanel(Inventory inventory) {
        this.inventory = inventory;
        
        initSources();
        initComponents();
        generateSlots();
    }
    
    private void initSources() {
        try {      
            sourceSlot = new TextureSource(new File("src/main/resources/inventory/slot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateSlots() {

        int width = inventory.getWidth();
        
        for (int i = 0; i < inventory.getItems().size(); i++) {

            SlotPanel slot = new SlotPanel(sourceSlot);
            
            int x = i % width;
            int y = i / width;
            
             add(slot, new org.netbeans.lib.awtextra.AbsoluteConstraints(x*32, y*32, 32, 32));
        }

        updateUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(150, 150, 150));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
