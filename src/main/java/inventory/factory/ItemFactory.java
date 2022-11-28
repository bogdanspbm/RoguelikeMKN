package inventory.factory;

import database.adapter.implementation.ItemDatabaseAdapter;
import inventory.objects.Item;
import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

import java.io.File;
import java.util.HashMap;

public class ItemFactory {

    private ItemDatabaseAdapter itemDatabaseAdapter;

    private HashMap<Integer, ItemDescription> descriptionMap;
    private HashMap<String, TextureSource> textureSourceMap;

    public void itemFactory() {
        try {
            itemDatabaseAdapter = new ItemDatabaseAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ItemDescription getDescription(int id) {
        if (descriptionMap.containsKey(id)) {
            return descriptionMap.get(id);
        }

        try {
            ItemDescription description = itemDatabaseAdapter.getItemByID(id);
            descriptionMap.put(id, description);
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private TextureSource getTextureSource(String path) {
        if (textureSourceMap.containsKey(path)) {
            return textureSourceMap.get(path);
        }

        try {
            TextureSource source = new TextureSource(new File(path));
            textureSourceMap.put(path, source);
            return source;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Item createItem(int id, int quantity) {
        Item result = new Item(id, quantity);
        ItemDescription description = getDescription(id);
        result.setTextureSource(getTextureSource(description.texturePath()));
        return result;
    }
}
