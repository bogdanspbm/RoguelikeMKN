package player;

import config.Config;
import exceptions.CreationException;
import exceptions.SetException;
import interfaces.Controllable;
import objects.collision.BoxCollision;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;
import player.animation.factory.PlayerAnimationComponentFactory;
import player.controller.PlayerController;
import structures.Vector3D;

import static world.singleton.World.getWorld;

public class Player extends Pawn implements Controllable {

    public Player() throws CreationException {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        createCollision();
        initPlayerController();
    }

    private void initPlayerController() throws CreationException {
        try {
            controllerAdapter.setController(new PlayerController());
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
    public void moveRight(int x) {
        if (!getWorld().checkCollides(getCollision(), new Vector3D(location.x() + x, location.y(), location.z()))) {
            location.addX(x);
        }
    }

    @Override
    public void moveForward(int x) {
        if (!getWorld().checkCollides(getCollision(), new Vector3D(location.x(), location.y() - x, location.z()))) {
            location.addY(-x);
        }
    }

    @Override
    public void jump() {
        if (!inJump && !isInAir()) {
            inJump = true;
            Thread jump = new Thread(new Runnable() {
                public void run() //Этот метод будет выполняться в побочном потоке
                {
                    try {
                        Thread.sleep(jumpTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    inJump = false;
                }
            });
            jump.start();    //Запуск потока
        }
    }

    @Override
    public void interact() {

    }

    @Override
    public void action() {

    }
}
