package interfaces;

import objects.collision.Collision;
import player.Player;

public interface Collidable extends Placeable {


    public void setCollision(Collision collision);

    public Collision getCollision();
}
