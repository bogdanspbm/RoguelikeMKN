package world.singleton;

import config.Config;
import engine.render.interfaces.Drawable;
import interfaces.Damageable;
import interfaces.Interactive;
import inventory.objects.Item;
import objects.World;
import objects.collision.Collision;
import objects.controller.Controller;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import player.controller.PlayerController;
import structures.Vector3D;
import world.map.Map;
import world.map.MapBuilder;
import world.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Processor {
    private volatile static Processor singleton;

    World world = new World();

    private Processor() {
        MapBuilder builder = new MapBuilder("last_world.wld");
        //builder.setResolution(32);
        world.setMap(new Map(builder));
        world.setTiles(world.getMap().getTiles());
        world.getMap().exportToFile("last_world.wld");
        sortTiles();
        startTick();
    }

    public Pawn getPlayerPawn(int index) {
        int counter = 0;
        Pawn lastPawn = null;
        for (Pawn pawn : world.getPawns()) {
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

    private void sortTiles() {
        Collections.sort(world.getTiles());
    }

    private void startTick() {
        Thread tick = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                while (true) {
                    world.getPawns().forEach(pawn -> {
                        pawn.tryFall();
                        pawn.tick();
                    });

                    world.getProjectiles().forEach(projectile -> {
                        projectile.tick();
                        projectileCollide(projectile);
                    });

                    world.getControllers().forEach(controller -> {
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


    public static Processor getWorld() {
        if (singleton == null) {
            synchronized (Processor.class) {
                singleton = new Processor();
            }
        }

        return singleton;
    }

    public Map getMap() {
        return world.getMap();
    }

    public void addControllers(Controller controller) {
        world.getControllers().add(controller);
    }


    public void addPawn(Pawn pawn) {
        world.getPawns().add(pawn);
        world.getDamageables().add(pawn);
        addControllers(pawn.getController());
    }

    public void addProjectile(Projectile projectile) {
        world.getProjectiles().add(projectile);
    }

    public List<Pawn> getPawns() {
        return world.getPawns().stream().toList();
    }


    public void deleteProjectile(Projectile projectile) {
        world.getProjectiles().remove(projectile);
    }

    public void projectileCollide(Projectile projectile) {
        for (Damageable damageable : world.getDamageables()) {
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

        for (Tile tile : world.getTiles()) {
            if (collision.collide(tile.getCollision(), location)) {
                return true;
            }
        }

        for (Pawn pawn : world.getPawns()) {
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

        for (Item item : world.getItems()) {
            if (pawn.getCollision().collide(item.getCollision())) {
                item.startOverlap(pawn);
            } else {
                item.stopOverlap(pawn);
            }
        }
    }

    public Item createItem(int id, int quantity) {
        Item result = world.getItemFactory().createItem(id, quantity);
        world.getItems().add(result);
        return result;
    }

    private void cleanItems() {
        world.getItems().removeIf(item -> (item == null || item.getQuantity() == 0 || item.getId() == -1));
    }

    public List<Drawable> getDrawables() {
        List<Drawable> result = new ArrayList<>();

        // TODO: Remove from tick event
        cleanItems();

        int i = 0;
        int j = 0;

        while (i < world.getTiles().size() && j < world.getPawns().size()) {
            Tile tile = world.getTiles().get(i);
            Pawn pawn = getPawns().get(j);

            if (pawn.compareTo(tile) < 0) {
                result.add(pawn);
                j++;
            } else {
                result.add(tile);
                i++;
            }
        }

        while (i < world.getTiles().size()) {
            Tile tile = world.getTiles().get(i);
            result.add(tile);
            i++;
        }

        while (j < world.getPawns().size()) {
            Pawn pawn = getPawns().get(j);
            result.add(pawn);
            j++;
        }

        for (Item item : world.getItems()) {
            result.add(item);
        }

        for (Projectile projectile : world.getProjectiles()) {
            result.add(projectile);
        }

        return result;
    }

    public List<Interactive> getOverlappedInteractions(Pawn instigator) {
        List<Interactive> result = new ArrayList<>();

        for (Item item : world.getItems()) {
            if (item.hasOverlapped(instigator)) {
                result.add(item);
            }
        }

        return result;
    }

    public void removePawn(Pawn pawn) {
        world.getControllers().remove(pawn.getController());
        world.getPawns().remove(pawn);
    }
}
