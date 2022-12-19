package enemies;

import enemies.controller.BotController;
import enums.EBotBehaviour;
import exceptions.CreationException;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import player.animation.factory.PlayerAnimationComponentFactory;

public class Enemy extends Pawn {

    public Enemy() {

    }


    public Enemy(EBotBehaviour behaviour) throws CreationException {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        createCollision();
        initEnemyController(behaviour);
    }

    protected void initEnemyController(EBotBehaviour behaviour) throws CreationException {
        try {
            controllerAdapter.setController(new BotController(behaviour));

        } catch (Exception e) {
            throw new CreationException("Can't create PlayerController: \n" + e.toString());
        }
    }

    protected void createCollision() throws CreationException {
        try {
            collisionAdapter.setCollision(new CylinderCollision(8, 16));
        } catch (Exception e) {
            throw new CreationException("Can't create Collision: \n" + e.toString());
        }
    }

    @Override
    public void jump() {

    }

    @Override
    public void interact() {

    }

    @Override
    public void action() {

    }
}
