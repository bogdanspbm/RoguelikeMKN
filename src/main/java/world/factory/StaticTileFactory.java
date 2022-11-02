package world.factory;

import objects.animations.objects.AnimationSource;
import world.Tile;

import java.util.HashMap;

public class StaticTileFactory extends TileFactory {

    public StaticTileFactory(HashMap<String, AnimationSource> sources) {
        this.sources = sources;
    }

    @Override
    public Tile createTile(String name) {
        return new Tile(sources.get(name));
    }
}
