package inventory.ui;

import inventory.Inventory;
import objects.animations.objects.TextureSource;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PanelInventory extends JPanel {

    Inventory inventory;

    TextureSource sourcePanel;
    TextureSource sourceSlot;

    List<PanelSlot> slots = new ArrayList<>();

    public PanelInventory(Inventory inventory) {
        this.inventory = inventory;

        initSources();

        initComponent();
        generateItems();


    }

    private void initSources() {
        try {
            sourcePanel = new TextureSource(new File("src/main/resources/inventory/panel.png"));
            sourceSlot = new TextureSource(new File("src/main/resources/inventory/slot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponent() {
        setBackground(new Color(100, 100, 100));
    }

    private void generateItems() {
        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {

            int x = i % width;
            int y = i / width;

            PanelSlot slot = new PanelSlot(sourceSlot, inventory.getItems().get(i));

            slots.add(slot);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {

            int x = i % width;
            int y = i / width;

            g.drawImage(sourceSlot.getImage(), x * 64, y * 64, null);
        }
    }
}
