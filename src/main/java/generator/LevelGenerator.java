package generator;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Arrays;

public class LevelGenerator {
    static float [] attitude = new float[1024];




    public static void main(String[] args) {

        PerlinNoiseGenerator noiseForEachCell = new PerlinNoiseGenerator(0,attitude);

        float y = 0f;
        // made perlin noise for each cell of 1024
        for(int i = 0; i < 32;i++){
            for(int j = 0; j < 32;j++){
                attitude[i * 32 + j] = noiseForEachCell.noise((float)i, y,3,0.5f);
                y += 1/32f; // 32 cells
            }
            y = 0f;
        }
//        System.out.println(Arrays.toString(attitude));


        try {
        ImageIO.write(noiseForEachCell.getMap(),"png", new File("noise.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
