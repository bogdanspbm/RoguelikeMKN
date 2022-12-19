package objects.spawner;

import structures.Vector3D;
import world.map.Map;

public class Spawner {
    private Vector3D location;
    protected Map parent;

    public Vector3D getLocation() {
        return location;
    }

    public void setLocation(Vector3D location) {
        this.location = location;
    }

    public void setParent(Map map) {
        parent = map;
    }
}
