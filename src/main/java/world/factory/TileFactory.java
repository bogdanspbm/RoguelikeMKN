package world.factory;

import objects.animations.objects.AnimationSource;
import world.Tile;

import java.util.HashMap;

abstract class TileFactory {

    protected HashMap<String, AnimationSource> sources;

    public abstract Tile createTile(String name);
}
