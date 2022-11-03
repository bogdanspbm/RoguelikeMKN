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
    protected Vector3D location;
    protected Vector3D rotation = new Vector3D(0, 0, 45);
    protected String name = "Pawn";

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
        System.out.println(getLocation());
        if (getLocation() != null && !getWorld().checkCollides(getCollision(), new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() - fallSpeed))) {
            getLocation().addZ(-fallSpeed);
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
