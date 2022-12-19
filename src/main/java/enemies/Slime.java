package enemies;

import enemies.factory.SlimeAnimationFactory;
import enums.EBotBehaviour;
import exceptions.CreationException;
import player.animation.factory.PlayerAnimationComponentFactory;

public class Slime extends Enemy {

    public Slime() throws CreationException {
        SlimeAnimationFactory animationComponentFactory = new SlimeAnimationFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        createCollision();
        initEnemyController(EBotBehaviour.AGGRESSOR);
    }
}
