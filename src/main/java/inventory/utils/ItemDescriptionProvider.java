package inventory.utils;

import database.adapter.implementation.ItemDatabaseAdapter;
import exceptions.DatabaseException;
import inventory.objects.ItemDescription;

public class ItemDescriptionProvider {

    ItemDatabaseAdapter adapter;

    public ItemDescriptionProvider() throws DatabaseException {
        adapter = new ItemDatabaseAdapter();
    }

    public ItemDescription getDescription(int id) throws DatabaseException {
        return adapter.getItemByID(id);
    }
}
