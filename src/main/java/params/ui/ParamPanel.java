package params.ui;

import database.adapter.implementation.ParamsDatabaseAdapter;
import params.ParamsComponent;

public class ParamPanel extends javax.swing.JPanel {

    ParamsDatabaseAdapter paramsAdapter;
    ParamsComponent component;

    public ParamPanel(ParamsComponent component) {
        initComponents();
        this.component = component;

        try {
            paramsAdapter = new ParamsDatabaseAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        generateParams();
    }

    private void generateParams() {
        if (paramsAdapter == null) {
            return;
        }

        try {
            for (String name : paramsAdapter.getParamsList()) {
                ParamBar bar = new ParamBar(name, component);
                pParams.add(bar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pParams = new javax.swing.JPanel();
        pSplit = new javax.swing.JPanel();
        pStats = new javax.swing.JPanel();
        pInfoHeader = new javax.swing.JPanel();
        pParamHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        pParams.setLayout(new javax.swing.BoxLayout(pParams, javax.swing.BoxLayout.Y_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(pParams, gridBagConstraints);

        pSplit.setBackground(new java.awt.Color(200, 200, 200));
        pSplit.setMaximumSize(new java.awt.Dimension(1, 32767));
        pSplit.setMinimumSize(new java.awt.Dimension(1, 100));
        pSplit.setPreferredSize(new java.awt.Dimension(1, 300));

        javax.swing.GroupLayout pSplitLayout = new javax.swing.GroupLayout(pSplit);
        pSplit.setLayout(pSplitLayout);
        pSplitLayout.setHorizontalGroup(
            pSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        pSplitLayout.setVerticalGroup(
            pSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(pSplit, gridBagConstraints);

        javax.swing.GroupLayout pStatsLayout = new javax.swing.GroupLayout(pStats);
        pStats.setLayout(pStatsLayout);
        pStatsLayout.setHorizontalGroup(
            pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );
        pStatsLayout.setVerticalGroup(
            pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(pStats, gridBagConstraints);

        pInfoHeader.setMaximumSize(new java.awt.Dimension(32767, 60));
        pInfoHeader.setMinimumSize(new java.awt.Dimension(100, 60));
        pInfoHeader.setPreferredSize(new java.awt.Dimension(199, 60));

        javax.swing.GroupLayout pInfoHeaderLayout = new javax.swing.GroupLayout(pInfoHeader);
        pInfoHeader.setLayout(pInfoHeaderLayout);
        pInfoHeaderLayout.setHorizontalGroup(
            pInfoHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );
        pInfoHeaderLayout.setVerticalGroup(
            pInfoHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pInfoHeader, gridBagConstraints);

        pParamHeader.setMaximumSize(new java.awt.Dimension(32767, 60));
        pParamHeader.setMinimumSize(new java.awt.Dimension(100, 60));
        pParamHeader.setPreferredSize(new java.awt.Dimension(199, 60));
        pParamHeader.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel1.setText("Params");
        pParamHeader.add(jLabel1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pParamHeader, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pInfoHeader;
    private javax.swing.JPanel pParamHeader;
    private javax.swing.JPanel pParams;
    private javax.swing.JPanel pSplit;
    private javax.swing.JPanel pStats;
    // End of variables declaration//GEN-END:variables
}
