package singleton;

import objects.animations.objects.TextureSource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageBuffer {
    private static Map<String, TextureSource> buffer = new HashMap<>();

    public static TextureSource getImageFromBuffer(String path) {
        if (!buffer.containsKey(path)) {
            try {
                TextureSource source = new TextureSource(new File(path));
                buffer.put(path, source);
            } catch (Exception e) {
                return null;
            }
        }

        return buffer.get(path);
    }

    public static TextureSource getImageFromBuffer(String path, Dimension dimension) {
        if (!buffer.containsKey(path)) {
            try {
                TextureSource source = new TextureSource(new File(path), dimension);
                buffer.put(path, source);
            } catch (Exception e) {
                return null;
            }
        }

        return buffer.get(path);
    }

}
