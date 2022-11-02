package world;

import engine.render.interfaces.Drawable;
import interfaces.Placeble;
import structures.Vector2D;

import java.awt.*;

public class Tile implements Drawable, Placeble {

    private Vector2D location;
    @Override
    public void draw(Graphics grphcs) {
        grphcs.setColor(new Color(100,100,100));
        grphcs.fillRect(location.x(), location.y(), 100,100);
    }

    @Override
    public Vector2D getLocation() {
        return location;
    }

    @Override
    public void setLocation(Vector2D location) {
        this.location = location;
    }
}
