package engine.processer;

import enemies.Enemy;
import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;
import exceptions.CreationException;
import generator.PerlinNoiseGenerator;
import interfaces.Placeable;
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

    public void start() {
        try {
            generateWorld();
            createPlayer();
            createEnemy("coward");//трусливый бот
            createEnemy("aggressor");//агресивный бот
            createEnemy("calm");//статичный бот
        } catch (Exception e) {
            e.printStackTrace();
        }
        createWindow();
    }

    private Window window;

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

    private void generateWorld() throws IOException, CreationException {
        HashMap<String, TextureSource> sources = new HashMap<>();
        sources.put("stone_a", new TextureSource(new File("src/main/resources/tiles/stone_a.png")));

        PerlinNoiseGenerator generator = new PerlinNoiseGenerator(32, 8);
        int[][] map = generator.getMap();

        // TODO: Перенести хранение тайлов в Database
        StaticTileFactory factory = new StaticTileFactory(sources);
        for (int i = 0; i < 16; i++) {
            for (int k = 0; k < 16; k++) {
                Tile tile = factory.createTile("stone_a");
                tile.setLocation(new Vector3D(i * 64, k * 64, 0));
                getWorld().addTile(tile);
            }
        }


        getWorld().sortTiles();
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
