package enemies.factory.implementation;

import enemies.Enemy;
import enemies.Slime;
import enemies.animations.factory.SlimeAnimationFactory;
import enemies.factory.BotFactory;

import static world.singleton.Processor.getWorld;

public class SlimeFactory extends BotFactory {
    @Override
    public Enemy createBot() {
        try {
            Slime slime = new Slime();
            slime.setLocation(getRandomLocation());
            return slime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
