package objects.pawn;

import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Physical;
import interfaces.Placeable;
import objects.animations.component.AnimationComponent;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import objects.controller.Controller;
import objects.controller.ControllerAdapter;
import structures.Vector3D;

import java.awt.*;

import static world.singleton.World.getWorld;

public abstract class Pawn implements Placeable, Drawable, Collidable, Physical {

    private Controller controller;

    @Override
    public boolean isInAir() {
        return getLocation().z() >= 0&& !getWorld().checkCollides(collision, new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() - fallSpeed));
    }

    protected Vector3D location;
    protected Vector3D rotation = new Vector3D(0, 0, 45);
    protected String name = "Pawn";

    protected boolean inJump = false;

    protected CollisionAdapter collisionAdapter = new CollisionAdapter(this);
    protected ControllerAdapter controllerAdapter = new ControllerAdapter(this);

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    private Collision collision;


    protected AnimationComponent animationComponent;

    @Override
    public Vector3D getLocation() {
        return location;
    }

    @Override
    public Vector3D getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }

    @Override
    public void tryFall() {
        if (!inJump) {
            if (getLocation() != null && getLocation().z() >= 0 && !getWorld().checkCollides(getCollision(), new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() - fallSpeed))) {
                getLocation().addZ(-fallSpeed);
            }
        } else {
            if (getLocation() != null && !getWorld().checkCollides(getCollision(), new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() + jumpSpeed))) {
                getLocation().addZ(jumpSpeed);
            }
        }
    }

    @Override
    public void setLocation(Vector3D location) {
        this.location = location;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void draw(Graphics grphcs) {
        if (animationComponent != null) {
            grphcs.drawImage(animationComponent.getImage(), getLocation().x(), getLocation().y() - getLocation().z(), null);
        }
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

}
