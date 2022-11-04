package engine.render.panel;

import engine.render.interfaces.DrawableProvider;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {

    DrawableProvider provider;

    public RenderPanel(DrawableProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        provider.getDrawable().forEach(
                drawable -> {
                    drawable.draw(g);
                });

    }
}
