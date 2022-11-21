package world.factory;

import exceptions.CreationException;
import objects.animations.objects.TextureSource;
import world.Tile;

import java.util.HashMap;

abstract class TileFactory {

    protected HashMap<String, TextureSource> sources;

    public abstract Tile createTile(String name) throws CreationException;
}
