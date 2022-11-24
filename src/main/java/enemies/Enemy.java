package enemies;

import enemies.controller.BotController;
import exceptions.CreationException;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import player.animation.factory.PlayerAnimationComponentFactory;

public class Enemy extends Pawn {

    public String typeOfBotsBehaviour;

    public Enemy(String typeOfBotsBehaviour) throws CreationException {
        this.typeOfBotsBehaviour = typeOfBotsBehaviour;
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        createCollision();
        initEnemyController();
    }

    private void initEnemyController() throws CreationException {
        try {
            controllerAdapter.setController(new BotController(typeOfBotsBehaviour));

        } catch (Exception e) {
            throw new CreationException("Can't create PlayerController: \n" + e.toString());
        }
    }

    private void createCollision() throws CreationException {
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
