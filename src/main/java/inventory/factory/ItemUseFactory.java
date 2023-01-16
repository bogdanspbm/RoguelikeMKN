package inventory.factory;

import inventory.objects.ItemDescription;
import inventory.objects.ItemUseAction;
import inventory.objects.implementation.ItemEmptyAction;
import inventory.objects.implementation.ItemUseHeal;
import org.json.JSONObject;

public class ItemUseFactory {

    public ItemUseAction generateAction(JSONObject meta) {
        ItemUseAction result = new ItemEmptyAction(0);

        if (!meta.has("action")) {
            return result;
        }

        if (!meta.has("value")) {
            return result;
        }

        switch (meta.getString("action")) {
            case "heal": {
                result = new ItemUseHeal(meta.getInt("value"));
            }
        }

        return result;
    }
}
