package world;

import database.adapter.implementation.TextureDatabaseAdapter;
import enums.ETileType;
import generator.PerlinNoiseGenerator;
import objects.animations.objects.TextureSource;
import structures.Vector3D;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {

    private PerlinNoiseGenerator noiseGenerator;
    private int resolution;

    private int SEA_LEVEL = 110;

    TextureDatabaseAdapter textureDatabaseAdapter;

    ETileType[][] types;

    int[][] map;

    List<Tile> tiles = new ArrayList<>();

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

    public List<Tile> getTiles() {
        if (tiles.size() == 0) {
            tiles = generateTiles();
        }

        return tiles;
    }


    private List<Tile> generateTiles() {
        List<Tile> result = new ArrayList<>();

        generateTypes();

        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                Tile tile;
                if (i == 0 || k == 0 || i == map.length - 1 || k == map[i].length - 1) {
                    tile = new Tile(textureDatabaseAdapter.getTextureByName("brick_hole_e"));
                } else {
                    String name = getTileByNeighbor(i, k);
                    tile = new Tile(textureDatabaseAdapter.getTextureByName(name));
                }
                tile.setType(types[i][k]);
                tile.setLocation(new Vector3D(i * 64 - resolution / 2 * 64, resolution / 2 * 64 - k * 64, -100));
                result.add(tile);
            }
        }

        return result;
    }

    private String getTileByNeighbor(int x, int y) {
        String res = "brick_hole_e";

        if (types[x][y] == ETileType.STONE) {
            return "stone_a";
        }

        int holes = getStoneNeigh(x, y);

        switch (holes) {
            case 0: {
                return "stone_a";
            }
            case 5: {
                if (types[x + 1][y + 1] == ETileType.HOLE) {
                     /*
                    oxx
                    oxx
                    ooo
                    */
                    return "brick_hole_h";
                }
                if (types[x + 1][y - 1] == ETileType.HOLE) {
                      /*
                    ooo
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y + 1] == ETileType.HOLE) {
                    /*
                    xxo
                    xxo
                    ooo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y - 1] == ETileType.HOLE) {
                     /*
                    ooo
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }

                return "brick_hole_e";
            }
            case 3: {
                if (types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    xxx
                    ooo
                    */
                    return "brick_hole_i";
                }
                if (types[x][y + 1] == ETileType.STONE) {
                    /*
                    ooo
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y] == ETileType.STONE) {
                    /*
                    xxo
                    xxo
                    xxo
                    */
                    return "brick_hole_g";
                }
                if (types[x - 1][y] == ETileType.STONE) {
                    /*
                    oxx
                    oxx
                    oxx
                    */
                    return "brick_hole_d";
                }
                return "brick_hole_e";
            }
            case 1: {
                if (types[x + 1][y + 1] == ETileType.STONE) {
                     /*
                    xxo
                    xxx
                    xxx
                    */
                    return "brick_corner_b";
                }
                if (types[x + 1][y - 1] == ETileType.STONE) {
                      /*
                    xxx
                    xxx
                    xxo
                    */
                    return "brick_corner_d";
                }
                if (types[x - 1][y + 1] == ETileType.STONE) {
                    /*
                    oxx
                    xxx
                    xxx
                    */
                    return "brick_corner_a";
                }
                if (types[x - 1][y - 1] == ETileType.STONE) {
                     /*
                    xxx
                    xxx
                    oxx
                    */
                    return "brick_corner_c";
                }
            }
        }
        return res;
    }

    private int getStoneNeigh(int x, int y) {
        int counter = 0;

        if (types[x][y + 1] == ETileType.STONE) {
            counter++;
        }
        if (types[x][y - 1] == ETileType.STONE) {
            counter++;
        }
        if (types[x - 1][y] == ETileType.STONE) {
            counter++;
        }
        if (types[x + 1][y] == ETileType.STONE) {
            counter++;
        }
        if (types[x + 1][y + 1] == ETileType.STONE) {
            counter++;
        }
        if (types[x + 1][y - 1] == ETileType.STONE) {
            counter++;
        }
        if (types[x - 1][y - 1] == ETileType.STONE) {
            counter++;
        }
        if (types[x - 1][y + 1] == ETileType.STONE) {
            counter++;
        }

        return counter;
    }

    private void generateTypes() {
        types = new ETileType[resolution][resolution];

        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                if (i == 0 || k == 0 || i == map.length - 1 || k == map[i].length - 1) {
                    types[i][k] = ETileType.HOLE;
                } else {
                    if (map[i][k] < SEA_LEVEL) {
                        types[i][k] = ETileType.HOLE;
                    } else {
                        types[i][k] = ETileType.STONE;
                    }
                }
            }
        }
    }

}
