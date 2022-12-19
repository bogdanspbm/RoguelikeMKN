package objects.projectile.factory;

import objects.animations.factory.AnimationComponentFactory;
import objects.buff.factory.BuffBuilder;
import objects.pawn.Pawn;
import objects.projectile.Projectile;

import java.util.List;

public abstract class ProjectileFactory {

    Pawn owner;

    protected AnimationComponentFactory animationComponentFactory;

    public void ProjectileFactory(Pawn owner) {
        this.owner = owner;
    }

    public abstract Projectile createProjectile();
    public abstract Projectile createProjectile(List<BuffBuilder> builderList);
}
