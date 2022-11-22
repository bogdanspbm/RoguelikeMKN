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
            createEnemy();
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

    private void createEnemy() throws CreationException {
        Enemy enemy = new Enemy();
        getWorld().addPawn(enemy);
        getWorld().addControllers(enemy.getController());
        enemy.setLocation(new Vector3D(500, 200, 100));
    }

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    private List<Drawable> formDrawableList() {
        List<Drawable> res = new ArrayList<>();


        int i = 0;
        int j = 0;

        while (i < getWorld().getTiles().size() && j < getWorld().getPawns().size()) {
            Tile tile = getWorld().getTiles().get(i);
            Pawn pawn = getWorld().getPawns().get(j);

            if (pawn.compareTo(tile) < 0) {
                res.add(pawn);
                j++;
            } else {
                res.add(tile);
                i++;
            }
        }

        while (i < getWorld().getTiles().size()) {
            Tile tile = getWorld().getTiles().get(i);
            res.add(tile);
            i++;
        }

        while (j < getWorld().getPawns().size()) {
            Pawn pawn = getWorld().getPawns().get(j);
            res.add(pawn);
            j++;
        }

        return res;
    }

    private void generateWorld() throws IOException, CreationException {
        HashMap<String, TextureSource> sources = new HashMap<>();
        sources.put("snow", new TextureSource(new File("src/main/resources/Sprites/Snow/ISO_Tile_Snow_01.png")));

        PerlinNoiseGenerator generator = new PerlinNoiseGenerator(32, 8);
        int[][] map = generator.getMap();

        // TODO: Перенести хранение тайлов в Database
        StaticTileFactory factory = new StaticTileFactory(sources);
        for (int i = 0; i < 16; i++) {
            for (int k = 0; k < 16; k++) {
                Tile tile = factory.createTile("snow");
                tile.setLocation(new Vector3D(i * 128 - k * 64 - 128, k * 32 - 128, -map[i][k] * 2));
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
        return formDrawableList();
    }
}
