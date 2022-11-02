package objects.collision;

import enums.ECollisionType;
import interfaces.Placeble;
import structures.Vector3D;

public abstract class Collision {

    protected Placeble owner;

    protected ECollisionType type;

    public void setOwner(Placeble owner) {
        this.owner = owner;
    }

    public ECollisionType getType() {
        return type;
    }

    public Vector3D getLocation() {
        if (owner != null) {
            return owner.getLocation();
        } else {
            return new Vector3D(0, 0, 0);
        }
    }

    abstract boolean collide(Collision collision);
}
