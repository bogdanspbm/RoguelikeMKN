package generator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Random;

public class PerlinNoiseGenerator {
    private Random random;
    private byte[] permutationTable;

    private int resolution = 1024;
    public int octaves = 8;


    public PerlinNoiseGenerator(int resolution,int octaves) {
        this.resolution = resolution;
        this.octaves = octaves;
        random = new Random();
        permutationTable = new byte[resolution * resolution];
        random.nextBytes(permutationTable);
    }

    private float[] getPseudoRandomGradientVector(int x, int y) {
        int v = (int) (((x * 1836311903L) ^ (y * 2971215073L) + 4807526976L) & 1023L);
        v = permutationTable[v] & 3;
        switch (v) {
            case 0:
                return new float[]{1, 0};
            case 1:
                return new float[]{-1, 0};
            case 2:
                return new float[]{0, 1};
            default:
                return new float[]{0, -1};
        }
    }

    private float qunticCurve(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private float dot(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1];
    }

    public float noise(float fx, float fy) {
        int left = (int) Math.floor(fx);
        int top = (int) Math.floor(fy);
        float pointInQuadX = fx - left;
        float pointInQuadY = fy - top;

        float[] topLeftGradient = getPseudoRandomGradientVector(left, top);
        float[] topRightGradient = getPseudoRandomGradientVector(left + 1, top);
        float[] bottomLeftGradient = getPseudoRandomGradientVector(left, top + 1);
        float[] bottomRightGradient = getPseudoRandomGradientVector(left + 1, top + 1);

        float[] distanceToTopLeft = new float[]{pointInQuadX, pointInQuadY};
        float[] distanceToTopRight = new float[]{pointInQuadX - 1, pointInQuadY};
        float[] distanceToBottomLeft = new float[]{pointInQuadX, pointInQuadY - 1};
        float[] distanceToBottomRight = new float[]{pointInQuadX - 1, pointInQuadY - 1};

        float tx1 = dot(distanceToTopLeft, topLeftGradient);
        float tx2 = dot(distanceToTopRight, topRightGradient);
        float bx1 = dot(distanceToBottomLeft, bottomLeftGradient);
        float bx2 = dot(distanceToBottomRight, bottomRightGradient);

        pointInQuadX = qunticCurve(pointInQuadX);
        pointInQuadY = qunticCurve(pointInQuadY);

        float tx = lerp(tx1, tx2, pointInQuadX);
        float bx = lerp(bx1, bx2, pointInQuadX);
        float tb = lerp(tx, bx, pointInQuadY);

        return tb;

    }

    public float noise(float fx, float fy, float persistence) {
        float amplitude = 1;
        float max = 0;
        float result = 0;
        int o = octaves;

        while (o-- > 0) {
            max += amplitude;
            result += noise(fx, fy) * amplitude;
            amplitude *= persistence;
            fx *= 2;
            fy *= 2;
        }
        return result / max;
    }


    public int[][] getMap() {
        int[][] res = new int[resolution][resolution];
        for (int xx = 0; xx < resolution; xx++) {
            for (int yy = 0; yy < resolution; yy++) {

                float newVal = noise(xx / 100f, yy / 100f, 0.5f);
                res[xx][yy] = (int) (newVal * 255 + 128) & 255;
            }
        }
        return res;
    }

    public BufferedImage getImage() {
        BufferedImage map = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) map.getRaster().getDataBuffer()).getData();
        for (int xx = 0; xx < resolution; xx++) {
            for (int yy = 0; yy < resolution; yy++) {

                float newVal = noise(xx / 100f, yy / 100f,  0.5f);
                pixels[xx * resolution + yy] = (int) (newVal * 255 + 128) & 255;
            }
        }
        return map;
    }
}
