package ui.buttons;

import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JPanel {

    double percent = 0;

    public ProgressBar() {

    }

    public void setPercent(double percent) {
        this.percent = percent;
    }


    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) grphcs;

        graphics.setColor(new Color(200, 200, 200));
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(new Color(200, 200, 50));
        graphics.fillRect(0, 0, (int) (width * this.percent), height);
    }
}
