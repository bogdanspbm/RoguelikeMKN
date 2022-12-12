package generator.ui;

import javax.swing.*;
import java.awt.*;

public class NoisePanel extends JPanel {

    int[][] map;
    int scale = 1;
    int level = 120;

    public NoisePanel(int[][] map, int level) {
        this.map = map;
        this.level = level;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                int intensity = map[i][k];
                if (intensity > level) {
                    grphcs.setColor(new Color(0, 0, 0));
                } else {
                    grphcs.setColor(new Color(255, 255, 255));
                }
                grphcs.fillRect(i * scale, k * scale, scale, scale);
            }
        }
    }
}
