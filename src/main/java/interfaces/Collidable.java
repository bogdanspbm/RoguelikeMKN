package interfaces;

import objects.collision.Collision;
import player.Player;

public interface Collidable extends Placeable {


    public void setCollision(Collision collision);

    public Collision getCollision();

    @Override
    default int compareTo(Placeable o) {
        if (getLocation().y() != o.getLocation().y()) {
            return getLocation().y() - o.getLocation().y();
        } else if (getLocation().x() != o.getLocation().x()) {
            return getLocation().x() - o.getLocation().x();
        } else {
            return getLocation().z() - o.getLocation().z();
        }
    }
}
