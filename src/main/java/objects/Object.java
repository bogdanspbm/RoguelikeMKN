package objects;

import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Placeable;
import objects.animations.component.AnimationComponent;
import objects.animations.objects.TextureSource;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import structures.Vector3D;

import java.awt.*;

public abstract class Object implements Drawable, Placeable, Collidable {

    protected Vector3D location = new Vector3D(0, 0, 0);
    protected Vector3D rotation = new Vector3D(0, 0, 0);

    protected Collision collision;

    protected TextureSource source;
    protected AnimationComponent animationComponent;

    protected CollisionAdapter collisionAdapter = new CollisionAdapter(this);

    @Override
    public void draw(Graphics grphcs) {
        if (source != null) {
            grphcs.drawImage(source.getImage(), location.x() - source.getImage().getWidth() / 2, location.y() - source.getImage().getHeight() / 2, null);
            return;
        }

        if (animationComponent != null) {
            grphcs.drawImage(animationComponent.getImage(), location.x() - animationComponent.getImage().getWidth(null) / 2, (int) location.y() - animationComponent.getImage().getHeight(null) / 2, null);
            return;
        }
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
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
}
