package world;

import engine.render.interfaces.Drawable;
import enums.ETileType;
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

    protected ETileType type = ETileType.STONE;

    public Tile(TextureSource source, ETileType type) {
        this.source = source;
        this.type = type;
        createCollision();
    }

    public void setType(ETileType type) {
        this.type = type;
    }

    private void createCollision() {

        switch (type) {
            case STONE -> {
                collisionAdapter.setCollision(new BoxCollision(32, 32, 32));
                break;
            }
            case HOLE -> {
                collisionAdapter.setCollision(new BoxCollision(32, 32, 64));
                break;
            }
            case WALL -> {
                collisionAdapter.setCollision(new BoxCollision(32, 32, 64));
                break;
            }
        }
    }


}
