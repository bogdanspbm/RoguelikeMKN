package enemies.factory.implementation;

import enemies.Enemy;
import enemies.factory.BotFactory;
import enums.EBotType;

public class KnightFactory extends BotFactory {
    @Override
    public Enemy createBotHidden() {
        try {
            Enemy enemy = new Enemy(EBotType.COWARD);
            enemy.setLocation(getRandomLocation());
            return enemy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
