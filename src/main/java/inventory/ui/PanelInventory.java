package inventory.ui;

import database.adapter.implementation.ItemDatabaseAdapter;
import exceptions.DatabaseException;
import inventory.Inventory;
import inventory.objects.ItemDescription;
import inventory.objects.Slot;
import objects.animations.objects.TextureSource;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PanelInventory extends JPanel {

    Inventory inventory;

    TextureSource sourcePanel;
    TextureSource sourceSlot;

    HashMap<String, TextureSource> sourceMap = new HashMap<>();

    List<PanelSlot> slots = new ArrayList<>();

    ItemDatabaseAdapter adapter;

    public PanelInventory(Inventory inventory) throws DatabaseException {
        this.inventory = inventory;
        this.adapter = new ItemDatabaseAdapter();

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

    private TextureSource getItemSource(Slot item) {
        if (item.getItem() == null) {
            return null;
        }

        try {
            ItemDescription desc = adapter.getItemByID(item.getItem().getId());
            String key = desc.texturePath();
            if (sourceMap.containsKey(key)) {
                return sourceMap.get(key);
            } else {
                TextureSource source = new TextureSource(new File(key));
                sourceMap.put(key, source);
                return source;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initComponent() {
        setBackground(new Color(100, 100, 100));
    }

    private void generateItems() {
        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {

            int x = i % width;
            int y = i / width;

            PanelSlot slot = new PanelSlot(sourceSlot, inventory.getItems().get(i), getItemSource(inventory.getItems().get(i)));

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

            g.drawImage(sourceSlot.getImage(), x * 32, y * 32, null);
        }

        for (int i = 0; i < inventory.getItems().size(); i++) {

            int x = i % width;
            int y = i / width;

            if (inventory.getItems().get(i).getIsParent() && getItemSource(inventory.getItems().get(i)) != null) {
                g.drawImage(getItemSource(inventory.getItems().get(i)).getImage(), x * 32, y * 32, null);
            }
        }
    }
}
