package world.map;

import enemies.factory.BotFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static utils.FileUtils.readFromFile;

public class MapBuilder {

    private int resolution = 32;
    private int seaLevel = 120;
    private boolean isEmpty = false;

    private int buildingLevel = 150;

    private int[][] map;
    private int[][] buildingMap;

    private String path;

    private List<BotFactory> botFactories = new ArrayList<>();

    public MapBuilder() {

    }

    public MapBuilder(String path) {
        this.path = path;

        String content = readFromFile(path);
        JSONObject jsonContent = new JSONObject(content);

        if (jsonContent.has("resolution")) {
            resolution = jsonContent.getInt("resolution");
        }

        if (jsonContent.has("sea_level")) {
            seaLevel = jsonContent.getInt("sea_level");
        }

        if (jsonContent.has("build_level")) {
            buildingLevel = jsonContent.getInt("build_level");
        }

        if (jsonContent.has("map")) {
            JSONArray mapArr = jsonContent.getJSONArray("map");
            map = new int[resolution][resolution];

            for (int i = 0; i < mapArr.length(); i++) {
                int val = mapArr.getInt(i);
                int x = i / resolution;
                int y = i % resolution;
                map[x][y] = val;
            }
        }

        if (jsonContent.has("walls")) {
            JSONArray buildArr = jsonContent.getJSONArray("walls");
            buildingMap = new int[resolution][resolution];

            for (int i = 0; i < buildArr.length(); i++) {
                int val = buildArr.getInt(i);
                int x = i / resolution;
                int y = i % resolution;
                buildingMap[x][y] = val;
            }
        }
    }

    public List<BotFactory> getBotFactories() {
        return botFactories;
    }

    public void addBotFactory(BotFactory factory) {
        botFactories.add(factory);
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int[][] getBuildingMap() {
        return buildingMap;
    }

    public void setBuildingMap(int[][] buildingMap) {
        this.buildingMap = buildingMap;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public void setSeaLevel(int level) {
        seaLevel = level;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setBuildingLevel(int level) {
        buildingLevel = level;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
