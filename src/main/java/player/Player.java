package player;

import exceptions.CreationException;
import exceptions.SetException;
import objects.collision.BoxCollision;
import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;
import player.animation.factory.PlayerAnimationComponentFactory;
import player.controller.PlayerController;

public class Player extends Pawn {

    public Player() throws CreationException {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent();
        createCollision();
        initPlayerController();
    }

    private void initPlayerController() {
        controllerAdapter.setController(new PlayerController());
    }

    private void createCollision() throws CreationException {
        try {
            collisionAdapter.setCollision(new BoxCollision(5, 5, 10));
        } catch (Exception e) {
            throw new CreationException("Can't create collision: \n" + e.toString());
        }
    }

}
