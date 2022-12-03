package world.singleton;

import config.Config;
import enemies.controller.BotController;
import engine.render.interfaces.Drawable;
import enums.ECollideType;
import interfaces.Collidable;
import interfaces.Damageable;
import interfaces.Interactive;
import inventory.factory.ItemFactory;
import inventory.objects.Item;
import objects.collision.Collision;
import objects.controller.Controller;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import objects.projectile.factory.ProjectileFactory;
import player.controller.PlayerController;
import structures.Vector3D;
import world.Map;
import world.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private volatile static World singleton;

    private Map map;

    private Queue<Pawn> pawns = new ConcurrentLinkedQueue<>();
    private List<Tile> tiles = new ArrayList<>();

    private List<Damageable> damageables = new ArrayList<>();

    private Queue<Projectile> projectiles = new ConcurrentLinkedQueue<>();

    private Queue<Controller> controllers = new ConcurrentLinkedQueue<>();

    private List<Item> items = new ArrayList<>();

    private ItemFactory itemFactory = new ItemFactory();

    private World() {
        map = new Map(32);
        tiles = map.getTiles();
        sortTiles();
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
        return pawns.stream().toList();
    }


    public void deleteProjectile(Projectile projectile) {
        projectiles.remove(projectile);
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

        if (collision == null) {
            return false;
        }

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

    // TODO: Don't like difference pawn and collision in input
    public void updateOverlap(Pawn pawn) {

        if (pawn.getCollision() == null) {
            return;
        }

        for (Item item : items) {
            if (pawn.getCollision().collide(item.getCollision())) {
                item.startOverlap(pawn);
            } else {
                item.stopOverlap(pawn);
            }
        }
    }

    public Item createItem(int id, int quantity) {
        Item result = itemFactory.createItem(id, quantity);
        items.add(result);
        return result;
    }

    private void cleanItems() {
        items.removeIf(item -> (item == null || item.getQuantity() == 0 || item.getId() == -1));
    }

    public List<Drawable> getDrawables() {
        List<Drawable> result = new ArrayList<>();

        // TODO: Remove from tick event
        cleanItems();

        int i = 0;
        int j = 0;

        while (i < tiles.size() && j < pawns.size()) {
            Tile tile = tiles.get(i);
            Pawn pawn = getPawns().get(j);

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
            Pawn pawn = getPawns().get(j);
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

    public List<Interactive> getOverlappedInteractions(Pawn instigator) {
        List<Interactive> result = new ArrayList<>();

        for (Item item : items) {
            if (item.hasOverlapped(instigator)) {
                result.add(item);
            }
        }

        return result;
    }
}
