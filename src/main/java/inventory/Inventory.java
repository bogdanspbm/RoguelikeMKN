package inventory;

import inventory.objects.Item;
import inventory.objects.ItemDescription;
import inventory.objects.Slot;
import inventory.utils.ItemDescriptionProvider;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Slot> items;

    private int width = 10;
    private int height = 5;

    ItemDescriptionProvider descriptionProvider;

    public Inventory() {
        generateInventory();

        try {
            descriptionProvider = new ItemDescriptionProvider();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateInventory() {
        items = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            items.add(new Slot(null, i, true));
        }
    }

    public void addItem(Item item) {
        try {
            ItemDescription itemDescription = descriptionProvider.getDescription(item.getId());

            int freeIndex = findFreePlace(item.getId(), itemDescription);
            while (item.getQuantity() > 0 && freeIndex != -1) {
                addItemToSlot(item, freeIndex, itemDescription);
                freeIndex = findFreePlace(item.getId(), itemDescription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean moveItemToSlot(int fromIndex, int toIndex) {
        if (items.get(fromIndex).getItem() == null) {
            return false;
        }

        int itemID = items.get(fromIndex).getItem().getId();

        try {
            ItemDescription description = descriptionProvider.getDescription(itemID);

            if (checkFreeSlot(toIndex, description)) {
                addItemToSlot(items.get(fromIndex).getItem(), toIndex, description);
                clearSlots(getChildrenIndexes(fromIndex, description));
                return true;
            }

            if (checkSameSlot(toIndex, description)) {
                return swapItemsFromSlots(fromIndex, toIndex);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean swapItemsFromSlots(int first, int second) {
        if (items.get(first).getItem() == items.get(second).getItem()) {
            return false;
        }

        Item tmp = items.get(first).getItem();
        items.get(first).setItem(items.get(second).getItem());
        items.get(second).setItem(tmp);

        return true;
    }

    public void dropItem(int index) {
        if (items.get(index).getItem() == null) {
            return;
        }

        int itemId = items.get(index).getItem().getId();

        try {
            ItemDescription description = descriptionProvider.getDescription(itemId);
            dropItem(index, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropItem(int index, ItemDescription description) {
        clearSlots(getChildrenIndexes(index, description));
    }

    private void clearSlots(List<Integer> indexes) {
        for (int i : indexes) {
            items.get(i).setItem(null);
        }
    }

    private boolean checkFreeSlot(int index, ItemDescription description) {
        List<Integer> indexes = getChildrenIndexes(index, description);
        boolean flag = true;

        for (int el : indexes) {
            if (el >= items.size()) {
                flag = false;
                break;
            }

            if (items.get(el).getItem() != null) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private boolean checkEqualSlot(int index, ItemDescription description) {
        List<Integer> indexes = getChildrenIndexes(index, description);
        boolean flag = true;

        for (int el : indexes) {
            if (el >= items.size()) {
                flag = false;
                break;
            }

            if (items.get(el).getItem() == null || items.get(el).getItem().getId() != description.id() || items.get(el).getItem().getQuantity() >= description.stackSize()) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private boolean checkSameSlot(int index, ItemDescription description) {
        List<Integer> indexes = getChildrenIndexes(index, description);
        boolean flag = true;

        for (int el : indexes) {
            if (el >= items.size()) {
                flag = false;
                break;
            }

            if (items.get(el).getItem() == null || items.get(el).getItem().getId() != description.id()) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private int findFreePlace(int id, ItemDescription description) {
        int index = findFreePlaceWithID(id, description);
        if (index != -1) {
            return index;
        }

        index = findFreeEmptyPlace(description);
        return index;
    }

    private void addItemToSlot(Item item, int index, ItemDescription description) {
        Item slotItem = items.get(index).getItem();

        if (slotItem == null) {
            slotItem = new Item(item.getId(), 0);
        }

        int freeSpace = description.stackSize() - slotItem.getQuantity();
        int amountToAdd = Math.min(freeSpace, item.getQuantity());

        slotItem.setQuantity(slotItem.getQuantity() + amountToAdd);
        item.setQuantity(item.getQuantity() - amountToAdd);

        for (int i : getChildrenIndexes(index, description)) {
            items.get(i).setItem(slotItem);
            items.get(i).setParentIndex(index);
            items.get(i).setIsParent(index == i);
        }
    }

    private int findFreePlaceWithID(int id, ItemDescription description) {
        int index = -1;

        for (int i = 0; i < items.size(); i++) {
            boolean flag = checkEqualSlot(i, description);

            if (flag) {
                return i;
            }
        }

        return index;
    }

    private int findFreeEmptyPlace(ItemDescription description) {
        int index = -1;

        for (int i = 0; i < items.size(); i++) {
            boolean flag = checkFreeSlot(i, description);

            if (flag) {
                return i;
            }
        }

        return index;
    }

    public List<Integer> getChildrenIndexes(int index, ItemDescription description) {
        List<Integer> result = new ArrayList<>();
        int width = getWidth();
        int x = index % width;
        int y = index / width;

        for (int i = 0; i < description.sizeX(); i++) {
            for (int k = 0; k < description.sizeY(); k++) {
                result.add((x + i) + (y + k) * width);
            }
        }

        return result;
    }

    public int getItemCount() {
        int counter = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItem() != null && items.get(i).getParentIndex() == i) {
                counter++;
            }
        }
        return counter;
    }

    public Item getItemByIndex(int index) {
        return items.get(index).getItem();
    }

    public List<Slot> getItems() {
        return items;
    }

    public int getWidth() {
        return width;
    }
}
