package inventory.ui.popup;

import inventory.objects.ItemDescription;
import inventory.ui.ItemPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ItemPopup extends JPopupMenu {

    ItemPanel parent;

    public ItemPopup(ItemPanel parent) {
        this.parent = parent;
        initPopup();
    }

    private void initPopup() {
        setBorderPainted(false);

        if (parent == null) {
            return;
        }

        ItemDescription description = parent.getDescription();
        generateDropEvent();

    }

    private void generateDropEvent() {
        JMenuItem itemDrop = new JMenuItem((new AbstractAction("Drop") {
            public void actionPerformed(ActionEvent e) {
                parent.dropItem();
            }
        }));

        itemDrop.setBorderPainted(false);

        add(itemDrop);
    }


}
