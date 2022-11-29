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
                    System.out.println("name: " + name + " neib: " + getStoneNeigh(i, k));
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
                /*
                    xxx
                    xxx
                    xxx
                    */
                return "brick_hole_e";
            }
            case 8: {
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                     /*
                     o-stone
                     x-hole
                     ooo
                     oxo
                     ooo
                    */
                    return "brick_hole_z";
                }
            }
            case 7: {
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    oox
                    oxo
                    ooo
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxx
                    ooo
                    */
                    return "brick_hole_l";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxo
                    oxo
                    */
                    return "brick_hole_m";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxo
                    xoo
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    xxo
                    ooo
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                     /*
                    xoo
                    oxo
                    ooo
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxo
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
            }
            case 6: {
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                     /*
                    oxx
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                     /*
                    oxo
                    oxx
                    ooo
                    */
                    return "brick_hole_x";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    oox
                    oxx
                    ooo
                    */
                    return "brick_hole_l";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    oox
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxx
                    oox
                    */
                    return "brick_hole_l";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    oox
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxo
                    oxx
                    */
                    return "brick_hole_m";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxo
                    xox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    oxo
                    xxo
                    */
                    return "brick_hole_m";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    xxo
                    xoo
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                         /*
                        xoo
                        oxo
                        xoo
                        */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xoo
                    xxo
                    ooo
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*
                    xxo
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*
                    xox
                    oxo
                    ooo
                    */
                    return "brick_hole_z";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*PH | |
                    oxo
                    oxo
                    oxo
                    */
                    return "brick_hole_n";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x][y -1] == ETileType.STONE) {
                     /*
                    ooo
                    xxx
                    ooo
                    */
                    return "brick_hole_o";
                }
            }
            case 5: {
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxx
                    oxx
                    ooo
                    */
                    return "brick_hole_h";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oox
                    oxx
                    oox
                    */
                    return "brick_hole_l";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                      /*
                    ooo
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                      /*
                    ooo
                    oxo
                    xxx
                    */
                    return "brick_hole_m";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    ooo
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                     /*
                    xoo
                    xxo
                    xoo
                    */
                    return "brick_hole_k";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxo
                    ooo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
            }
            case 4: {
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxx
                    ooo
                    */
                    return "brick_hole_w";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    oxx
                    ooo
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    oxx
                    oox
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                    /*
                    xxo
                    xxo
                    xoo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    ooo
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    ooo
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE
                        && types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xoo
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    xxo
                    ooo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE) {
                    /*PH
                    oxx
                    oxx
                    oxo
                    */
                    return "brick_corner_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    oxo
                    oxx
                    */
                    return "brick_corner_e";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxo
                    oxx
                    oxx
                    */
                    return "brick_corner_b";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    oxx
                    xoo
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                    /*
                    oxx
                    oxo
                    xox
                    */
                    return "brick_hole_n";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    oxx
                    xox
                    */
                    return "brick_hole_x";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    oxx
                    xox
                    */
                    return "brick_hole_l";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxo
                    oxx
                    xxo
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                    /*PH
                    oox
                    oxx
                    xxo
                    */
                    return "brick_hole_s";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y -1] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    xxx
                    ooo
                    */
                    return "brick_hole_u";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y -1] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                    /*
                    oxx
                    xxo
                    oox
                    */
                    return "brick_hole_y";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y -1] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    oox
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y -1] == ETileType.STONE
                        && types[x][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    oox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y -1] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    xoo
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y -1] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    xoo
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    oxo
                    xxo
                    */
                    return "brick_corner_e";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y  -1] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    xxo
                    oxo
                    */
                    return "brick_hole_y";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE
                        && types[x + 1][y] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxo
                    xxo
                    xxo
                    */
                    return "brick_corner_e";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE
                        && types[x + 1][y] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                    /*PH
                    oox
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y -1] == ETileType.STONE
                        && types[x + 1][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                    /*
                    oxx
                    xxo
                    xoo
                    */
                    return "brick_hole_y";
                }
            }
            case 3: {
                if (types[x][y - 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    xxx
                    ooo
                    */
                    return "brick_hole_i";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    ooo
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxo
                    xxo
                    */
                    return "brick_hole_g";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    oxx
                    oxx
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*
                    xoo
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*
                    xxx
                    xxo
                    xoo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    oxx
                    oox
                    */
                    return "brick_hole_h";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxo
                    xxo
                    xxx
                    */
                    return "brick_hole_y";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x - 1][y] == ETileType.STONE) {
                    /*PH | |
                    xxo
                    oxo
                    xxx
                    */
                    return "brick_hole_n";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE) {
                    /*PH
                    xxo
                    xxo
                    oxx
                    */
                    return "brick_corner_l";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxo
                    xox
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    xoo
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    xoo
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    xoo
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    xoo
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*PH
                    xxx
                    oxx
                    oxo
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    xxx
                    oxo
                    oxx
                    */
                    return "brick_corner_e";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y +1] == ETileType.STONE) {
                    /*PH
                    xxo
                    oxx
                    oxx
                    */
                    return "brick_hole_x";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y +1] == ETileType.STONE) {
                    /*
                    xox
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    oxx
                    xox
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*PH
                    oxx
                    oxx
                    xxo
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    oxo
                    xxx
                    */
                    return "brick_hole_n";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxo
                    oxx
                    xxx
                    */
                    return "brick_corner_b";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    xxx
                    oox
                    */
                    return "brick_hole_u";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xox
                    xxx
                    oox
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxx
                    oox
                    */
                    return "brick_hole_w";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    xxo
                    oox
                    */
                    return "brick_hole_j";
                }

                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    xxx
                    oxo
                    */
                    return "brick_corner_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    xxo
                    oxx
                    */
                    return "brick_corner_g";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    xxx
                    xoo
                    */
                    return "brick_hole_u";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xox
                    xxx
                    xoo
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxx
                    xoo
                    */
                    return "brick_hole_w";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xxx
                    oxx
                    xoo
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*
                    oxx
                    xxo
                    xox
                    */
                    return "brick_hole_y";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    xox
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    xxo
                    xxo
                    */
                    return "brick_hole_v";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*
                    xox
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    xxx
                    oxo
                    xxo
                    */
                    return "brick_hole_n";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    xxx
                    xxo
                    oxo
                    */
                    return "brick_hole_g";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    xxo
                    */
                    return "brick_corner_o";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxo
                    xxo
                    xxx
                    */
                    return "brick_hole_g";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x][y + 1] == ETileType.STONE) {
                    /*
                    oox
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    oox
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    oxx
                    xxx
                    oxo
                    */
                    return "brick_corner_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y] == ETileType.STONE) {
                    /*PH
                    oxx
                    xxo
                    oxx
                    */
                    return "brick_corner_h";
                }
                if (types[x - 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxx
                    oxo
                    */
                    return "brick_corner_k";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xox
                    xxx
                    oxo
                    */
                    return "brick_hole_q";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y - 1] == ETileType.STONE) {
                    /*
                    xxo
                    xxx
                    oxo
                    */
                    return "brick_corner_k";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y] == ETileType.STONE) {
                    /*PH
                    xxo
                    oxx
                    xxo
                    */
                    return "brick_corner_o";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    xxo
                    */
                    return "brick_corner_o";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    xox
                    */
                    return "brick_hole_v";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x - 1][y + 1] == ETileType.STONE) {
                    /*PH
                    oxx
                    xxo
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    xox
                    xxo
                    xox
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*PH
                    xxo
                    oxx
                    xxo
                    */
                    return "brick_corner_m";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE &&
                        types[x][y - 1] == ETileType.STONE) {
                    /*
                    oxo
                    xxx
                    xox
                    */
                    return "brick_hole_v";
                }
                if (types[x][y + 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE &&
                        types[x + 1][y - 1] == ETileType.STONE) {
                    /*
                    xox
                    xxx
                    oxo
                    */
                    return "brick_hole_q";
                }
            }
            case 2: {
                if (types[x - 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    oox
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xoo
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                     /*
                    xxo
                    xxo
                    xxx
                    */
                    return "brick_hole_g";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE) {
                     /*
                    xxx
                    xxo
                    xxo
                    */
                    return "brick_hole_g";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y - 1] == ETileType.STONE) {
                     /*
                    xxx
                    xxx
                    xoo
                    */
                    return "brick_hole_i";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x - 1][y - 1] == ETileType.STONE) {
                     /*
                    xxx
                    xxx
                    oox
                    */
                    return "brick_hole_i";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*
                    xxx
                    oxx
                    oxx
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxx
                    oxx
                    xxx
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxx
                    xxx
                    oxx
                    */
                    return "brick_corner_g";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxx
                    xxx
                    xox
                    */
                    return "brick_hole_u";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxx
                    xxx
                    xxo
                    */
                    return "brick_corner_i";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE) {
                     /*PH
                    oxx
                    xxo
                    xxx
                    */
                    return "brick_hole_g";
                }
                if (types[x + 1][y + 1] == ETileType.STONE && types[x - 1][y + 1] == ETileType.STONE) {
                     /*
                    oxo
                    xxx
                    xxx
                    */
                    return "brick_corner_e";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xox
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xox
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xox
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xox
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xox
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                     /*PH
                    xxo
                    oxx
                    xxx
                    */
                    return "brick_corner_b";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                     /*
                    xxo
                    xxx
                    oxx
                    */
                    return "brick_corner_l";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                     /*
                    xxo
                    xxx
                    xox
                    */
                    return "brick_hole_w";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x + 1][y + 1] == ETileType.STONE) {
                     /*
                    xxo
                    xxx
                    xxo
                    */
                    return "brick_corner_j";
                }
                if (types[x + 1][y] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*PH
                    xxx
                    oxo
                    xxx
                    */
                    return "brick_hole_n";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*
                    xxx
                    oxx
                    xox
                    */
                    return "brick_hole_h";
                }
                if (types[x + 1][y - 1] == ETileType.STONE && types[x - 1][y] == ETileType.STONE) {
                     /*PH
                    xxx
                    oxx
                    xxo
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                     /*PH
                    xxx
                    xxo
                    oxx
                    */
                    return "brick_hole_g";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x + 1][y] == ETileType.STONE) {
                     /*
                    xxx
                    xxo
                    xox
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y - 1] == ETileType.STONE && types[x + 1][y - 1] == ETileType.STONE) {
                     /*PH
                    xxx
                    xxx
                    oxo
                    */
                    return "brick_corner_f";
                }
                if (types[x][y - 1] == ETileType.STONE && types[x][y + 1] == ETileType.STONE) {
                     /*
                    xxo
                    xxx
                    xxo
                    */
                    return "brick_corner_j";
                }
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
                if (types[x][y + 1] == ETileType.STONE) {
                     /*
                    xox
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y] == ETileType.STONE) {
                     /*
                    xxx
                    xxo
                    xxx
                    */
                    return "brick_hole_g";
                }
                if (types[x][y - 1] == ETileType.STONE) {
                     /*
                    xxx
                    xxx
                    xox
                    */
                    return "brick_hole_i";
                }
                if (types[x - 1][y] == ETileType.STONE) {
                     /*
                    xxx
                    oxx
                    xxx
                    */
                    return "brick_hole_d";
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
                if (i == 0 || k == 0 || i == 1 || k == 1 || i == map.length - 1 || k == map[i].length - 1 || i == map.length - 2 || k == map[i].length - 2) {
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
