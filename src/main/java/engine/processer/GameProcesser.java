package engine.processer;

import enemies.Enemy;
import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;
import exceptions.CreationException;
import generator.PerlinNoiseGenerator;
import interfaces.Placeable;
import inventory.objects.Item;
import objects.animations.objects.TextureSource;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import player.Player;
import structures.Vector3D;
import world.Tile;
import world.factory.StaticTileFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static world.singleton.World.getWorld;

public class GameProcesser implements DrawableProvider {
    private Window window;

    public void start() {
        try {
            createPlayer();
            createItems();
            createEnemy("coward");//трусливый бот
            createEnemy("aggressor");//агресивный бот
            createEnemy("calm");//статичный бот
        } catch (Exception e) {
            e.printStackTrace();
        }
        createWindow();
    }

    public void createItems() {
        getWorld().createItem(1, 3).setLocation(new Vector3D(100, 50, 0));
        getWorld().createItem(2, 1).setLocation(new Vector3D(100, 150, 0));
    }

    private void createPlayer() throws CreationException {
        Player player = new Player();
        getWorld().addPawn(player);
        player.setLocation(new Vector3D(100, 100, 100));
    }

    private void createEnemy(String typeOfBotsBehaviour) throws CreationException {
        Enemy enemy = new Enemy(typeOfBotsBehaviour);
        getWorld().addPawn(enemy);
        getWorld().addControllers(enemy.getController());
        switch (typeOfBotsBehaviour) {
            case ("coward"):
                enemy.setLocation(new Vector3D(400, 100, 100));
                break;
            case ("aggressor"):
                enemy.setLocation(new Vector3D(200, 200, 50));
                break;
            case ("calm"):
                enemy.setLocation(new Vector3D(500, 200, 100));
                break;
        }

    }

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    @Override
    public Placeable getCamera() {
        return getWorld().getPawns().get(0);
    }

    @Override
    public List<Drawable> getDrawable() {
        return getWorld().getDrawables();
    }
}
