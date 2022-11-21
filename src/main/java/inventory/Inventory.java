package inventory;

import inventory.objects.Item;
import inventory.objects.ItemDescription;
import inventory.utils.ItemDescriptionProvider;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private Item[] items;

    private int sizeX = 10;
    private int sizeY = 5;

    public Inventory() {
        generateInventory();
    }

    private void generateInventory() {
        items = new Item[sizeX * sizeY];
    }

    public void addItem(Item item) {

        ItemDescriptionProvider descriptionProvider = new ItemDescriptionProvider();
        ItemDescription itemDescription = descriptionProvider.getDescription(item.getId());

        int freeIndex = findFreePlace(item.getId(), itemDescription);
        while (item.getQuantity() > 0 && freeIndex != -1) {
            addItemToSlot(item, freeIndex, itemDescription);
            freeIndex = findFreePlace(item.getId(), itemDescription);
        }
    }

    private int findFreePlace(int id, ItemDescription description) {
        int index = findFreePlaceWithID(id, description);
        if (index != -1) {
            return index;
        }

        index = findFreeEmptyPlace();
        return index;
    }

    private void addItemToSlot(Item item, int index, ItemDescription description) {
        Item slot = items[index];

        if (slot == null) {
            slot = new Item(0, 0);
        }

        int freeSpace = description.stackSize() - slot.getQuantity();
        int amountToAdd = Math.min(freeSpace, item.getQuantity());

        slot.setQuantity(slot.getQuantity() + amountToAdd);
        item.setQuantity(item.getQuantity() - amountToAdd);

        items[index] = slot;
    }

    private int findFreePlaceWithID(int id, ItemDescription description) {
        int index = -1;

        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getId() == id && items[i].getQuantity() < description.stackSize()) {
                return i;
            }
        }

        return index;
    }

    private int findFreeEmptyPlace() {
        int index = -1;

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                return i;
            }
        }

        return index;
    }

    public int getItemCount() {
        int counter = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    public Item getItemByIndex(int index) {
        return items[index];
    }
}
