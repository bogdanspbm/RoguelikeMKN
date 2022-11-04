package generator;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Random;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

//public class PerlinNoiseGenerator {
//    private final Random rand = new Random();
//
//    public final int length;
//    public final float[] values;
//
//    public PerlinNoiseGenerator(int length, int size){
//        this.length = length;
//        values = new float[length]; //create massive of numbers for method
//
//        Arrays.fill(values, -1);
//
//        for (int x = 0; x < length; x += size){
//            set(x, rand.nextFloat() * 2 - 1); //for each element from massive assign random number
//        }
//
//        float scale = 1.0f / length;
//        do{
//            int half = size/2;
//            for (int x = 0; x < length; x += size){
//                float a = get(x); // return element from massive on x position
//                float b = get(x + size);
//
//                set(x + half, (a + b) / 2.0f + (rand.nextFloat() * 2 - 1) * size * scale);  //linear interpolation
//            }
//            size /= 2;
//        } while (size > 1);
//    }
//
//    public void set(int pos, float val) {
//          if (pos < 0 || pos >= length) return; // if we don't work with elements from our massive we return nothing
//          values[pos] = val; // to our element assign value
//    }
//    public float get(int pos) {
//          if (pos < 0 || pos >= length) return 0;
//          return values[pos];
//    }
//
//    public BufferedImage getMap() {
//        BufferedImage map = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
//        int[] pixels = ((DataBufferInt) map.getRaster().getDataBuffer()).getData();
//        for (int x = 0; x < length; x++) {
//            boolean fill = false;
//            for (int y = 0; y < length; y++) {
//                int i = x + y * length;
//                if (pixels[i] > -1) fill = true;
//                int val = (int) (values[x] * 128) + 128;
//                pixels[i] = (val << 2); // | (val << 8) | val
//                if (fill) pixels[i] = val;
//            }
//        }
//        return map;
//    }
//
//}

class PerlinNoiseGenerator
{
    byte[] permutationTable;
    static float [] attitude = new float[1024];

    public PerlinNoiseGenerator(int seed, float[] attitude)
    {
        Random rand = new Random(seed);
        permutationTable = new byte[1024];
        rand.nextBytes(permutationTable);
        this.attitude= attitude;

    }

    private float[] getPseudoRandomGradientVector(int x, int y)
    {
        int v = (int)(((x * 7) ^ (y * 773373) + 7777777) & 1023);
//        int v = (int)(((x * 1836311903) ^ (y * 2971215073) + 4807526976) & 1023);

        v = permutationTable[v]&3;

        switch (v)
        {
            case 0:  return new float[]{  1, 0 };
            case 1:  return new float[]{ -1, 0 };
            case 2:  return new float[]{  0, 1 };
            default: return new float[]{  0,-1 };
        }
    }

    static float qunticCurve(float t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    static float lerp(float a, float b, float t)
    {
        return a + (b - a) * t;
    }

    static float dot(float[] a, float[] b)
    {
        return a[0] * b[0] + a[1] * b[1];
    }

    public float noise(float fx, float fy)
    {
        int left = (int)Math.floor(fx);
        int top  = (int)Math.floor(fy);
        float pointInQuadX = fx - left;
        float pointInQuadY = fy - top;

        float[] topLeftGradient     = getPseudoRandomGradientVector(left,   top  );
        float[] topRightGradient    = getPseudoRandomGradientVector(left+1, top  );
        float[] bottomLeftGradient  = getPseudoRandomGradientVector(left,   top+1);
        float[] bottomRightGradient = getPseudoRandomGradientVector(left+1, top+1);

        float[] distanceToTopLeft     = new float[]{ pointInQuadX,   pointInQuadY   };
        float[] distanceToTopRight    = new float[]{ pointInQuadX-1, pointInQuadY   };
        float[] distanceToBottomLeft  = new float[]{ pointInQuadX,   pointInQuadY-1 };
        float[] distanceToBottomRight = new float[]{ pointInQuadX-1, pointInQuadY-1 };

//        Vector2D<Float> a = new Vector2D<>(pointInQuadX,pointInQuadY);
//        Vector2D<Float> b = new Vector2D<>(pointInQuadX-1,pointInQuadY);
//        a.dot(b);
        float tx1 = dot(distanceToTopLeft,     topLeftGradient);
        float tx2 = dot(distanceToTopRight,    topRightGradient);
        float bx1 = dot(distanceToBottomLeft,  bottomLeftGradient);
        float bx2 = dot(distanceToBottomRight, bottomRightGradient);

        pointInQuadX = qunticCurve(pointInQuadX);
        pointInQuadY = qunticCurve(pointInQuadY);

        float tx = lerp(tx1, tx2, pointInQuadX);
        float bx = lerp(bx1, bx2, pointInQuadX);
        float tb = lerp(tx, bx, pointInQuadY);

        return tb;

    }

    public float noise(float fx, float fy, int octaves, float persistence)
    {
        float amplitude = 1;
        float max = 0;
        float result = 0;

        while (octaves-- > 0)
        {
            max += amplitude;
            result += noise(fx, fy) * amplitude;
            amplitude *= persistence;
            fx *= 2;
            fy *= 2;
        }
        return result/max;
    }

    public BufferedImage getMap() {
        BufferedImage map = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) map.getRaster().getDataBuffer()).getData();
        for (int xx = 0; xx < 32; xx++) {
            for (int yy = 0; yy < 32; yy++) {
                int i = xx * 32 + yy;
                int val = (int) ((attitude[i]+1)/2*128);
//                System.out.println(val);
                pixels[i] = val<<4;
            }
        }
        System.out.println(Arrays.toString(pixels));
        return map;
    }
}
