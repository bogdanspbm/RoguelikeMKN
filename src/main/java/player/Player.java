package player;

import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;
import player.animation.factory.PlayerAnimationComponentFactory;

public class Player extends Pawn {

    public Player() {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent();
    }

}
