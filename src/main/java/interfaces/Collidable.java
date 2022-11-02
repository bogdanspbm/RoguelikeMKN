package interfaces;

import objects.collision.Collision;
import player.Player;

public interface Collidable {


    public void setCollision(Collision collision);

    public Collision getCollision();
}
