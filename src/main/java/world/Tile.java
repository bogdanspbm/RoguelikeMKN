package world;

import engine.render.interfaces.Drawable;
import exceptions.CreationException;
import interfaces.Collidable;
import objects.animations.objects.TextureSource;
import objects.collision.BoxCollision;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import structures.Vector3D;

import java.awt.*;

public class Tile implements Drawable, Collidable {


    private Vector3D location;
    private Vector3D rotation = new Vector3D(0, 0, 0);
    private TextureSource source;

    private Collision collision;

    protected CollisionAdapter collisionAdapter = new CollisionAdapter(this);

    public Tile(TextureSource source) throws CreationException {
        this.source = source;
        createCollision();
    }

    private void createCollision() {
        collisionAdapter.setCollision(new BoxCollision(64, 64, 32));
    }

    @Override
    public void draw(Graphics grphcs) {
        grphcs.drawImage(source.getImage(), location.x() - source.getImage().getWidth() / 2, location.y() - source.getImage().getHeight() / 2, null);
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
