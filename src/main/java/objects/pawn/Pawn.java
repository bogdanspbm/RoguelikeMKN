package objects.pawn;

import engine.render.interfaces.Drawable;
import interfaces.Placeble;
import objects.animations.component.AnimationComponent;
import objects.controller.Controller;
import structures.Vector3D;

import java.awt.*;

public abstract class Pawn implements Placeble, Drawable {

    protected Controller controller;
    protected Vector3D location;
    protected String name = "Pawn";

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
}
