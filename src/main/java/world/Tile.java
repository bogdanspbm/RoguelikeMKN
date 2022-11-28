package world;

import engine.render.interfaces.Drawable;
import exceptions.CreationException;
import interfaces.Collidable;
import objects.Object;
import objects.animations.objects.TextureSource;
import objects.collision.BoxCollision;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import structures.Vector3D;

import java.awt.*;

public class Tile extends Object {

    public Tile(TextureSource source) {
        this.source = source;
        createCollision();
    }

    private void createCollision() {
        collisionAdapter.setCollision(new BoxCollision(64, 64, 32));
    }



}
