package objects.projectile;

import objects.collision.Collision;
import objects.pawn.Pawn;
import structures.Vector3D;

import javax.xml.stream.Location;

public class ProjectileMovable extends Projectile {

    public ProjectileMovable(Pawn owner) {
        super(owner);
    }

    @Override
    public Vector3D getLocation() {
        return location;
    }

    @Override
    public Vector3D getRotation() {
        return rotation;
    }

    @Override
    public void tick() {
    }
}
