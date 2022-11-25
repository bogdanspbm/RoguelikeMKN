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
import inventory.objects.Slot;
import objects.animations.objects.TextureSource;

import javax.swing.*;

public class InventoryPanel extends JPanel {

    Inventory inventory;

    HashMap<String, TextureSource> sourcesSlot = new HashMap<>();

    List<SlotPanel> slots = new ArrayList<>();
    List<ItemPanel> items = new ArrayList<>();

    HashMap<String, TextureSource> sourceMap = new HashMap<>();
    HashMap<Integer, ItemDescription> descMap = new HashMap<>();
    ItemDatabaseAdapter adapter;


    public InventoryPanel(Inventory inventory) throws DatabaseException {
        this.inventory = inventory;
        this.adapter = new ItemDatabaseAdapter();

        initSources();
        initComponents();
        update();
    }

    private void initSources() {
        try {
            sourcesSlot.put("default", new TextureSource(new File("src/main/resources/inventory/slot.png")));
            sourcesSlot.put("overlapped", new TextureSource(new File("src/main/resources/inventory/slot_overlapped.png")));
            sourcesSlot.put("free", new TextureSource(new File("src/main/resources/inventory/slot_free.png")));
            sourcesSlot.put("blocked", new TextureSource(new File("src/main/resources/inventory/slot_blocked.png")));
            sourcesSlot.put("merge", new TextureSource(new File("src/main/resources/inventory/slot_merge.png")));
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


    public ItemPanel getDraggedItem() {
        return draggedItem;
    }

    public void setDraggedItem(MouseEvent evt, ItemPanel item) {
        if (item != null) {
            dragX = evt.getXOnScreen() - item.getLocationOnScreen().x;
            dragY = evt.getYOnScreen() - item.getLocationOnScreen().y;
        }
        draggedItem = item;
    }

    public void overlapItem(boolean enable, int index, ItemDescription description) {
        List<Integer> indexes = inventory.getChildrenIndexes(index, description);

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

        for (SlotPanel slot : slots) {
            remove(slot);
        }

        slots = new ArrayList<>();

        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {
            SlotPanel slot = new SlotPanel(sourcesSlot);
            slots.add(slot);

            int x = i % width;
            int y = i / width;

            slot.setBounds(x * 32, y * 32, 32, 32);
            add(slot);
        }

        updateUI();
    }


    private void generateItems() {
        for (ItemPanel item : items) {
            remove(item);
        }

        items = new ArrayList<>();

        int width = inventory.getWidth();

        for (int i = 0; i < inventory.getItems().size(); i++) {
            Item item = inventory.getItems().get(i).getItem();
            if (item != null && inventory.getItems().get(i).getIsParent()) {
                ItemDescription description = getItemDescription(item);
                ItemPanel itemUI = new ItemPanel(i, getItemSource(item), description, this);
                itemUI.setIndex(i);
                items.add(itemUI);

                int x = i % width;
                int y = i / width;

                itemUI.setBounds(x * 32, y * 32, 32 * description.sizeX(), 32 * description.sizeY());
                add(itemUI);
            }
        }

        updateUI();
    }

    private void redrawItemFromSlot(int index) {

        ItemPanel toRemove = null;

        for (ItemPanel panel : items) {
            if (panel.getIndex() == index) {
                toRemove = panel;
                remove(panel);
            }
        }

        items.remove(toRemove);

        Item item = inventory.getItems().get(index).getItem();
        if (item != null && inventory.getItems().get(index).getIsParent()) {
            int width = inventory.getWidth();
            ItemDescription description = getItemDescription(item);
            ItemPanel itemUI = new ItemPanel(index, getItemSource(item), description, this);
            itemUI.setIndex(index);
            items.add(itemUI);

            int x = index % width;
            int y = index / width;

            itemUI.setBounds(x * 32, y * 32, 32 * description.sizeX(), 32 * description.sizeY());
            add(itemUI);
        }
    }


    private void initComponents() {
        setBackground(new java.awt.Color(150, 150, 150));
        setLayout(null);
    }


    private ItemPanel draggedItem;
    private int dragX, dragY;
    private int overlapIndex;
    private List<Integer> lastColored = new ArrayList<>();

    public void dragItem(java.awt.event.MouseEvent evt) {
        if (draggedItem != null) {
            int x = -getLocationOnScreen().x + evt.getXOnScreen() - dragX;
            int y = -getLocationOnScreen().y + evt.getYOnScreen() - dragY;
            draggedItem.setLocation(x, y);

            int index = x / 32 + y / 32 * inventory.getWidth();
            overlapIndex = index;

            clearCoveredSlots();
            overlapCoveredSlots(index);
        }
    }

    private void clearCoveredSlots() {
        for (int i : lastColored) {
            slots.get(i).applySource("default");
        }
    }

    private void overlapCoveredSlots(int index) {
        List<Integer> children = inventory.getChildrenIndexes(index, draggedItem.getDescription());

        String source = "free";
        if (isSimilarSlot(index)) {
            source = "merge";
        } else if (!canSwapDraggedItemTo(index)) {
            source = "blocked";
        }

        for (int i : children) {
            slots.get(i).applySource(source);
        }

        lastColored = children;
    }

    private boolean isSimilarSlot(int index) {
        if (inventory.getItems().get(index).getItem() == null) {
            return false;
        }
        return inventory.getItems().get(index).getItem().getId() == draggedItem.getDescription().id();
    }

    private boolean canSwapDraggedItemTo(int index) {
        List<Integer> children = inventory.getChildrenIndexes(index, draggedItem.getDescription());

        for (int i : children) {
            Slot slot = inventory.getItems().get(i);

            if (slot.getItem() != null) {
                return false;
            }
        }

        return true;
    }

    public void dragEnd(ItemPanel item) {
        int width = inventory.getWidth();
        int x, y;
        int index = item.getIndex();
        if (canSwapDraggedItemTo(overlapIndex)) {
            x = overlapIndex % width;
            y = overlapIndex / width;
            draggedItem.setLocation(x * 32, y * 32);

            if (inventory.moveItemToSlot(item.getIndex(), overlapIndex)) {
                item.setIndex(overlapIndex);
            }
        } else if (isSimilarSlot(overlapIndex) && index != overlapIndex) {
            if (inventory.moveItemToSlot(item.getIndex(), overlapIndex)) {
                update();
            }
        } else {
            x = item.getIndex() % width;
            y = item.getIndex() / width;
            draggedItem.setLocation(x * 32, y * 32);
        }
        clearCoveredSlots();
    }

    public void update(){
        generateItems();
        generateSlots();
    }

    public Inventory getInventory() {
        return inventory;
    }


}
