package objects.projectile;

import objects.collision.Collision;
import objects.pawn.Pawn;
import structures.Vector3D;

import java.awt.*;

public class ProjectileAttached extends Projectile {

    public ProjectileAttached(Pawn owner) {
        super(owner);
    }


    @Override
    public Vector3D getLocation() {
        return owner.getLocation();
    }

    @Override
    public Vector3D getRotation() {
        return owner.getRotation();
    }

    @Override
    public void tick() {

    }
}
