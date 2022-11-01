package engine.processer;

import engine.render.window.Window;

public class GameProcesser {

    public void start() {
        createWindow();
    }

    private Window window;

    private void createWindow() {
        window = new Window();
        window.setVisible(true);
    }
}
