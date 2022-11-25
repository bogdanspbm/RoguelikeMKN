package objects.projectile.factory;

import objects.pawn.Pawn;
import objects.projectile.Projectile;

public abstract class ProjectileFactory {

    Pawn owner;

    public void ProjectileFactory(Pawn owner) {
        this.owner = owner;
    }

    public abstract Projectile createProjectile();
}
