package objects.projectile;

import objects.buff.factory.BuffBuilder;
import objects.collision.Collision;
import objects.pawn.Pawn;
import structures.Vector3D;

import javax.xml.stream.Location;
import java.util.List;

public class ProjectileMovable extends Projectile {

    public ProjectileMovable(Pawn owner) {
        super(owner);
    }

    public ProjectileMovable(Pawn owner, List<BuffBuilder> builderList) {
        super(owner, builderList);
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
        super.tick();
    }
}
