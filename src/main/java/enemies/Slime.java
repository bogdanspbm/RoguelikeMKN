package enemies;

import enemies.animations.factory.SlimeAnimationFactory;
import enums.EBotBehaviour;
import exceptions.CreationException;
import objects.buff.factory.BuffFactory;
import objects.projectile.Projectile;
import utils.WorldUtils;

import static world.singleton.Processor.getWorld;

public class Slime extends Enemy implements Cloneable {

    public Slime() throws CreationException {
        SlimeAnimationFactory animationComponentFactory = new SlimeAnimationFactory();
        animationComponent = animationComponentFactory.createAnimationComponent(this);
        createCollision();
        paramsComponent.setMaxHealth(20);
        initEnemyController(EBotBehaviour.AGGRESSOR);
    }

    @Override
    public void applyDamage(int value, Projectile instigator) {

        if (paramsComponent.checkIsDead()) {
            return;
        }

        BuffFactory factory = new BuffFactory(getParamsComponent());

        instigator.getBuilderList().forEach(builder -> {
                    getParamsComponent().addBuff(factory.createBuff(builder));
                }
        );

        this.paramsComponent.addHealth(-value);

        if (paramsComponent.checkIsDead()) {
            deathEvent(instigator);
            return;
        }

        generateChildOnHit();
    }

    private void generateChildOnHit() {
        Slime child = clone();
        child.setLocation(WorldUtils.getRandomLocationNearPoint(child, getLocation(), 100));
        getWorld().addPawn(child);
    }

    protected Slime clone() {
        try {
            Slime slime = new Slime();
            slime.paramsComponent = paramsComponent.clone();
            return slime;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
