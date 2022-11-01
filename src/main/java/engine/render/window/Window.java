package engine.render.window;

import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.panel.RenderPanel;

import javax.swing.*;
import java.util.List;

public class Window extends JFrame {

    RenderPanel renderPanel;

    public Window() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 300));
        setPreferredSize(new java.awt.Dimension(500, 300));
    }

    public void createRenderPanel(DrawableProvider provider) {
        if (renderPanel != null) {
            remove(renderPanel);
        }

        renderPanel = new RenderPanel(provider);
        add(renderPanel);

        renderPanel.updateUI();
    }


}
