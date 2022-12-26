package botTests;


import enemies.Enemy;
import enemies.Slime;
import enums.EBotType;
import objects.collision.BoxCollision;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import org.junit.Assert;
import org.junit.Test;
import structures.Vector3D;

public class botTests {


    @Test
    public void testIdleBot() {
        try {
            Pawn bot = new Enemy(EBotType.CALM);
            Vector3D startLocation = bot.getLocation().copy();
            for (int i = 0; i < 10000; i++) {
                bot.getController().tick();
            }
            Assert.assertEquals(startLocation.x(), bot.getLocation().x());
            Assert.assertEquals(startLocation.y(), bot.getLocation().y());
            Assert.assertEquals(startLocation.z(), bot.getLocation().z());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testPatrolBot() {
        try {
            Pawn bot = new Enemy(EBotType.PATROL);
            Vector3D startLocation = bot.getLocation().copy();
            for (int i = 0; i < 10000; i++) {
                bot.getController().tick();
            }
            double distance = Math.sqrt((startLocation.x() - bot.getLocation().x()) * (startLocation.x() - bot.getLocation().x()) + (startLocation.y() - bot.getLocation().y()) * (startLocation.y() - bot.getLocation().y()));
            Assert.assertEquals(distance > 0, true);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


}
