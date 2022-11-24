package inventory.ui;

import objects.animations.objects.TextureSource;




public class SlotPanel extends javax.swing.JPanel {

    private TextureSource source;
            
    public SlotPanel(TextureSource source) {
        this.source = source;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(200, 200, 200));
        setMaximumSize(new java.awt.Dimension(32, 32));
        setMinimumSize(new java.awt.Dimension(32, 32));
        setLayout(new java.awt.GridBagLayout());

        background.setIcon(new javax.swing.ImageIcon(source.getImage()));
        add(background, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    // End of variables declaration//GEN-END:variables
}
