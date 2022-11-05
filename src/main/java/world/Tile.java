package world;

import engine.render.interfaces.Drawable;
import exceptions.CreationException;
import exceptions.SetException;
import interfaces.Collidable;
import interfaces.Placeable;
import objects.animations.objects.AnimationSource;
import objects.collision.BoxCollision;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import structures.Vector3D;

import java.awt.*;

public class Tile implements Drawable, Collidable {


    private Vector3D location;
    private Vector3D rotation = new Vector3D(0, 0, 45);
    private AnimationSource source;

    private Collision collision;

    protected CollisionAdapter collisionAdapter = new CollisionAdapter(this);

    public Tile(AnimationSource source) throws CreationException {
        this.source = source;
        createCollision();
    }

    private void createCollision() {
        collisionAdapter.setCollision(new BoxCollision(16, 16, 16));
    }

    @Override
    public void draw(Graphics grphcs) {
        grphcs.drawImage(source.getImage(), location.x(), location.y() - location.z(), null);
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
    public void setLocation(Vector3D location) {
        this.location = location;
    }

    @Override
    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }


    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

}
