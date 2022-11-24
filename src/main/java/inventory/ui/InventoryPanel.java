package inventory.ui;

import database.adapter.implementation.ItemDatabaseAdapter;
import exceptions.DatabaseException;
import inventory.Inventory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventory.objects.Item;
import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

import javax.swing.*;

public class InventoryPanel extends JPanel {

    Inventory inventory;

    HashMap<String, TextureSource> sourcesSlot = new HashMap<>();

    List<SlotPanel> slots;
    List<ItemPanel> items;

    ItemPanel draggedItem;

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
            sourcesSlot.put("default", new TextureSource(new File("src/main/resources/inventory/slot.png")));
            sourcesSlot.put("overlapped", new TextureSource(new File("src/main/resources/inventory/slot_overlapped.png")));

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

    private List<Integer> getChildrenIndexes(int index, ItemDescription description) {
        List<Integer> result = new ArrayList<>();
        int width = inventory.getWidth();
        int x = index % width;
        int y = index / width;

        for (int i = 0; i < description.sizeX(); i++) {
            for (int k = 0; k < description.sizeY(); k++) {
                result.add((x + i) + (y + k) * width);
            }
        }

        return result;
    }

    public void setDraggedItem(ItemPanel item) {
        draggedItem = item;
    }

    public void overlapItem(boolean enable, int index, ItemDescription description) {
        List<Integer> indexes = getChildrenIndexes(index, description);

        for (int i : indexes) {
            SlotPanel slot = slots.get(i);
            if (enable) {
                slot.applySource("overlapped");
            } else {
                slot.applySource("default");
            }
        }

        updateUI();
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
            SlotPanel slot = new SlotPanel(sourcesSlot);
            slots.add(slot);

            int x = i % width;
            int y = i / width;

            slot.setBounds(x * 32, y * 32, 32, 32);
            add(slot);
            //add(slot, new org.netbeans.lib.awtextra.AbsoluteConstraints(x * 32, y * 32, 32, 32));
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
                ItemPanel itemUI = new ItemPanel(getItemSource(item), description, this);
                itemUI.setIndex(i);
                items.add(itemUI);

                int x = i % width;
                int y = i / width;

                itemUI.setBounds(x * 32, y * 32, 32 * description.sizeX(), 32 * description.sizeY());
                add(itemUI);
                //add(itemUI, new org.netbeans.lib.awtextra.AbsoluteConstraints(x * 32, y * 32, 32 * description.sizeX(), 32 * description.sizeY()));
            }
        }

        updateUI();
    }

    private void initComponents() {
        setBackground(new java.awt.Color(150, 150, 150));
        setLayout(null);
    }


    public void dragItem(java.awt.event.MouseEvent evt) {
        if (draggedItem != null) {
            int x = -getLocationOnScreen().x + evt.getXOnScreen();
            int y = -getLocationOnScreen().y + evt.getYOnScreen();
            draggedItem.setLocation(x, y);
        }
    }


}
