package objects.collision;

import enums.ECollideType;
import enums.ECollisionType;
import interfaces.Placeable;
import structures.Vector3D;

public abstract class Collision {

    protected Placeable owner;

    protected Vector3D location = new Vector3D(0, 0, 0);
    protected Vector3D rotation = new Vector3D(0, 0, 0);

    protected ECollisionType type = ECollisionType.EMPTY;

    public void setOwner(Placeable owner) {
        this.owner = owner;
    }

    public ECollisionType getType() {
        return type;
    }


    public Vector3D getLocation() {
        if (owner != null) {
            return owner.getLocation();
        } else {
            return location;
        }
    }

    public Vector3D getRotation() {
        if (owner != null) {
            return owner.getRotation();
        } else {
            return rotation;
        }
    }

    public void setLocation(Vector3D location) {
        this.location = location;
    }

    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }

    public abstract Vector3D getShape();

    public boolean collide(Collision collision) {
        return collide(collision, getLocation());
    }

    public abstract boolean collide(Collision collision, Vector3D position);
}
