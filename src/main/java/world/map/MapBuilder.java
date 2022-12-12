package world.map;

public class MapBuilder {

    private int resolution = 64;
    private int seaLevel = 120;

    private String path;

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public void setMapPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setSeaLevel(int level) {
        seaLevel = level;
    }

    public int getSeaLevel() {
        return seaLevel;
    }
}
