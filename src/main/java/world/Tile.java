package world;

import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Placeble;
import objects.animations.objects.AnimationSource;
import objects.collision.BoxCollision;
import objects.collision.Collision;
import structures.Vector3D;

import java.awt.*;

public class Tile implements Drawable, Placeble, Collidable {

    private Vector3D location;
    private AnimationSource source;

    private Collision collision;

    public Tile(AnimationSource source) {
        this.source = source;
        createCollision();
    }

    private void createCollision() {
        collision = new BoxCollision(16, 16, 16);
        collision.setOwner(this);
    }

    @Override
    public void draw(Graphics grphcs) {
        grphcs.drawImage(source.getImage(), location.x(), location.y(), null);
    }

    @Override
    public Vector3D getLocation() {
        return location;
    }

    @Override
    public void setLocation(Vector3D location) {
        this.location = location;
    }


    @Override
    public Collision getCollision() {
        return collision;
    }
}
