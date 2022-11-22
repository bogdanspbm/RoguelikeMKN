package world.singleton;

import config.Config;
import enemies.controller.BotController;
import interfaces.Collidable;
import objects.collision.Collision;
import objects.controller.Controller;
import objects.pawn.Pawn;
import player.controller.PlayerController;
import structures.Vector3D;
import world.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    private volatile static World singleton;

    private List<Pawn> pawns = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

    private List<Controller> controllers = new ArrayList<>();

    private World() {
        startPhysics();
        startControllers();
    }

    public Pawn getPlayerPawn(int index) {
        int counter = 0;
        Pawn lastPawn = null;
        for (Pawn pawn : pawns) {
            if (pawn.getController().getClass().equals(PlayerController.class)) {
                lastPawn = pawn;

                if (index == counter) {
                    break;
                }

                counter++;
            }
        }

        return lastPawn;
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

    private void startControllers() {
        Thread controller = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                while (true) {
                    controllers.forEach(controller -> {
                        // TODO: Потом нужно будет перенести все в Tick
                        controller.tick();
                    });
                    try {
                        Thread.sleep((int) (1000 / Config.FRAME_RATE));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        controller.start();    //Запуск потока
    }


    public static World getWorld() {
        if (singleton == null) {
            synchronized (World.class) {
                singleton = new World();
            }
        }

        return singleton;
    }

    public void addControllers(Controller controller) {
        controllers.add(controller);
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
