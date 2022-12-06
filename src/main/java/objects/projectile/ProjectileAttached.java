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
        Vector3D velocity = owner.getController().getControllerVelocity();
        Vector3D result = new Vector3D(owner.getLocation().x() + velocity.x() * 20, owner.getLocation().y() + velocity.y() * 20, owner.getLocation().z());
        return result;
    }

    @Override
    public Vector3D getRotation() {
        Vector3D velocity = owner.getController().getControllerVelocity();

        if (velocity.x() > 0.5) {
            return new Vector3D(0, 0, 0);
        } else if (velocity.x() < -0.5) {
            return new Vector3D(0, 0, 180);
        } else if (velocity.y() > 0.5) {
            return new Vector3D(0, 0, 90);
        } else if (velocity.y() < -0.5) {
            return new Vector3D(0, 0, 270);
        }

        return new Vector3D(0, 0, 0);
    }

}
