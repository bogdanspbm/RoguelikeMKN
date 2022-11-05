package objects.collision;

import exceptions.SetException;
import interfaces.Collidable;
import interfaces.Placeable;

public class CollisionAdapter {
    Collidable owner;

    public CollisionAdapter(Collidable owner) {
        this.owner = owner;
    }

    public void setCollision(Collision collision) {
        owner.setCollision(collision);
        collision.setOwner(owner);
    }
}
