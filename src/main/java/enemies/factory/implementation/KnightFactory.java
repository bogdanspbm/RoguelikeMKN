package enemies.factory.implementation;

import enemies.Enemy;
import enemies.Slime;
import enemies.factory.BotFactory;
import enums.EBotBehaviour;

public class KnightFactory extends BotFactory {
    @Override
    public Enemy createBotHidden() {
        try {
            Enemy enemy = new Enemy(EBotBehaviour.COWARD);
            enemy.setLocation(getRandomLocation());
            return enemy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
