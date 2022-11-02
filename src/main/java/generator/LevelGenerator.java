package generator;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class LevelGenerator {
    public static void main(String[] args) {
        PerlinNoiseGenerator noise = new PerlinNoiseGenerator(128,16);

        try {
            ImageIO.write(noise.getMap(),"png", new File("noise.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
