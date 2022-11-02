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

public class GameProcesser implements DrawableProvider {

    List<Pawn> pawns = new ArrayList<>();

    public void start() {
        try {
            createPlayer();
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
        try {
            generateTestTiles().forEach(tile -> result.add(tile));
        } catch (Exception e) {

        }

        pawns.forEach(pawn -> result.add(pawn));
        return result;
    }

    private List<Tile> generateTestTiles() throws IOException {
        ArrayList<Tile> result = new ArrayList<>();
        HashMap<String, AnimationSource> sources = new HashMap<>();
        sources.put("grass", new AnimationSource(new File("src/main/resources/tiles/landscape/grass.png")));

        // TODO: Перенести хранение тайлов в Database
        StaticTileFactory factory = new StaticTileFactory(sources);
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Tile tile = factory.createTile("grass");
                tile.setLocation(new Vector3D(i * 60 - k * 30, k * 15, 0));
                result.add(tile);
            }
        }

        return result;
    }


    @Override
    public List<Drawable> getDrawable() {
        return formDrawableList();
    }
}
