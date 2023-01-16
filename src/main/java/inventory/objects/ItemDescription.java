package inventory.objects;

import org.json.JSONObject;

public record ItemDescription(int id, int stackSize, int sizeX, int sizeY, String name, String texturePath, String description, JSONObject meta) {
}
