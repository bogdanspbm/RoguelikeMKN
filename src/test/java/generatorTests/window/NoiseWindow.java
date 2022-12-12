package generatorTests.window;

import exceptions.DatabaseException;
import generator.PerlinNoiseGenerator;
import generator.ui.NoisePanel;
import params.ParamsComponent;
import params.ui.ParamPanel;
import paramsTests.window.ParamsWindow;

import javax.swing.*;

public class NoiseWindow extends JFrame {

    PerlinNoiseGenerator generator;

    public NoiseWindow() {
        this.generator = new PerlinNoiseGenerator(1, 64);
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() throws DatabaseException {
        java.awt.GridBagConstraints gridBagConstraints;

        pPanel = new NoisePanel(generator.getHeightMap(), 120);


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(500, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(pPanel, gridBagConstraints);

        pack();
    }

    public static void main(String[] args) {
        NoiseWindow window = new NoiseWindow();
        window.setVisible(true);
    }

    private NoisePanel pPanel;
}
