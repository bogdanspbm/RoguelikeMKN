package objects;

import interfaces.Damageable;
import inventory.factory.ItemFactory;
import inventory.objects.Item;
import objects.controller.Controller;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import world.map.Map;
import world.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private Map map;

    private Queue<Pawn> pawns = new ConcurrentLinkedQueue<>();
    private List<Tile> tiles = new ArrayList<>();

    private Queue<Damageable> damageables = new ConcurrentLinkedQueue<>();

    private Queue<Projectile> projectiles = new ConcurrentLinkedQueue<>();

    private Queue<Controller> controllers = new ConcurrentLinkedQueue<>();

    private List<Item> items = new ArrayList<>();

    private ItemFactory itemFactory = new ItemFactory();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Queue<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(Queue<Pawn> pawns) {
        this.pawns = pawns;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public Queue<Damageable> getDamageables() {
        return damageables;
    }

    public void setDamageables(Queue<Damageable> damageables) {
        this.damageables = damageables;
    }

    public Queue<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(Queue<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public Queue<Controller> getControllers() {
        return controllers;
    }

    public void setControllers(Queue<Controller> controllers) {
        this.controllers = controllers;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    public void setItemFactory(ItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }
}
