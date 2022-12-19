package objects.buff.factory;

import inventory.objects.ItemDescription;
import org.json.JSONObject;

import java.util.Random;

public class BuffBuilderFactory {
    public BuffBuilderFactory() {

    }

    public BuffBuilder createBuilder(ItemDescription description) {
        JSONObject meta = description.meta();

        if (!meta.has("buff")) {
            return null;
        }

        if (!meta.has("value")) {
            return null;
        }

        int chance = meta.getInt("value");
        Random random = new Random();

        if (random.nextInt(100) > chance) {
            return null;
        }

        String name = meta.getString("buff");
        BuffBuilder builder = new BuffBuilder();
        builder.setName(name);

        return builder;
    }

    public BuffBuilder createBuilder(ItemDescription description, int seed) {
        JSONObject meta = description.meta();

        if (!meta.has("buff")) {
            return null;
        }

        if (!meta.has("value")) {
            return null;
        }

        int chance = meta.getInt("value");

        if (seed > chance) {
            return null;
        }

        String name = meta.getString("buff");
        BuffBuilder builder = new BuffBuilder();
        builder.setName(name);

        return builder;
    }


}
