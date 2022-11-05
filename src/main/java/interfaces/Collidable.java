package interfaces;

import objects.collision.Collision;
import player.Player;
import structures.Vector3D;

public interface Collidable extends Placeable {


    public void setCollision(Collision collision);

    public Collision getCollision();

    /*
    @Override
    default int compareTo(Placeable o) {
        try {
            Vector3D a = getCollision().getShape().rotate(getRotation());
            Vector3D b = ((Collidable)(o)).getCollision().getShape().rotate(getRotation());

            if (getLocation().y() + a.y() != o.getLocation().y() + b.y()) {
                return getLocation().y() + a.y()- o.getLocation().y() - b.y();
            } else if (getLocation().x() + a.x() != o.getLocation().x() + b.x()) {
                return getLocation().x() + a.x() - o.getLocation().x() - b.x();
            } else {
                return getLocation().z() + a.z() - o.getLocation().z() - b.z();
            }
        } catch (Exception e) {
            if (getLocation().y() != o.getLocation().y()) {
                return getLocation().y() - o.getLocation().y();
            } else if (getLocation().x() != o.getLocation().x()) {
                return getLocation().x() - o.getLocation().x();
            } else {
                return getLocation().z() - o.getLocation().z();
            }
        }
    }*/
}
