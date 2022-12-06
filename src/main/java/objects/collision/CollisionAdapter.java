package objects.collision;

import exceptions.SetException;
import interfaces.Collidable;
import interfaces.Placeable;

public class CollisionAdapter {
    private Collidable owner;
    private Collision collision;

    public CollisionAdapter(Collidable owner) {
        this.owner = owner;
    }

    public void setCollision(Collision collision) {
        this.collision = collision;
        collision.setOwner(owner);
    }

    public Collision getCollision() {
        return collision;
    }
}
