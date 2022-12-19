package objects.spawner;

import structures.Vector3D;

import static world.singleton.Processor.getWorld;

public class RandomSpawner extends Spawner {

    @Override
    public Vector3D getLocation() {
        return parent.getRandomSpawnPosition();
    }
}
