package engine.processer;

import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;
import exceptions.CreationException;
import objects.animations.objects.AnimationSource;
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
import java.util.Random;

import static world.singleton.World.getWorld;

public class GameProcesser implements DrawableProvider {


    public void start() {
        try {
            createPlayer();
            generateTestTiles();
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

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    private List<Drawable> formDrawableList() {
        List<Drawable> result = new ArrayList<>();

        getWorld().getTiles().forEach(tile -> result.add(tile));
        getWorld().getPawns().forEach(pawn -> result.add(pawn));
        return result;
    }

    private void generateTestTiles() throws IOException, CreationException {
        HashMap<String, AnimationSource> sources = new HashMap<>();
        sources.put("grass", new AnimationSource(new File("src/main/resources/tiles/landscape/grass.png")));

        // TODO: Перенести хранение тайлов в Database
        StaticTileFactory factory = new StaticTileFactory(sources);
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Random rnd = new Random();
                int height = 1 + rnd.nextInt(2);
                for (int j = 0; j < height; j++) {
                    Tile tile = factory.createTile("grass");
                    tile.setLocation(new Vector3D(i * 64 - k * 32, k * 16 + 100, j * 32 - 64));
                    getWorld().addTile(tile);
                }
            }
        }
    }


    @Override
    public List<Drawable> getDrawable() {
        return formDrawableList();
    }
}
