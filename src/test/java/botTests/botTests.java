package botTests;


import enemies.Enemy;
import enemies.Slime;
import enums.EBotType;
import enums.EPawnStatus;
import exceptions.CreationException;
import objects.collision.BoxCollision;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import org.junit.Assert;
import org.junit.Test;
import structures.Vector3D;

public class botTests {

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
    public void testKillBot() {
        try {
            Pawn bot = new Enemy(EBotType.PATROL);
            Assert.assertEquals(bot.getParamsComponent().checkIsDead(), false);
            bot.applyDamage(10000, null);
            Assert.assertEquals(bot.getParamsComponent().checkIsDead(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
