package objects.projectile;

import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Tickable;
import objects.pawn.Pawn;

public abstract class Projectile implements Collidable, Drawable, Tickable {
    protected Pawn owner;

    public Projectile(Pawn owner) {
        this.owner = owner;
    }
}
