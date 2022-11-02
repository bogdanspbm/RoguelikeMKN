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

public class GameProcesser implements DrawableProvider {

    List<Pawn> pawns = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();

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
        pawns.add(player);
        player.setLocation(new Vector3D(100, 100, 0));
    }

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    private List<Drawable> formDrawableList() {
        List<Drawable> result = new ArrayList<>();

        tiles.forEach(tile -> result.add(tile));
        pawns.forEach(pawn -> result.add(pawn));
        return result;
    }

    private void generateTestTiles() throws IOException {
        tiles = new ArrayList<>();
        HashMap<String, AnimationSource> sources = new HashMap<>();
        sources.put("grass", new AnimationSource(new File("src/main/resources/tiles/landscape/grass.png")));

        // TODO: Перенести хранение тайлов в Database
        StaticTileFactory factory = new StaticTileFactory(sources);
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Random rnd = new Random();
                int z = rnd.nextInt() % 3;
                Tile tile = factory.createTile("grass");
                tile.setLocation(new Vector3D(i * 64 - k * 32, k * 16 - 32 * z + 100, 0));
                tiles.add(tile);
            }
        }
    }


    @Override
    public List<Drawable> getDrawable() {
        return formDrawableList();
    }
}
