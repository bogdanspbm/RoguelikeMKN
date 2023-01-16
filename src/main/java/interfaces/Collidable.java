package interfaces;

import objects.collision.Collision;
import player.Player;
import structures.Vector3D;

public interface Collidable extends Placeable {


    void setCollision(Collision collision);

    Collision getCollision();

}
