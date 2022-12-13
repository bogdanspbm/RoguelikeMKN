package ui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import javax.swing.JPanel;

public class JSmoothPanel extends JPanel {

    int a, b, c, d;
    Color color = getBackground();

    public JSmoothPanel(int a, int b, int c, int d) {
        setOpaque(false);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        color = getBackground();
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color); //To change body of generated methods, choose Tools | Templates.
        this.color = color;
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) grphcs;
        graphics.setColor(color);
        //graphics.setColor(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), getBackground().getAlpha()));

        // Draw Corners
        graphics.fillOval(0, 0, a, a);

        graphics.fillOval(width - b, 0, b, b);

        graphics.fillOval(width - c, height - c, c, c);

        graphics.fillOval(0, height - d, d, d);

        // Draw Borders
        graphics.fillRect(0, a / 2, width / 2, height - d / 2 - a / 2);
        graphics.fillRect(width / 2, b / 2, width, height - c / 2 - b / 2);

        graphics.fillRect(a / 2, 0, width - b / 2 - a / 2, height / 2);
        graphics.fillRect(d / 2, height / 2, width - c / 2 - d / 2, height / 2 + 1);
    }
}