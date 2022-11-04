package engine.processer;

import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;
import exceptions.CreationException;
import interfaces.Placeable;
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

    private void generateTestTiles() throws IOException, CreationException {
        HashMap<String, AnimationSource> sources = new HashMap<>();
        sources.put("grass", new AnimationSource(new File("src/main/resources/tiles/landscape/grass.png")));

        // TODO: Перенести хранение тайлов в Database
        StaticTileFactory factory = new StaticTileFactory(sources);
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                int height = 1;
                if (i == 0 || k == 0) {
                    height = 2;
                } else {
                    Random rnd = new Random();
                    height = 1 + rnd.nextInt(2);
                }
                for (int j = 0; j < height; j++) {
                    Tile tile = factory.createTile("grass");
                    tile.setLocation(new Vector3D(i * 64 - k * 32 + 128, k * 16 + 128, j * 32));
                    getWorld().addTile(tile);
                }
            }
        }

        getWorld().sortTiles();
    }


    @Override
    public List<Drawable> getDrawable() {
        return formDrawableList();
    }
}
