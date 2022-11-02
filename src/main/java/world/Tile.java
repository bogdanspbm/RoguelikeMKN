package world;

import engine.render.interfaces.Drawable;
import interfaces.Placeble;
import objects.animations.objects.AnimationSource;
import structures.Vector3D;

import java.awt.*;

public class Tile implements Drawable, Placeble {

    private Vector3D location;
    private AnimationSource source;

    public Tile(AnimationSource source) {
        this.source = source;
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
}
