package utils;

import objects.pawn.Pawn;
import structures.Vector3D;

import java.util.Random;

import static world.singleton.Processor.getWorld;

public class WorldUtils {

    private static int ITER_LIMIT = 1000;

    public static Vector3D getRandomLocationNearPoint(Pawn owner, Vector3D center, int maxRadius) {
        Random random = new Random();
        Vector3D newPoint = center;
        int counter = 0;


        while (getWorld().checkCollides(owner.getCollision(), newPoint) && counter < ITER_LIMIT) {
            int x = center.x() + random.nextInt(2 * maxRadius) - maxRadius;
            int y = center.y() + random.nextInt(2 * maxRadius) - maxRadius;
            newPoint = new Vector3D(x, y, center.z() + 10);
            counter += 1;
        }

        return newPoint;
    }
}
