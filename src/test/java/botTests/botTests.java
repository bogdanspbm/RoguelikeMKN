package botTests;


import enemies.Enemy;
import enemies.Slime;
import enums.EBotType;
import enums.EPawnStatus;
import exceptions.CreationException;
import objects.collision.BoxCollision;
import objects.collision.CylinderCollision;
import objects.controller.Controller;
import objects.pawn.Pawn;
import org.junit.Assert;
import org.junit.Test;
import player.Player;
import structures.Vector3D;

import static world.singleton.Processor.getWorld;

public class botTests {

    private double getDistanceToTarget(Vector3D a, Vector3D b) {
        int xDirection = a.x() - b.x();
        int yDirection = a.y() - b.y();

        return Math.sqrt(xDirection * xDirection + yDirection * yDirection);
    }

    @Test
    public void testChangeStatusOnAttack() {
        Enemy bot = new Enemy();
        bot.setStatus(EPawnStatus.ATTACK);
        Assert.assertEquals(bot.getStatus(), EPawnStatus.ATTACK);
    }

    @Test
    public void testChangeStatusOnWalk() {
        Enemy bot = new Enemy();
        bot.setStatus(EPawnStatus.WALK);
        Assert.assertEquals(bot.getStatus(), EPawnStatus.WALK);
    }

    @Test
    public void testIdleBot() throws CreationException {
        Pawn bot = new Enemy(EBotType.CALM);
        Vector3D startLocation = bot.getLocation().copy();
        // TODO: Wait что-то там
        // Цикл убрать
        for (int i = 0; i < 10000; i++) {
            bot.getController().tick();
            Assert.assertEquals(startLocation.x(), bot.getLocation().x());
            Assert.assertEquals(startLocation.y(), bot.getLocation().y());
            Assert.assertEquals(startLocation.z(), bot.getLocation().z());
        }
    }

    @Test
    public void testPatrolBot() throws CreationException {
        Pawn bot = new Enemy(EBotType.PATROL);
        bot.setLocation(new Vector3D(0, 0, 100));
        Vector3D startLocation = bot.getLocation().copy();
        // TODO: Предикат пока не выполнится условие
        // И макс кол-во шагов
        for (int i = 0; i < 10000; i++) {
            bot.getController().tick();
        }
        double distance = Math.sqrt((startLocation.x() - bot.getLocation().x()) * (startLocation.x() - bot.getLocation().x()) + (startLocation.y() - bot.getLocation().y()) * (startLocation.y() - bot.getLocation().y()));
        Assert.assertEquals(distance > 0, true);

    }

    @Test
    public void testKillBot() throws CreationException {
        Pawn bot = new Enemy(EBotType.PATROL);
        Assert.assertEquals(bot.getParamsComponent().checkIsDead(), false);
        bot.applyDamage(10000, null);
        Assert.assertEquals(bot.getParamsComponent().checkIsDead(), true);
    }

    @Test
    public void botVsPlayer() throws CreationException {
        Pawn bot = new Enemy(EBotType.AGGRESSOR);
        bot.setLocation(new Vector3D(40, 40, 100));
        Player player = new Player();
        player.setLocation(new Vector3D(-40, 40, 100));

        assert player.getParamsComponent().getHealthPercentage() == 1;

        getWorld().addPawn(bot);
        getWorld().addPawn(player);

        for (int i = 0; i < 100000; i++) {
            bot.getController().tick();
            getWorld().getProjectiles().forEach(projectile -> {
                projectile.tick();
                getWorld().projectileCollide(projectile);
            });
        }

        assert player.getParamsComponent().getHealthPercentage() < 1;
    }

    @Test
    public void botScary() throws CreationException {
        Pawn bot = new Enemy(EBotType.COWARD);
        bot.setLocation(new Vector3D(40, 40, 100));
        Player player = new Player();
        player.setLocation(new Vector3D(-40, 40, 100));


        double distance = getDistanceToTarget(bot.getLocation(), player.getLocation());

        getWorld().addPawn(bot);
        getWorld().addPawn(player);

        for (int i = 0; i < 1000; i++) {
            bot.getController().tick();
        }

        assert distance < getDistanceToTarget(bot.getLocation(), player.getLocation());
    }

    @Test
    public void test() throws CreationException {
        Pawn bot = new Slime();
        getWorld().addPawn(bot);

        assert getWorld().getPawns().size() == 1;

        bot.applyDamage(10, null);

        assert getWorld().getPawns().size() == 2;
    }


}
