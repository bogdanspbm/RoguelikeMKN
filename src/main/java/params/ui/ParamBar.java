package params.ui;

import params.ParamsComponent;
import ui.buttons.JSmoothButton;


public class ParamBar extends javax.swing.JPanel {

    String name; 
    ParamsComponent component;
    ParamPanel parent;
    
    public ParamBar(String name, ParamsComponent component) {
        this.name = name;
        this.component = component;
        initComponents();
        setName();
    }
    
    private void setName(){
         tName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
         tValue.setText(component.getParamsMap().get(name) + "");
    }
    
    public void setParent(ParamPanel parent){
        this.parent = parent;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tName = new javax.swing.JLabel();
        tValue = new javax.swing.JLabel();
        bAdd = new JSmoothButton(3,3,3,3);

        setBackground(new java.awt.Color(51, 51, 51));
        setMaximumSize(new java.awt.Dimension(2147483647, 30));
        setMinimumSize(new java.awt.Dimension(200, 30));
        setPreferredSize(new java.awt.Dimension(400, 30));
        setLayout(new java.awt.GridBagLayout());

        tName.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tName.setForeground(new java.awt.Color(255, 255, 255));
        tName.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(tName, gridBagConstraints);

        tValue.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tValue.setForeground(new java.awt.Color(255, 255, 255));
        tValue.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        add(tValue, gridBagConstraints);

        bAdd.setMaximumSize(new java.awt.Dimension(24, 24));
        bAdd.setMinimumSize(new java.awt.Dimension(24, 24));
        bAdd.setPreferredSize(new java.awt.Dimension(24, 24));
        bAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(bAdd, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddMouseClicked
        if(component.getFreePoints() > 0){
            component.increaseParam(name);
            setName();
        }
        
        if(component.getFreePoints() <= 0){
            bAdd.setVisible(false);
        }
        
        parent.update();
    }//GEN-LAST:event_bAddMouseClicked

    public void update(){
        if(component.getFreePoints() <= 0){
            bAdd.setVisible(false);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bAdd;
    private javax.swing.JLabel tName;
    private javax.swing.JLabel tValue;
    // End of variables declaration//GEN-END:variables
}
