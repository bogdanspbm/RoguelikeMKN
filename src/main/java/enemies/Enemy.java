package enemies;

import enemies.controller.BotController;
import enums.EBotType;
import enums.EPawnStatus;
import exceptions.CreationException;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import objects.projectile.factory.MeleeProjectileFactory;
import player.animation.factory.PlayerAnimationComponentFactory;

public class Enemy extends Pawn {

    public Enemy() {

    }


    public Enemy(EBotType behaviour) throws CreationException {
        PlayerAnimationComponentFactory animationComponentFactory = new PlayerAnimationComponentFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        projectileFactory = new MeleeProjectileFactory(this, "sword_splash");
        createCollision();
        initEnemyController(behaviour);
    }

    public void initEnemyController(EBotType type) throws CreationException {
        try {
            controllerAdapter.setController(new BotController(type));
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
        if (status != EPawnStatus.ATTACK) {
            Thread attack = new Thread(new Runnable() {
                public void run() //Этот метод будет выполняться в побочном потоке
                {
                    try {
                        setStatus(EPawnStatus.ATTACK);
                        Projectile projectile = projectileFactory.createProjectile(inventory.getBuffBuilders());
                        projectile.setDamage((int) (projectile.getDamage() * paramsComponent.getDamageScale()));
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
