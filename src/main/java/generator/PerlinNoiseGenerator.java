package generator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Random;

public class PerlinNoiseGenerator {
    private final Random rand = new Random();

    public final int length;
    public final float[] values;

    public PerlinNoiseGenerator(int length, int size){
        this.length = length;
        values = new float[length];

        Arrays.fill(values, -1);

        for (int x = 0; x < length; x += size){
            set(x, rand.nextFloat() * 2 -1);
        }

        float scale = 1.0f / length;
        do{
            int half = size/2;
            for (int x = 0; x < length; x += size){
                float a = get(x);
                float b = get(x + size);

                set(x + half, (a + b) / 2.0f + (rand.nextFloat() * 2 - 1) * size * scale);
            }
            size /= 2;
        } while (size > 1);
    }

    public void set(int pos, float val) {
          if (pos < 0 || pos >= length) return;
          values[pos] = val;
    }
    public float get(int pos) {
          if (pos < 0 || pos >= length) return 0;
          return values[pos];
    }

    public BufferedImage getMap() {
        BufferedImage map = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) map.getRaster().getDataBuffer()).getData();
        for (int x = 0; x < length; x++) {
            boolean fill = false;
            for (int y = 0; y < length; y++) {
                int i = x + y * length;
                if (pixels[i] > -1) fill = true;
                int val = (int) (values[x] * 128) + 128;
                pixels[i] = (val << 1); // | (val << 8) | val
                if (fill) pixels[i] = val;
            }
        }
        return map;
    }

}
