package inventory.utils;

import inventory.objects.ItemDescription;

public class ItemDescriptionProvider {

    public ItemDescription getDescription(int id) {
        ItemDescription res = new ItemDescription(0, 10, 2, 2);
        return res;
    }
}
