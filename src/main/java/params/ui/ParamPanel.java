package params.ui;

import database.adapter.implementation.ParamsDatabaseAdapter;
import java.util.ArrayList;
import java.util.List;
import params.ParamsComponent;
import ui.buttons.ProgressBar;

public class ParamPanel extends javax.swing.JPanel {

    ParamsDatabaseAdapter paramsAdapter;
    ParamsComponent component;
    
    List<ParamBar> bars = new ArrayList<>();

    public ParamPanel(ParamsComponent component) {
        initComponents();
        this.component = component;

        try {
            paramsAdapter = new ParamsDatabaseAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        generateParams();

        ((ProgressBar) (pProgress)).setPercent(component.getLevelPercentage());
        tLevel.setText(component.getLevel() + "");
    }

    private void generateParams() {
        if (paramsAdapter == null) {
            return;
        }

        try {
            for (String name : paramsAdapter.getParamsList()) {
                ParamBar bar = new ParamBar(name, component);
                bar.setParent(this);
                pParams.add(bar);
                bars.add(bar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(){
        for(ParamBar bar : bars){
            bar.update();
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
        tParams = new javax.swing.JLabel();
        pHorizontal = new javax.swing.JPanel();
        pProgress = new ProgressBar();
        tLevel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        pParams.setBackground(new java.awt.Color(51, 51, 51));
        pParams.setLayout(new javax.swing.BoxLayout(pParams, javax.swing.BoxLayout.Y_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(pParams, gridBagConstraints);

        pSplit.setBackground(new java.awt.Color(102, 102, 102));
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

        pStats.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pStatsLayout = new javax.swing.GroupLayout(pStats);
        pStats.setLayout(pStatsLayout);
        pStatsLayout.setHorizontalGroup(
                pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 199, Short.MAX_VALUE)
        );
        pStatsLayout.setVerticalGroup(
                pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 214, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(pStats, gridBagConstraints);

        pInfoHeader.setBackground(new java.awt.Color(51, 51, 51));
        pInfoHeader.setMaximumSize(new java.awt.Dimension(32767, 86));
        pInfoHeader.setMinimumSize(new java.awt.Dimension(100, 86));
        pInfoHeader.setPreferredSize(new java.awt.Dimension(199, 86));

        javax.swing.GroupLayout pInfoHeaderLayout = new javax.swing.GroupLayout(pInfoHeader);
        pInfoHeader.setLayout(pInfoHeaderLayout);
        pInfoHeaderLayout.setHorizontalGroup(
                pInfoHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 199, Short.MAX_VALUE)
        );
        pInfoHeaderLayout.setVerticalGroup(
                pInfoHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 86, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pInfoHeader, gridBagConstraints);

        pParamHeader.setBackground(new java.awt.Color(51, 51, 51));
        pParamHeader.setMaximumSize(new java.awt.Dimension(32767, 86));
        pParamHeader.setMinimumSize(new java.awt.Dimension(100, 86));
        pParamHeader.setPreferredSize(new java.awt.Dimension(199, 86));
        pParamHeader.setLayout(new java.awt.GridBagLayout());

        tParams.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        tParams.setForeground(new java.awt.Color(204, 204, 0));
        tParams.setText("Params");
        pParamHeader.add(tParams, new java.awt.GridBagConstraints());

        pHorizontal.setMaximumSize(new java.awt.Dimension(32767, 32));
        pHorizontal.setMinimumSize(new java.awt.Dimension(100, 32));
        pHorizontal.setOpaque(false);
        pHorizontal.setPreferredSize(new java.awt.Dimension(32, 32));
        pHorizontal.setLayout(new java.awt.GridBagLayout());

        pProgress.setBackground(new java.awt.Color(200, 200, 200));
        pProgress.setMaximumSize(new java.awt.Dimension(32767, 16));
        pProgress.setMinimumSize(new java.awt.Dimension(100, 16));
        pProgress.setOpaque(false);
        pProgress.setPreferredSize(new java.awt.Dimension(4, 16));

        javax.swing.GroupLayout pProgressLayout = new javax.swing.GroupLayout(pProgress);
        pProgress.setLayout(pProgressLayout);
        pProgressLayout.setHorizontalGroup(
                pProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        pProgressLayout.setVerticalGroup(
                pProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 6);
        pHorizontal.add(pProgress, gridBagConstraints);

        tLevel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tLevel.setForeground(new java.awt.Color(250, 250, 250));
        tLevel.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        pHorizontal.add(tLevel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pParamHeader.add(pHorizontal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pParamHeader, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pHorizontal;
    private javax.swing.JPanel pInfoHeader;
    private javax.swing.JPanel pParamHeader;
    private javax.swing.JPanel pParams;
    private javax.swing.JPanel pProgress;
    private javax.swing.JPanel pSplit;
    private javax.swing.JPanel pStats;
    private javax.swing.JLabel tLevel;
    private javax.swing.JLabel tParams;
    // End of variables declaration//GEN-END:variables
}
