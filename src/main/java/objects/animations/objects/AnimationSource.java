package objects.animations.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimationSource {
    private BufferedImage image;
    private List<BufferedImage> imageFrames = new ArrayList<>();

    private int x, y;

    public AnimationSource(File file, Dimension frameSize) throws IOException {
        image = ImageIO.read(file);
        cropImage(frameSize);
    }

    private void cropImage(Dimension frameSize) {
        x = image.getWidth() / frameSize.width;
        y = image.getHeight() / frameSize.height;


        for (int k = 0; k < y; k++) {
            for (int i = 0; i < x; i++) {
                BufferedImage frame = image.getSubimage(i * frameSize.width, k * frameSize.height, frameSize.width, frameSize.height);
                imageFrames.add(frame);
            }
        }
    }

    public BufferedImage getFrame(int x, int y) {
        return imageFrames.get(y * this.x + x);
    }

    public BufferedImage getImage() {
        return image;
    }
}
