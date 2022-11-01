package engine.processer;

import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;

import java.util.ArrayList;
import java.util.List;

public class GameProcesser implements DrawableProvider {

    public void start() {
        createWindow();
    }

    private Window window;

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    @Override
    public List<Drawable> getDrawable() {
        return new ArrayList<>();
    }
}
