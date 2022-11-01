package engine.processer;

import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;
import exceptions.CreationException;
import objects.pawn.Pawn;
import player.Player;
import structures.Vector2D;

import java.util.ArrayList;
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
        player.setLocation(new Vector2D(100, 100));
    }

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    private List<Drawable> formDrawableList() {
        List<Drawable> result = new ArrayList<>();

        pawns.forEach(pawn -> result.add(pawn));

        return result;
    }

    @Override
    public List<Drawable> getDrawable() {
        return formDrawableList();
    }
}
