package player;

import config.Config;
import enums.EPawnStatus;
import exceptions.CreationException;
import exceptions.SetException;
import interfaces.Controllable;
import objects.collision.BoxCollision;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import objects.projectile.factory.MeleeProjectileFactory;
import player.animation.component.PlayerAnimationComponent;
import player.animation.factory.PlayerAnimationComponentFactory;
import player.controller.PlayerController;
import structures.Vector3D;

import static world.singleton.World.getWorld;

public class Player extends Pawn {

    public Player() throws CreationException {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        projectileFactory = new MeleeProjectileFactory(this, "sword_splash");
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

    private void createCollision() {
        collisionAdapter.setCollision(new CylinderCollision(8, 16));
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
        if (status != EPawnStatus.ATTACK) {
            Thread attack = new Thread(new Runnable() {
                public void run() //Этот метод будет выполняться в побочном потоке
                {
                    try {
                        setStatus(EPawnStatus.ATTACK);
                        projectileFactory.createProjectile();
                        Thread.sleep(1000);
                        setStatus(EPawnStatus.WALK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            attack.start();
        }
    }


    @Override
    public void openInventory() {

    }
}
