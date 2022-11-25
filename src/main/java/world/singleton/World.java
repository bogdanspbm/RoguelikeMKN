package world.singleton;

import config.Config;
import enemies.controller.BotController;
import interfaces.Collidable;
import interfaces.Damageable;
import objects.collision.Collision;
import objects.controller.Controller;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import objects.projectile.factory.ProjectileFactory;
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

    private List<Damageable> damageables = new ArrayList<>();

    private List<Projectile> projectiles = new ArrayList<>();

    private List<Controller> controllers = new ArrayList<>();

    private World() {
        startTick();
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

    private void startTick() {
        Thread tick = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                while (true) {
                    pawns.forEach(pawn -> {
                        pawn.tryFall();
                        pawn.tick();
                    });

                    projectiles.forEach(projectile -> {
                        projectile.tick();
                        projectileCollide(projectile);
                    });

                    controllers.forEach(controller -> {
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
        tick.start();    //Запуск потока
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
        damageables.add(pawn);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void projectileCollide(Projectile projectile) {
        for (Damageable damageable : damageables) {
            if (!projectile.hasDamaged(damageable)) {
                Collision collision = damageable.getCollision();
                if (collision.collide(projectile.getCollision())) {
                    damageable.applyDamage(projectile.getDamage(), projectile);
                    projectile.addDamaged(damageable);
                }
            }
        }
    }

    public boolean checkCollides(Collision collision, Vector3D location) {
        for (Tile tile : tiles) {
            if (collision.collide(tile.getCollision(), location)) {
                return true;
            }
        }

        for (Pawn pawn : pawns) {
            if (!pawn.getCollision().equals(collision)) {
                if (collision.collide(pawn.getCollision(), location)) {
                    return true;
                }
            }
        }
        return false;
    }
}
