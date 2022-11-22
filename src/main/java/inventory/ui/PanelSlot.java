package inventory.ui;

import inventory.objects.Item;
import inventory.objects.Slot;
import objects.animations.objects.TextureSource;

import javax.swing.*;
import java.awt.*;

public class PanelSlot extends JPanel {
    TextureSource sourceBG;
    TextureSource sourceItem;
    Slot slot;

    public PanelSlot(TextureSource sourceBG, Slot slot, TextureSource sourceItem) {
        this.sourceBG = sourceBG;
        this.sourceItem = sourceItem;
        this.slot = slot;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sourceBG.getImage(), 0, 0, null);

        if (slot.getIsParent()) {
            g.drawImage(sourceItem.getImage(), 0, 0, null);
        }
    }

}
