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

    public Inventory() {
        generateInventory();
    }

    private void generateInventory() {
        items = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            items.add(new Slot(null, i, true));
        }
    }

    public void addItem(Item item) {
        try {
            ItemDescriptionProvider descriptionProvider = new ItemDescriptionProvider();
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
            List<Integer> indexes = getChildrenIndexes(i, description);
            boolean flag = true;

            for (int el : indexes) {
                if (el >= items.size()) {
                    flag = false;
                    break;
                }

                if (items.get(i).getItem() == null || items.get(i).getItem().getId() != id || items.get(i).getItem().getQuantity() >= description.stackSize()) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return i;
            }
        }

        return index;
    }

    private int findFreeEmptyPlace(ItemDescription description) {
        int index = -1;

        for (int i = 0; i < items.size(); i++) {
            List<Integer> indexes = getChildrenIndexes(i, description);
            boolean flag = true;

            for (int el : indexes) {
                if (el >= items.size()) {
                    flag = false;
                    break;
                }

                if (items.get(i).getItem() != null) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return i;
            }
        }

        return index;
    }

    private List<Integer> getChildrenIndexes(int index, ItemDescription description) {
        List<Integer> result = new ArrayList<>();

        int curX = index % width;
        int curY = index / width;
        for (int i = 0; i < description.sizeX(); i++) {
            for (int k = 0; k < description.sizeY(); k++) {
                int x = curX + i;
                int y = curY + k;

                result.add(x + y * width);
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
