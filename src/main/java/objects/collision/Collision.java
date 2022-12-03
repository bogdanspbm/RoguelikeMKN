package objects.collision;

import enums.ECollideType;
import enums.ECollisionType;
import interfaces.Placeable;
import structures.Vector3D;

public abstract class Collision {

    protected Placeable owner;

    protected ECollisionType type = ECollisionType.EMPTY;
    protected ECollideType collideType = ECollideType.BLOCK;

    public void setOwner(Placeable owner) {
        this.owner = owner;
    }

    public ECollisionType getType() {
        return type;
    }

    public void setCollideType(ECollideType type) {
        this.collideType = type;
    }

    public ECollideType getCollideType() {
        return collideType;
    }

    public Vector3D getLocation() {
        if (owner != null) {
            return owner.getLocation();
        } else {
            return new Vector3D(0, 0, 0);
        }
    }

    public Vector3D getRotation() {
        if (owner != null) {
            return owner.getRotation();
        } else {
            return new Vector3D(0, 0, 0);
        }
    }

    public abstract Vector3D getShape();

    public boolean collide(Collision collision) {
        return collide(collision, owner.getLocation());
    }

    public abstract boolean collide(Collision collision, Vector3D position);
}
