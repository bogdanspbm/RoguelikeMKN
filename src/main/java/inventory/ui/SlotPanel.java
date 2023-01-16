package inventory.ui;

import objects.animations.objects.TextureSource;
import utils.ImageUtils;

import java.awt.*;
import java.util.HashMap;


public class SlotPanel extends javax.swing.JPanel {

    private HashMap<String, TextureSource> sources;

    public SlotPanel(HashMap<String, TextureSource> sources) {
        this.sources = sources;
        initComponents();
    }

    private void initComponents() {

        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(200, 200, 200));
        setMaximumSize(new java.awt.Dimension(32, 32));
        setMinimumSize(new java.awt.Dimension(32, 32));
        setLayout(new java.awt.GridBagLayout());

        background.setIcon(new javax.swing.ImageIcon(sources.get("default").getImage()));
        add(background, new java.awt.GridBagConstraints());
    }

    public void applySource(String source) {
        if (sources.containsKey(source)) {
            background.setIcon(new javax.swing.ImageIcon(sources.get(source).getImage()));
        }
    }


    private javax.swing.JLabel background;

}
