package objects.projectile.factory;

import objects.animations.factory.AnimationComponentFactory;
import objects.pawn.Pawn;
import objects.projectile.Projectile;

public abstract class ProjectileFactory {

    Pawn owner;

    protected AnimationComponentFactory animationComponentFactory;

    public void ProjectileFactory(Pawn owner) {
        this.owner = owner;
    }

    public abstract Projectile createProjectile();
}
