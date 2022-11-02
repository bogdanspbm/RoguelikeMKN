package player;

import exceptions.CreationException;
import objects.collision.BoxCollision;
import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;
import player.animation.factory.PlayerAnimationComponentFactory;

public class Player extends Pawn {

    public Player() throws CreationException {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent();
        setCollision();
    }

    private void setCollision() {
        collision = new BoxCollision(5, 5, 10);
        collision.setOwner(this);
    }

}
