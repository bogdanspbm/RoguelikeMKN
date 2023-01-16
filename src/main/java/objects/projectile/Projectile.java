package objects.projectile;

import config.Config;
import interfaces.Damageable;
import interfaces.Tickable;
import objects.Object;
import objects.buff.factory.BuffBuilder;
import objects.pawn.Pawn;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static world.singleton.Processor.getWorld;

public abstract class Projectile extends Object implements Tickable {

    protected Pawn owner;

    protected int damage = 10;

    protected int lifeTime = 300;
    protected int curTick = 0;

    protected HashMap<Damageable, Boolean> damageMap = new HashMap<>();

    List<BuffBuilder> builderList = new ArrayList<>();

    public Projectile(Pawn owner) {
        this.owner = owner;
        initCollision();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Projectile(Pawn owner, List<BuffBuilder> builderList) {
        this.owner = owner;
        this.builderList = builderList;
        initCollision();
    }


    @Override
    public void draw(Graphics grphcs) {
        //super.draw(grphcs);
        if (this.animationComponent != null) {
            Graphics2D graphics2D = (Graphics2D) grphcs;

            double radianAngle = (double) getRotation().z() * Math.PI / 180f;

            double x = getLocation().x() * Math.cos(radianAngle) - getLocation().y() * Math.sin(radianAngle);
            double y = getLocation().x() * Math.sin(radianAngle) + getLocation().y() * Math.cos(radianAngle);

            graphics2D.rotate(-radianAngle);
            grphcs.drawImage(this.animationComponent.getImage(), (int) x - this.animationComponent.getImage().getWidth(null) / 2, (int) y - this.animationComponent.getImage().getHeight(null) / 2, null);
            graphics2D.rotate(radianAngle);
        }
    }

    @Override
    public void tick() {
        addTick();
        checkDeath();
    }

    public List<BuffBuilder> getBuilderList() {
        return builderList;
    }

    protected void addTick() {
        curTick += 1000 / Config.FRAME_RATE;
    }

    protected void checkDeath() {
        if (curTick > lifeTime) {
            getWorld().deleteProjectile(this);
        }
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

    public Pawn getOwner() {
        return owner;
    }
}
