package enemies.factory;

import enemies.Enemy;
import objects.spawner.RandomSpawner;
import objects.spawner.Spawner;
import structures.Vector3D;
import world.map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BotFactory {

    protected List<Spawner> spawnerList = new ArrayList<>();

    protected int spawnLimit = 3;
    protected int spawned = 0;

    protected Map parent;

    public abstract Enemy createBot();

    protected Vector3D getRandomLocation() {
        if (spawnerList.size() == 0) {
            Spawner spawner = new RandomSpawner();
            spawner.setParent(parent);
            return spawner.getLocation();
        }

        Random random = new Random();
        int index = random.nextInt(spawnerList.size());

        return spawnerList.get(index).getLocation();
    }

    public void addSpawn(Spawner spawner) {
        spawnerList.add(spawner);
        spawner.setParent(this.parent);
    }

    public void setMap(Map map) {
        this.parent = map;

        for (Spawner spawner : spawnerList) {
            spawner.setParent(map);
        }
    }

    public int getSpawnLimit() {
        return spawnLimit;
    }
}
