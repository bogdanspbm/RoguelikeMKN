package player;

import enums.EPawnStatus;
import exceptions.CreationException;
import interfaces.Interactive;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import objects.projectile.factory.MeleeProjectileFactory;
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
    public void moveRight(int x) {
        if (!getWorld().checkCollides(getCollision(), new Vector3D(location.x() + x, location.y(), location.z()))) {
            location.addX(x);
            getWorld().updateOverlap(this);
        }
    }

    @Override
    public void moveForward(int x) {
        if (!getWorld().checkCollides(getCollision(), new Vector3D(location.x(), location.y() - x, location.z()))) {
            location.addY(-x);
            getWorld().updateOverlap(this);
        }
    }

    @Override
    public void interact() {
        for (Interactive interactive : getWorld().getOverlappedInteractions(this)) {
            interactive.interact(this);
        }
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
                        Thread.sleep(300);
                        setStatus(EPawnStatus.WALK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            attack.start();
        }
    }

}
