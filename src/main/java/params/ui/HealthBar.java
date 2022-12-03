package params.ui;

import params.ParamsComponent;

import javax.swing.*;
import java.awt.*;

public class HealthBar extends JPanel {

    ParamsComponent component;

    public HealthBar(ParamsComponent component) {
        this.component = component;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(20, 20, 20));
        g.fillRect(0, 0, 204, 37);

        int width = (int) (200 * component.getHealthPercentage());

        g.setColor(new Color(180, 40, 40));
        g.fillRect(2, 1, width, 35);

    }
}
