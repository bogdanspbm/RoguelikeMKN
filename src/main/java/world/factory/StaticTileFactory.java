package world.factory;

import enums.ETileType;
import exceptions.CreationException;
import objects.animations.objects.TextureSource;
import world.Tile;

import java.util.HashMap;

public class StaticTileFactory extends TileFactory {

    public StaticTileFactory(HashMap<String, TextureSource> sources) {
        this.sources = sources;
    }

    @Override
    public Tile createTile(String name) throws CreationException {
        return new Tile(sources.get(name), ETileType.HOLE);
    }
}
