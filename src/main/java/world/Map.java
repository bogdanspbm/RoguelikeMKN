package world;

import database.adapter.implementation.TextureDatabaseAdapter;
import generator.PerlinNoiseGenerator;
import objects.animations.objects.TextureSource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {

    private PerlinNoiseGenerator noiseGenerator;
    private int resolution;

    TextureDatabaseAdapter textureDatabaseAdapter;

    int[][] map;

    public Map(int resolution) {
        try {
            textureDatabaseAdapter = new TextureDatabaseAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.resolution = resolution;
        noiseGenerator = new PerlinNoiseGenerator(resolution, 10);
        generateNoiseMap();
    }

    private void generateNoiseMap() {
        this.map = noiseGenerator.getMap();
    }


    private List<Tile> generateTiles() {
        List<Tile> result = new ArrayList<>();

        return result;
    }

}
