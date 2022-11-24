package inventory.ui;

import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

import javax.swing.*;

public class ItemPanel extends JPanel {

    private TextureSource source;
    private ItemDescription description;

    public ItemPanel(TextureSource source, ItemDescription description) {
        this.source = source;
        this.description = description;

        initComponents();
    }

    private void initComponents() {

        setOpaque(false);

        background = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32 * description.sizeX(), 32 * description.sizeY()));
        setMinimumSize(new java.awt.Dimension(32 * description.sizeX(), 32 * description.sizeY()));
        setLayout(new java.awt.GridBagLayout());

        background.setIcon(new javax.swing.ImageIcon(source.getImage()));
        add(background, new java.awt.GridBagConstraints());
    }

    private javax.swing.JLabel background;
}
