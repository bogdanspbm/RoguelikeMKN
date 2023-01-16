package objects.projectile.factory;

import objects.animations.component.AnimationComponent;
import objects.animations.factory.implementation.SolidAnimationComponentFactory;
import objects.buff.factory.BuffBuilder;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import objects.projectile.ProjectileAttached;

import java.util.List;

import static world.singleton.Processor.getWorld;

public class MeleeProjectileFactory extends ProjectileFactory {

    public MeleeProjectileFactory(Pawn owner, String animName) {
        this.owner = owner;
        animationComponentFactory = new SolidAnimationComponentFactory(animName);
    }

    @Override
    public Projectile createProjectile() {
        ProjectileAttached projectile = new ProjectileAttached(owner);
        try {
            // TODO: Remove pawn from constructor?
            AnimationComponent animationComponent = animationComponentFactory.createAnimationComponent(null);
            projectile.setAnimationComponent(animationComponent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getWorld().addProjectile(projectile);
        return projectile;
    }

    @Override
    public Projectile createProjectile(List<BuffBuilder> builderList) {
        ProjectileAttached projectile = new ProjectileAttached(owner, builderList);
        try {
            // TODO: Remove pawn from constructor?
            AnimationComponent animationComponent = animationComponentFactory.createAnimationComponent(null);
            projectile.setAnimationComponent(animationComponent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getWorld().addProjectile(projectile);
        return projectile;
    }

}
