package world.singleton;

import config.Config;
import interfaces.Collidable;
import objects.collision.Collision;
import objects.pawn.Pawn;
import structures.Vector3D;
import world.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    private volatile static World singleton;

    private List<Pawn> pawns = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

    private World() {
        startPhysics();
    }

    public void sortTiles() {
        Collections.sort(tiles);
    }

    private void startPhysics() {

        Thread physics = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                while (true) {
                    pawns.forEach(pawn -> {
                        pawn.tryFall();
                        // TODO: Потом нужно будет перенести все в Tick
                        pawn.tick();
                    });
                    try {
                        Thread.sleep((int) (1000 / Config.FRAME_RATE));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        physics.start();    //Запуск потока

    }


    public static World getWorld() {
        if (singleton == null) {
            synchronized (World.class) {
                singleton = new World();
            }
        }

        return singleton;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void addPawn(Pawn pawn) {
        pawns.add(pawn);
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public boolean checkCollides(Collision collision, Vector3D location) {
        for (Tile tile : tiles) {
            if (collision.collide(tile.getCollision(), location)) {
                return true;
            }
        }
        return false;
    }
}
