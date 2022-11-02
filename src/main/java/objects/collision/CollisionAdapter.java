package objects.collision;

import exceptions.SetException;
import interfaces.Collidable;
import interfaces.Placeable;

public class CollisionAdapter {
    Placeable owner;

    public CollisionAdapter(Placeable owner) {
        this.owner = owner;
    }

    public void setCollision(Collision collision) throws SetException {
        try {
            ((Collidable) (owner)).setCollision(collision);
            collision.setOwner(owner);
        } catch (Exception e) {
            throw new SetException("Can't set collision exception: \n" + e.toString());
        }
    }
}
