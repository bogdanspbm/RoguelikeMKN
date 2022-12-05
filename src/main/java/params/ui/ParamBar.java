package params.ui;

import params.ParamsComponent;


public class ParamBar extends javax.swing.JPanel {

    String name; 
    ParamsComponent component;
    
    public ParamBar(String name, ParamsComponent component) {
        this.name = name;
        this.component = component;
        initComponents();
        
        tName.setText(name);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tName = new javax.swing.JLabel();
        tValue = new javax.swing.JLabel();
        bUpdate = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(2147483647, 30));
        setMinimumSize(new java.awt.Dimension(200, 30));
        setPreferredSize(new java.awt.Dimension(400, 30));
        setLayout(new java.awt.GridBagLayout());

        tName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tName.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(tName, gridBagConstraints);

        tValue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tValue.setText("10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        add(tValue, gridBagConstraints);

        bUpdate.setText("+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(bUpdate, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bUpdate;
    private javax.swing.JLabel tName;
    private javax.swing.JLabel tValue;
    // End of variables declaration//GEN-END:variables
}
