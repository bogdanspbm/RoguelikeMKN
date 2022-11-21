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

        updateUI();
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
        setLayout(new java.awt.GridBagLayout());
    }

    private void generateItems() {
        int width = inventory.getWidth();
        java.awt.GridBagConstraints gridBagConstraints;

        for (int i = 0; i < inventory.getItems().length; i++) {
            gridBagConstraints = new GridBagConstraints();

            int x = i % width;
            int y = i / width;

            gridBagConstraints.gridx = x;
            gridBagConstraints.gridy = y;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;

            PanelSlot slot = new PanelSlot(sourceSlot, inventory.getItems()[i]);
            slot.setPreferredSize(new Dimension(64, 64));
            slot.setMinimumSize(new Dimension(64, 64));

            slots.add(slot);
            add(slot, gridBagConstraints);
        }
    }
}
