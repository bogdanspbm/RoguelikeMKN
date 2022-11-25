package inventory.ui;

import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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

    public ItemDescription getDescription() {
        return description;
    }

    public int getIndex() {
        return index;
    }

    private void initComponents() {

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                formMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }

        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMouseClick(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseRelease(evt);
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

    private void formMouseClick(java.awt.event.MouseEvent evt) {
        parent.setDraggedItem(evt, this);
        parent.overlapItem(false, index, description);
    }

    private void formMouseRelease(java.awt.event.MouseEvent evt) {
        parent.dragEnd(this);
        parent.setDraggedItem(evt, null);
    }

    private void formMouseDragged(java.awt.event.MouseEvent evt) {
        parent.dragItem(evt);
    }


}
