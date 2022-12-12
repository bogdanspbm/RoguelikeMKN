package generator.ui;

import javax.swing.*;
import java.awt.*;

public class NoisePanel extends JPanel {

    int[][] map;
    int scale = 1;

    public NoisePanel(int[][] map) {
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                int intensity = map[i][k];
                grphcs.setColor(new Color(intensity, intensity, intensity));
                grphcs.fillRect(i * scale, k * scale, scale, scale);
            }
        }
    }
}
