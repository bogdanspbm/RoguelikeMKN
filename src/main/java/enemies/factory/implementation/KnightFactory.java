package enemies.factory.implementation;

import enemies.Enemy;
import enemies.Slime;
import enemies.factory.BotFactory;
import enums.EBotBehaviour;

public class KnightFactory extends BotFactory {
    @Override
    public Enemy createBot() {
        if (spawned >= spawnLimit) {
            return null;
        }
        try {
            Enemy enemy = new Enemy(EBotBehaviour.COWARD);
            enemy.setLocation(getRandomLocation());
            spawned += 1;
            return enemy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
