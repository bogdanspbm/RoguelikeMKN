package objects.projectile;

import exceptions.CreationException;
import objects.collision.Collision;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;
import structures.Vector3D;

import java.awt.*;

public class ProjectileAttached extends Projectile {

    public ProjectileAttached(Pawn owner) {
        super(owner);

    }

    @Override
    protected void initCollision() {
        collisionAdapter.setCollision(new CylinderCollision(8, 16));
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
