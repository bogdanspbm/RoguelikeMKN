package generator;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class LevelGenerator {

    static int resolution = 1024;



    public static void main(String[] args) {

        PerlinNoiseGenerator noiseForEachCell = new PerlinNoiseGenerator(0);

        try {
        ImageIO.write(noiseForEachCell.getMap(),"png", new File("noise.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
