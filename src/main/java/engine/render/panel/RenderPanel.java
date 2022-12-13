package engine.render.panel;

import engine.render.interfaces.DrawableProvider;
import interfaces.Placeable;
import inventory.ui.InventoryPanel;

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

        Placeable camera = provider.getCamera();


        g.translate(-camera.getLocation().x() + getWidth() / 2, -(camera.getLocation().y() - camera.getLocation().z()) + getHeight() / 2);

        provider.getDrawable().forEach(
                drawable -> {
                    drawable.draw(g);
                });

        g.translate(camera.getLocation().x() - getWidth() / 2, (camera.getLocation().y() - camera.getLocation().z()) - getHeight() / 2);

    }
}
