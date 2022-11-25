package interfaces;

import objects.collision.Collision;
import objects.projectile.Projectile;

public interface Damageable {

    public Collision getCollision();

    public void applyDamage(int value, Projectile instigator);
}
