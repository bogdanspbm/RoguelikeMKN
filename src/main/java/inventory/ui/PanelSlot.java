package inventory.ui;

import inventory.objects.Item;
import objects.animations.objects.TextureSource;

import javax.swing.*;
import java.awt.*;

public class PanelSlot extends JPanel {
    TextureSource source;
    Item item;

    public PanelSlot(TextureSource source, Item item) {
        this.source = source;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(source.getImage(), 0, 0, null);
    }

}
