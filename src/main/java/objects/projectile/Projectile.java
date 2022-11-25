package objects.projectile;

import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Damageable;
import interfaces.Tickable;
import objects.animations.component.AnimationComponent;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import objects.pawn.Pawn;
import structures.Vector3D;

import java.awt.*;
import java.util.HashMap;

public abstract class Projectile implements Collidable, Drawable, Tickable {

    protected Pawn owner;
    protected Collision collision;
    protected AnimationComponent animationComponent;

    protected int damage = 10;

    protected HashMap<Damageable, Boolean> damageMap = new HashMap<>();

    protected CollisionAdapter collisionAdapter = new CollisionAdapter(this);

    public Projectile(Pawn owner) {
        this.owner = owner;
        initCollision();
    }

    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent = animationComponent;
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }


    @Override
    public void setLocation(Vector3D location) {

    }

    @Override
    public void setRotation(Vector3D rotation) {

    }

    @Override
    public void draw(Graphics grphcs) {
        if (animationComponent != null) {
            grphcs.drawImage(animationComponent.getImage(), getLocation().x(), getLocation().y() - getLocation().z(), null);
        }
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    protected void initCollision() {

    }

    public int getDamage() {
        return damage;
    }

    public void addDamaged(Damageable damageable) {
        damageMap.put(damageable, true);
    }

    public boolean hasDamaged(Damageable damageable) {
        return damageMap.containsKey(damageable);
    }
}
