package objects.pawn;

import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Placeable;
import objects.animations.component.AnimationComponent;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import objects.controller.Controller;
import objects.controller.ControllerAdapter;
import structures.Vector3D;

import java.awt.*;

public abstract class Pawn implements Placeable, Drawable, Collidable {

    private Controller controller;
    protected Vector3D location;
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
            grphcs.drawImage(animationComponent.getImage(), (int) getLocation().x(), (int) getLocation().y(), null);
        }
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

}
