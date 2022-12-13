package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class ImageUtils {

    // TODO: Doesn't work (
    public static BufferedImage applyColorShader(BufferedImage input, Color color) {
        BufferedImage gray = new BufferedImage(input.getWidth(), input.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = gray.getGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        float red = color.getRed();
        float green = color.getGreen();
        float blue = color.getBlue();
        if (red > 0.0f) red = 255.0f / red * 100f;
        if (green > 0.0f) green = 255.0f / green * 100f;
        if (blue > 0.0f) blue = 255.0f / blue * 100f;

        float[] factors = new float[]{
                1.0f, 1.0f, 1.0f, 1.0f
        };

        float[] offsets = new float[]{
                red, green, blue, 0
        };

        RescaleOp op = new RescaleOp(factors, offsets, null);
        return op.filter(gray, output);
    }
}
