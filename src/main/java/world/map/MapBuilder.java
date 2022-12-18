package world.map;

import org.json.JSONArray;
import org.json.JSONObject;

import static utils.FileUtils.readFromFile;

public class MapBuilder {

    private int resolution = 64;
    private int seaLevel = 120;

    private int buildingLevel = 150;

    private int[][] map;
    private int[][] buildingMap;

    private String path;

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
}
