package inventory.ui;

import inventory.objects.ItemDescription;
import inventory.ui.popup.ItemPopup;
import objects.animations.objects.TextureSource;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ItemPanel extends JPanel {

    private TextureSource source;
    private ItemDescription description;
    private InventoryPanel parent;

    ItemPopup popup = null;
    private int index = 0;
    private boolean wasDragged = false;

    public ItemPanel(int index, TextureSource source, ItemDescription description, InventoryPanel parent) {
        this.source = source;
        this.description = description;
        this.parent = parent;
        this.index = index;

        initComponents();
    }

    public void setIndex(int index) {
        this.index = index;
        setCount();
    }

    private void setCount() {
        int count = parent.inventory.getItems().get(index).getItem().getQuantity();
        if (count == 1) {
            tCount.setVisible(false);
        } else {
            tCount.setVisible(true);
            tCount.setText(count + "");
        }
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
                formMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseRelease(evt);
            }

            public void mouseClicked(MouseEvent e) {
                formMouseClick(e);
            }
        });

        setOpaque(false);
        java.awt.GridBagConstraints gridBagConstraints;

        background = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32 * description.sizeX(), 32 * description.sizeY()));
        setMinimumSize(new java.awt.Dimension(32 * description.sizeX(), 32 * description.sizeY()));
        setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 2);

        tCount = new javax.swing.JLabel();
        tCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tCount.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        tCount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tCount.setForeground(new java.awt.Color(255, 255, 255));
        tCount.setText("1");
        add(tCount, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        background.setIcon(new javax.swing.ImageIcon(source.getImage()));
        add(background, gridBagConstraints);
    }

    private javax.swing.JLabel background;
    private javax.swing.JLabel tCount;

    private void formMouseEntered(java.awt.event.MouseEvent evt) {
        parent.overlapItem(true, index, description);
    }

    private void formMouseExited(java.awt.event.MouseEvent evt) {
        parent.overlapItem(false, index, description);
    }

    private void formMouseClick(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == 3) {
            openSelectionList(evt);
        }
    }

    private void formMousePressed(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == 1) {
            parent.setDraggedItem(evt, this);
            parent.overlapItem(false, index, description);
        }
    }

    private void formMouseRelease(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == 1 && equals(parent.getDraggedItem()) && wasDragged) {
            parent.dragEnd(this);
            parent.setDraggedItem(evt, null);
            wasDragged = false;
        }
    }

    private void formMouseDragged(java.awt.event.MouseEvent evt) {
        wasDragged = true;
        parent.dragItem(evt);
    }

    private void openSelectionList(java.awt.event.MouseEvent evt) {
        popup = new ItemPopup(this);

        int x = evt.getXOnScreen() - getLocationOnScreen().x;
        int y = evt.getYOnScreen() - getLocationOnScreen().y;

        popup.show(this, x, y);
    }

    public void dropItem() {
        parent.getInventory().dropItem(index, description);
        parent.update();
    }


}
