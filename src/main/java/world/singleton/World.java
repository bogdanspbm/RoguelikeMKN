package world.singleton;

import config.Config;
import enemies.controller.BotController;
import engine.render.interfaces.Drawable;
import interfaces.Collidable;
import interfaces.Damageable;
import inventory.factory.ItemFactory;
import inventory.objects.Item;
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

    private List<Item> items = new ArrayList<>();

    private ItemFactory itemFactory = new ItemFactory();

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

    public Item createItem(int id, int quantity) {
        Item result = itemFactory.createItem(id, quantity);
        items.add(result);
        return result;
    }

    public List<Drawable> getDrawables() {
        List<Drawable> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < tiles.size() && j < pawns.size()) {
            Tile tile = tiles.get(i);
            Pawn pawn = pawns.get(j);

            if (pawn.compareTo(tile) < 0) {
                result.add(pawn);
                j++;
            } else {
                result.add(tile);
                i++;
            }
        }

        while (i < tiles.size()) {
            Tile tile = tiles.get(i);
            result.add(tile);
            i++;
        }

        while (j < pawns.size()) {
            Pawn pawn = pawns.get(j);
            result.add(pawn);
            j++;
        }

        for (Item item : items) {
            result.add(item);
        }

        for (Projectile projectile : projectiles) {
            result.add(projectile);
        }

        return result;
    }
}
