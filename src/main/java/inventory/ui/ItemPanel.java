package inventory.ui;

import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

import javax.swing.*;

public class ItemPanel extends JPanel {

    private TextureSource source;
    private ItemDescription description;
    private InventoryPanel parent;
    private int index = 0;

    public ItemPanel(TextureSource source, ItemDescription description, InventoryPanel parent) {
        this.source = source;
        this.description = description;
        this.parent = parent;

        initComponents();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        setOpaque(false);

        background = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32 * description.sizeX(), 32 * description.sizeY()));
        setMinimumSize(new java.awt.Dimension(32 * description.sizeX(), 32 * description.sizeY()));
        setLayout(new java.awt.GridBagLayout());

        background.setIcon(new javax.swing.ImageIcon(source.getImage()));
        add(background, new java.awt.GridBagConstraints());
    }

    private javax.swing.JLabel background;

    private void formMouseEntered(java.awt.event.MouseEvent evt) {
        parent.overlapItem(true, index, description);
    }

    private void formMouseExited(java.awt.event.MouseEvent evt) {
        parent.overlapItem(false, index, description);
    }
}
