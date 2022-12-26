package enemiesTests;

import enemies.Enemy;
import engine.initialization.GameInitializer;
import enums.EPawnStatus;
import objects.pawn.Pawn;
import org.junit.Assert;
import org.junit.Test;

public class EnemiesTests extends Assert {

    @Test
    public void testChangeStatusOnAttack(){
        Enemy bot = new Enemy();
        bot.setStatus(EPawnStatus.ATTACK);
        Assert.assertEquals(bot.getStatus(), EPawnStatus.ATTACK);
    }

    @Test
    public void testChangeStatusOnWalk(){
        Enemy bot = new Enemy();
        bot.setStatus(EPawnStatus.WALK);
        Assert.assertEquals(bot.getStatus(), EPawnStatus.WALK);
    }

}
