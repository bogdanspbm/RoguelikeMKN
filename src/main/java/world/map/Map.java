package world.map;

import database.adapter.implementation.TextureDatabaseAdapter;
import enemies.Enemy;
import enemies.factory.BotFactory;
import enums.ETileType;
import generator.PerlinNoiseGenerator;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import structures.Vector3D;
import world.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.FileUtils.writeToFile;
import static world.singleton.Processor.getWorld;

public class Map {

    private PerlinNoiseGenerator noiseGenerator;
    private int resolution;

    private int seaLevel;
    private int buildingLevel = 150;

    TextureDatabaseAdapter textureDatabaseAdapter;

    ETileType[][] types;

    int[][] map;
    int[][] buildingMap;

    List<BotFactory> botFactories = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();

    public Map(MapBuilder builder) {
        try {
            textureDatabaseAdapter = new TextureDatabaseAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.resolution = builder.getResolution();
        this.seaLevel = builder.getSeaLevel();
        this.buildingLevel = builder.getBuildingLevel();

        if (builder.getMap() != null) {
            this.map = builder.getMap();
        }

        if (builder.getBuildingMap() != null) {
            this.buildingMap = builder.getBuildingMap();
        }

        if (builder.getBotFactories() != null) {
            this.botFactories = builder.getBotFactories();
        }

        noiseGenerator = new PerlinNoiseGenerator(1, resolution);
        if (this.map == null) {
            generateNoiseMap();
        }

        if (this.buildingMap == null) {
            generateBuildings();
        }

        //generateBots();
    }

    private void generateNoiseMap() {
        this.map = noiseGenerator.getHeightMap();
    }

    private void generateBuildings() {
        noiseGenerator = new PerlinNoiseGenerator(0.25, resolution);
        buildingMap = noiseGenerator.getHeightMap();
    }

    public List<Tile> getTiles() {
        if (tiles.size() == 0) {
            tiles = generateTiles();
        }

        return tiles;
    }

    private void generateBots() {
        for (BotFactory factory : botFactories) {
            for (int i = 0; i < factory.getSpawnLimit(); i++) {
                Enemy bot = factory.createBot();
                getWorld().addPawn(bot);
                System.out.println(i);
            }
        }
    }

    private List<Tile> generateTiles() {
        List<Tile> result = new ArrayList<>();

        generateTypes();

        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                Tile tile;
                if (i == 0 || k == 0 || i == resolution - 1 || k == resolution - 1) {
                    tile = new Tile(textureDatabaseAdapter.getTextureByName("brick_hole_e"), ETileType.HOLE);
                } else {
                    String name = getTileByNeighbor(i, k);
                    tile = new Tile(textureDatabaseAdapter.getTextureByName(name), types[i][k]);
                }
                tile.setType(types[i][k]);
                tile.setLocation(new Vector3D(i * 64 - resolution / 2 * 64, resolution / 2 * 64 - k * 64, 0));
                result.add(tile);
            }
        }

        return result;
    }

    public void exportToFile(String path) {
        JSONObject level = new JSONObject();
        level.put("resolution", resolution);
        level.put("sea_level", seaLevel);
        level.put("build_level", buildingLevel);
        JSONArray mapA = new JSONArray();
        JSONArray mapB = new JSONArray();

        for (int i = 0; i < resolution; i++) {
            for (int k = 0; k < resolution; k++) {
                mapA.put(this.map[i][k]);
                mapB.put(this.buildingMap[i][k]);
            }
        }

        level.put("map", mapA);
        level.put("walls", mapB);

        writeToFile(path, level.toString());
    }


    private String getTileByNeighbor(int x, int y) {
        String res = "brick_hole_e";

        if (types[x][y] == ETileType.STONE) {
            return "stone_a";
        }

        int walls = getWallNeigh(x, y);

        switch (walls) {
            case 9: {
                if (types[x][y] == ETileType.WALL && types[x + 1][y] == ETileType.WALL &&
                        types[x - 1][y] == ETileType.WALL && types[x][y + 1] == ETileType.WALL &&
                        types[x - 1][y + 1] == ETileType.WALL && types[x + 1][y + 1] == ETileType.WALL &&
                        types[x][y - 1] == ETileType.WALL && types[x + 1][y - 1] == ETileType.WALL &&
                        types[x - 1][y - 1] == ETileType.WALL) {
                    return "stone_a";
                }
            }
        }
        if (types[x][y] == ETileType.WALL) {
            return "wall_grey_a";
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
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
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
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    oox
                    oxo
                    ooo
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxx
                    ooo
                    */
                    return "brick_hole_l";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxo
                    oxo
                    */
                    return "brick_hole_m";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxo
                    xoo
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    xxo
                    ooo
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xoo
                    oxo
                    ooo
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxo
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
            }
            case 6: {
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                     /*
                    oxx
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxo
                    oxx
                    ooo
                    */
                    return "brick_hole_x";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    oox
                    oxx
                    ooo
                    */
                    return "brick_hole_l";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    oox
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxx
                    oox
                    */
                    return "brick_hole_l";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    oox
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxo
                    oxx
                    */
                    return "brick_hole_m";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxo
                    xox
                    */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    oxo
                    xxo
                    */
                    return "brick_hole_m";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    xxo
                    xoo
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                         /*
                        xoo
                        oxo
                        xoo
                        */
                    return "brick_hole_z";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xoo
                    xxo
                    ooo
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    xxo
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    xox
                    oxo
                    ooo
                    */
                    return "brick_hole_z";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    oxo
                    oxo
                    oxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                     /*
                    ooo
                    xxx
                    ooo
                    */
                    return "brick_hole_o";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                     /*
                    xoo
                    oxo
                    oox
                    */
                    return "brick_hole_z";
                }
            }
            case 5: {
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxx
                    oxx
                    ooo
                    */
                    return "brick_hole_h";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oox
                    oxx
                    oox
                    */
                    return "brick_hole_l";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                      /*
                    ooo
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                      /*
                    ooo
                    oxo
                    xxx
                    */
                    return "brick_hole_m";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    ooo
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                     /*
                    xoo
                    xxo
                    xoo
                    */
                    return "brick_hole_k";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxo
                    ooo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    oxo
                    ooo
                    */
                    return "brick_hole_n";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    ooo
                    xxx
                    xoo
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    ooo
                    xxx
                    oxo
                    */
                    return "brick_hole_q";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    ooo
                    xxx
                    oox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    ooo
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    xoo
                    xxx
                    ooo
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    ooo
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxo
                    oxo
                    oxx
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    oxo
                    oxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxx
                    oxo
                    */
                    return "brick_hole_af";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxo
                    xxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    xxo
                    oxo
                    oxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxo
                    oxo
                    */
                    return "brick_hole_ae";
                }
            }
            case 4: {
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxx
                    oxx
                    */
                    return "brick_hole_ad";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxo
                    xoo
                    */
                    return "brick_hole_j";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxx
                    ooo
                    */
                    return "brick_hole_w";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    oxx
                    ooo
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    oxx
                    oox
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    ooo
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    ooo
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE
                        && types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xoo
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    xxo
                    ooo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    oxx
                    oxo
                    */
                    return "brick_hole_ag";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    oxo
                    oxx
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxx
                    oxx
                    */
                    return "brick_hole_ad";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    oxx
                    xoo
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    oxo
                    xox
                    */
                    return "brick_hole_n";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxx
                    xox
                    */
                    return "brick_hole_x";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    oxx
                    xox
                    */
                    return "brick_hole_l";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxx
                    xxo
                    */
                    return "brick_hole_af";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    oxx
                    xxo
                    */
                    return "brick_hole_ag";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxx
                    ooo
                    */
                    return "brick_hole_u";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    oox
                    */
                    return "brick_hole_y";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    oox
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE
                        && types[x][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    oox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    xoo
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    xoo
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    oxo
                    xxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    oxo
                    */
                    return "brick_hole_ae";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE
                        && types[x + 1][y] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxo
                    xxo
                    */
                    return "brick_hole_ac";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE
                        && types[x + 1][y] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE
                        && types[x + 1][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    xoo
                    */
                    return "brick_hole_y";
                }
            }
            case 3: {
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxo
                    xxo
                    xxx
                    */
                    return "brick_hole_ac";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    oxo
                    xxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    xxx
                    ooo
                    */
                    return "brick_hole_i";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    ooo
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxo
                    xxo
                    */
                    return "brick_hole_g";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    oxx
                    oxx
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    xoo
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    xxx
                    xxo
                    xoo
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    oxx
                    oox
                    */
                    return "brick_hole_h";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxo
                    xxx
                    */
                    return "brick_hole_ac";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE) {
                    /*
                    xxo
                    oxo
                    xxx
                    */
                    return "brick_hole_ab";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxo
                    oxx
                    */
                    return "brick_hole_ah";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxo
                    xox
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    xoo
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    xoo
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    xoo
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    xoo
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    oxx
                    oxo
                    */
                    return "brick_hole_ag";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    xxx
                    oxo
                    oxx
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    xxo
                    oxx
                    oxx
                    */
                    return "brick_hole_ad";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                    /*
                    xox
                    oxx
                    oxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    oxx
                    xox
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    oxx
                    xxo
                    */
                    return "brick_hole_ag";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    oxo
                    xxx
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    oxx
                    xxx
                    */
                    return "brick_hole_ad";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxx
                    oox
                    */
                    return "brick_hole_u";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xox
                    xxx
                    oox
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxx
                    oox
                    */
                    return "brick_hole_w";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    xxo
                    oox
                    */
                    return "brick_hole_j";
                }

                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxx
                    oxo
                    */
                    return "brick_corner_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    oxx
                    */
                    return "brick_hole_ae";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxx
                    xoo
                    */
                    return "brick_hole_u";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xox
                    xxx
                    xoo
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxx
                    xoo
                    */
                    return "brick_hole_w";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxx
                    oxx
                    xoo
                    */
                    return "brick_hole_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    xox
                    */
                    return "brick_hole_y";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    xox
                    */
                    return "brick_hole_v";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    xxo
                    */
                    return "brick_hole_ac";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    xox
                    xxo
                    xxo
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    xxx
                    oxo
                    xxo
                    */
                    return "brick_hole_ab";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    xxx
                    xxo
                    oxo
                    */
                    return "brick_hole_ah";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    xxo
                    */
                    return "brick_corner_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxo
                    xxx
                    */
                    return "brick_hole_ac";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x][y + 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oox
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxx
                    oxo
                    */
                    return "brick_corner_h";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    oxx
                    */
                    return "brick_hole_ae";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxx
                    oxo
                    */
                    return "brick_corner_k";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xox
                    xxx
                    oxo
                    */
                    return "brick_hole_q";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    xxx
                    oxo
                    */
                    return "brick_corner_k";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y] != ETileType.HOLE) {
                    /*
                    xxo
                    oxx
                    xxo
                    */
                    return "brick_hole_af";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    xxo
                    */
                    return "brick_corner_o";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    xox
                    */
                    return "brick_hole_v";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    oxx
                    */
                    return "brick_corner_m";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x - 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxo
                    oxx
                    */
                    return "brick_hole_ae";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    xox
                    xxo
                    xox
                    */
                    return "brick_hole_k";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xxo
                    oxx
                    xxo
                    */
                    return "brick_hole_af";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE &&
                        types[x][y - 1] != ETileType.HOLE) {
                    /*
                    oxo
                    xxx
                    xox
                    */
                    return "brick_hole_v";
                }
                if (types[x][y + 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE &&
                        types[x + 1][y - 1] != ETileType.HOLE) {
                    /*
                    xox
                    xxx
                    oxo
                    */
                    return "brick_hole_q";
                }
            }
            case 2: {
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    oox
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    oxx
                    xxo
                    */
                    return "brick_hole_ag";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                     /*
                    oxx
                    xxo
                    xxx
                    */
                    return "brick_hole_ac";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xoo
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                     /*
                    xxo
                    xxo
                    xxx
                    */
                    return "brick_hole_g";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    xxo
                    xxo
                    */
                    return "brick_hole_g";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    xxx
                    xoo
                    */
                    return "brick_hole_i";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x - 1][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    xxx
                    oox
                    */
                    return "brick_hole_i";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    oxx
                    oxx
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxx
                    oxx
                    xxx
                    */
                    return "brick_hole_d";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxx
                    xxx
                    oxx
                    */
                    return "brick_corner_g";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxx
                    xxx
                    xox
                    */
                    return "brick_hole_u";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxx
                    xxx
                    xxo
                    */
                    return "brick_corner_i";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxx
                    xxo
                    xxx
                    */
                    return "brick_hole_ac";
                }
                if (types[x + 1][y + 1] != ETileType.HOLE && types[x - 1][y + 1] != ETileType.HOLE) {
                     /*
                    oxo
                    xxx
                    xxx
                    */
                    return "brick_corner_e";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xox
                    oxx
                    xxx
                    */
                    return "brick_hole_a";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xox
                    xxx
                    oxx
                    */
                    return "brick_hole_r";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xox
                    xxx
                    xox
                    */
                    return "brick_hole_o";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xox
                    xxx
                    xxo
                    */
                    return "brick_hole_p";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xox
                    xxo
                    xxx
                    */
                    return "brick_hole_c";
                }
                if (types[x - 1][y] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                     /*
                    xxo
                    oxx
                    xxx
                    */
                    return "brick_hole_ad";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                     /*
                    xxo
                    xxx
                    oxx
                    */
                    return "brick_corner_l";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                     /*
                    xxo
                    xxx
                    xox
                    */
                    return "brick_hole_w";
                }
                if (types[x + 1][y] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    oxo
                    xxx
                    */
                    return "brick_hole_ab";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    oxx
                    xox
                    */
                    return "brick_hole_h";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x - 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    oxx
                    xxo
                    */
                    return "brick_hole_ag";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    xxo
                    oxx
                    */
                    return "brick_hole_ah";
                }
                if (types[x][y - 1] != ETileType.HOLE && types[x + 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    xxo
                    xox
                    */
                    return "brick_hole_j";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE && types[x + 1][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    xxx
                    oxo
                    */
                    return "brick_corner_f";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE && types[x + 1][y + 1] != ETileType.HOLE) {
                     /*
                    xxo
                    xxx
                    xxo
                    */
                    return "brick_corner_j";
                }
            }
            case 1: {
                if (types[x + 1][y + 1] != ETileType.HOLE) {
                     /*
                    xxo
                    xxx
                    xxx
                    */
                    return "brick_corner_b";
                }
                if (types[x + 1][y - 1] != ETileType.HOLE) {
                      /*
                    xxx
                    xxx
                    xxo
                    */
                    return "brick_corner_d";
                }
                if (types[x - 1][y + 1] != ETileType.HOLE) {
                    /*
                    oxx
                    xxx
                    xxx
                    */
                    return "brick_corner_a";
                }
                if (types[x - 1][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    xxx
                    oxx
                    */
                    return "brick_corner_c";
                }
                if (types[x][y + 1] != ETileType.HOLE) {
                     /*
                    xox
                    xxx
                    xxx
                    */
                    return "brick_hole_b";
                }
                if (types[x + 1][y] != ETileType.HOLE) {
                     /*
                    xxx
                    xxo
                    xxx
                    */
                    return "brick_hole_g";
                }
                if (types[x][y - 1] != ETileType.HOLE) {
                     /*
                    xxx
                    xxx
                    xox
                    */
                    return "brick_hole_i";
                }
                if (types[x - 1][y] != ETileType.HOLE) {
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

        if (x < 2 || y < 2 || x > resolution - 2 || y > resolution - 2) {
            return 0;
        }

        if (types[x][y + 1] != ETileType.HOLE) {
            counter++;
        }
        if (types[x][y - 1] != ETileType.HOLE) {
            counter++;
        }
        if (types[x - 1][y] != ETileType.HOLE) {
            counter++;
        }
        if (types[x + 1][y] != ETileType.HOLE) {
            counter++;
        }
        if (types[x + 1][y + 1] != ETileType.HOLE) {
            counter++;
        }
        if (types[x + 1][y - 1] != ETileType.HOLE) {
            counter++;
        }
        if (types[x - 1][y - 1] != ETileType.HOLE) {
            counter++;
        }
        if (types[x - 1][y + 1] != ETileType.HOLE) {
            counter++;
        }

        return counter;
    }

    private int getWallNeigh(int x, int y) {
        int counter_wall = 0;

        if (x < 2 || y < 2 || x > resolution - 2 || y > resolution - 2) {
            return 0;
        }

        if (types[x][y] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x][y + 1] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x][y - 1] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x - 1][y] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x + 1][y] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x + 1][y + 1] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x + 1][y - 1] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x - 1][y - 1] == ETileType.WALL) {
            counter_wall++;
        }
        if (types[x - 1][y + 1] == ETileType.WALL) {
            counter_wall++;
        }

        return counter_wall;
    }

    private void generateTypes() {
        types = new ETileType[resolution][resolution];

        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                if (i == 0 || k == 0 || i == 1 || k == 1 || i == resolution - 1 || k == resolution - 1 || i == resolution - 2 || k == resolution - 2) {
                    types[i][k] = ETileType.HOLE;
                } else {
                    if (map[i][k] < seaLevel) {
                        types[i][k] = ETileType.HOLE;
                    } else {
                        if (buildingMap[i][k] > buildingLevel) {
                            types[i][k] = ETileType.WALL;
                        } else {
                            types[i][k] = ETileType.STONE;
                        }
                    }
                }
            }
        }
    }

    public Vector3D getRandomSpawnPosition() {
        Random rnd = new Random();
        int x = rnd.nextInt(resolution);
        int y = rnd.nextInt(resolution);
        while (!getTileByNeighbor(x, y).equals("stone_a")) {
            x = rnd.nextInt(resolution);
            y = rnd.nextInt(resolution);
        }

        return new Vector3D(x * 64 - resolution / 2 * 64, resolution / 2 * 64 - y * 64, 100);
    }


}
