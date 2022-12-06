package interfaces;

import objects.collision.Collision;
import objects.projectile.Projectile;

public interface Damageable {

    Collision getCollision();

    void applyDamage(int value, Projectile instigator);
}
