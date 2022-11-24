package inventory.ui;

import database.adapter.implementation.ItemDatabaseAdapter;
import exceptions.DatabaseException;
import inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventory.objects.Item;
import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

public class InventoryPanel extends javax.swing.JPanel {

    Inventory inventory;

    TextureSource sourceSlot;

    List<SlotPanel> slots;
    List<ItemPanel> items;

    HashMap<String, TextureSource> sourceMap = new HashMap<>();
    HashMap<Integer, ItemDescription> descMap = new HashMap<>();
    ItemDatabaseAdapter adapter;

    public InventoryPanel(Inventory inventory) throws DatabaseException {
        this.inventory = inventory;
        this.adapter = new ItemDatabaseAdapter();

        initSources();
        initComponents();
        generateItems();
        generateSlots();
    }

    private void initSources() {
        try {
            sourceSlot = new TextureSource(new File("src/main/resources/inventory/slot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ItemDescription getItemDescription(Item item) {
        if (item == null) {
            return null;
        }

        try {
            ItemDescription desc;
            if (!descMap.containsKey(item.getId())) {
                desc = adapter.getItemByID(item.getId());
                descMap.put(item.getId(), desc);
            } else {
                desc = descMap.get(item.getId());
            }
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private TextureSource getItemSource(Item item) {
        if (item == null) {
            return null;
        }

        try {
            ItemDescription desc = getItemDescription(item);
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

    private void generateSlots() {
        slots = new ArrayList<>();

        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {
            SlotPanel slot = new SlotPanel(sourceSlot);
            slots.add(slot);

            int x = i % width;
            int y = i / width;

            add(slot, new org.netbeans.lib.awtextra.AbsoluteConstraints(x * 32, y * 32, 32, 32));
        }

        updateUI();
    }


    private void generateItems() {
        items = new ArrayList<>();

        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {
            Item item = inventory.getItems().get(i).getItem();
            if (item != null && inventory.getItems().get(i).getIsParent()) {
                ItemDescription description = getItemDescription(item);
                ItemPanel itemUI = new ItemPanel(getItemSource(item), description);
                items.add(itemUI);

                int x = i % width;
                int y = i / width;

                add(itemUI, new org.netbeans.lib.awtextra.AbsoluteConstraints(x * 32, y * 32, 32 * description.sizeX(), 32 * description.sizeY()));
            }
        }

        updateUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(150, 150, 150));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
