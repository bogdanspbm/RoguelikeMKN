package generator;

import structures.Vector3F;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class PerlinNoiseGenerator {
    private int cellCount = 200; // было 150
    private Vector3F[][] verticesMatrix;

    private int[][] heightMap;
    private double[] verticesToBuffer;
    private double scaler = 1; // было 0.25f
    private double defaultFreq = 0.15;
    private double defaultAmplitude = 8;
    private double defaultPersis = 0.2;
    private double height = 4;
    private Vector3F[] buildingLocations;
    private int inum = 1;
    private int NUM_OCTAVES = 5;
    Date date = new Date();
    Random rnd = new Random(date.getTime());

    public PerlinNoiseGenerator() {
        verticesToBuffer = new double[(cellCount - 1) * (cellCount - 1) * 18];
        verticesMatrix = new Vector3F[cellCount][cellCount];
        heightMap = new int[cellCount][cellCount];
        refresh();
    }

    public PerlinNoiseGenerator(double scale, int size) {
        this.cellCount = size;
        this.scaler = scale;
        verticesToBuffer = new double[(cellCount - 1) * (cellCount - 1) * 18];
        verticesMatrix = new Vector3F[cellCount][cellCount];
        heightMap = new int[cellCount][cellCount];
        refresh();
    }

    public PerlinNoiseGenerator(double scale, int size, float height) {
        this.cellCount = size;
        this.height = height;
        this.scaler = scale;
        verticesToBuffer = new double[(cellCount - 1) * (cellCount - 1) * 18];
        verticesMatrix = new Vector3F[cellCount][cellCount];
        heightMap = new int[cellCount][cellCount];
        refresh();
    }

    private void generateHeightMap() {

        double min = 10000000;
        double max = -10000000;


        for (int i = 0; i < cellCount; i++) {
            for (int k = 0; k < cellCount; k++) {
                double val = verticesMatrix[i][k].y;
                if (val > max) {
                    max = val;
                }
                if (val < min) {
                    min = val;
                }
            }
        }

        for (int i = 0; i < cellCount; i++) {
            for (int k = 0; k < cellCount; k++) {
                double val = verticesMatrix[i][k].y;
                val -= min;
                val /= (max - min);
                val *= 255;
                heightMap[i][k] = (int) val;
            }
        }
    }

    private void genBuildingIndices(int count, int maxDepth) {
        buildingLocations = new Vector3F[count];
        int x, y, curDepth = 0, flag = 0;
        while (flag < count) {
            x = (int) (rnd.nextDouble() * (cellCount - 1));
            y = (int) (rnd.nextDouble() * (cellCount - 1));

            if (verticesMatrix[x][y].y > 1) {
                if (calcNormal(verticesMatrix[x][y], verticesMatrix[x + 1][y], verticesMatrix[x][y + 1]).y < 0.5f) {
                    flatZone(x, y, 2);
                    buildingLocations[flag] = new Vector3F(verticesMatrix[x][y].x, verticesMatrix[x][y].y, verticesMatrix[x][y].z);
                    flag++;
                } else {
                }
            }
            curDepth++;
            if (curDepth >= maxDepth) {
                buildingLocations[flag] = new Vector3F(0, 0, 0);
                flag++;
            }
        }
    }

    public Vector3F[] getBuldingLocations() {
        return buildingLocations;
    }

    private void matToVector() {
        int x = 0, y = 0;
        double a, b, c;
        int counterV = 0;
        for (int i = 0; i < cellCount - 1; i++) {
            for (int k = 0; k < cellCount - 1; k++) {

                a = verticesMatrix[i][k].x;
                b = verticesMatrix[i][k].y;
                c = verticesMatrix[i][k].z;

                verticesToBuffer[counterV] = a;
                counterV++;
                verticesToBuffer[counterV] = b;
                counterV++;
                verticesToBuffer[counterV] = c;
                counterV++;

                a = verticesMatrix[i][k + 1].x;
                b = verticesMatrix[i][k + 1].y;
                c = verticesMatrix[i][k + 1].z;
                ;
                verticesToBuffer[counterV] = a;
                counterV++;
                verticesToBuffer[counterV] = b;
                counterV++;
                verticesToBuffer[counterV] = c;
                counterV++;

                a = verticesMatrix[i + 1][k].x;
                b = verticesMatrix[i + 1][k].y;
                c = verticesMatrix[i + 1][k].z;

                verticesToBuffer[counterV] = a;
                counterV++;
                verticesToBuffer[counterV] = b;
                counterV++;
                verticesToBuffer[counterV] = c;
                counterV++;

                a = verticesMatrix[i][k + 1].x;
                b = verticesMatrix[i][k + 1].y;
                c = verticesMatrix[i][k + 1].z;
                verticesToBuffer[counterV] = a;
                counterV++;
                verticesToBuffer[counterV] = b;
                counterV++;
                verticesToBuffer[counterV] = c;
                counterV++;

                a = verticesMatrix[i + 1][k + 1].x;
                b = verticesMatrix[i + 1][k + 1].y;
                c = verticesMatrix[i + 1][k + 1].z;

                verticesToBuffer[counterV] = a;
                counterV++;
                verticesToBuffer[counterV] = b;
                counterV++;
                verticesToBuffer[counterV] = c;
                counterV++;

                a = verticesMatrix[i + 1][k].x;
                b = verticesMatrix[i + 1][k].y;
                c = verticesMatrix[i + 1][k].z;

                verticesToBuffer[counterV] = a;
                counterV++;
                verticesToBuffer[counterV] = b;
                counterV++;
                verticesToBuffer[counterV] = c;
                counterV++;
            }

        }
    }

    private static float[] texCordFromHeight(float h) {
        float[] res = new float[2];
        if (h < 0.2) {
            res[0] = 0;
            res[1] = 0;
            return res;
        }
        if (h > 1) {
            res[0] = 0.66f;
            res[1] = 0.66f;
            return res;
        }
        res[0] = 0.33f;
        res[1] = 0.88f;
        return res;
    }

    public double[] getVerticesVector() {
        return verticesToBuffer;
    }

    public void refresh() {
        inum = getIPrime((int) (rnd.nextInt(20)) % 20);
        fillZerosVerticesMatrix();
        genNoise();
        // genBuildingIndices(10, 1000);
        matToVector();
        generateHeightMap();
    }

    private int getIPrime(int i) {
        int[] primes = {3, 5, 7, 11, 13, 17, 31, 37, 41, 43, 47, 53, 59, 71, 73, 79, 97, 113, 157, 179};
        return primes[i];
    }

    private float noise2D(int x, int y) {
        int n = (int) (x + y * inum);
        n = (n << 13) ^ n;
        return (1.0f - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff)
                / 1073741824.0f);
    }

    private float smoothedNoise2D(int x, int y) {
        float corners = (noise2D(x - 1, y - 1) + noise2D(x + 1, y - 1)
                + noise2D(x - 1, y + 1) + noise2D(x + 1, y + 1)) / 16;
        float sides = (noise2D(x - 1, y) + noise2D(x + 1, y)
                + noise2D(x, y - 1) + noise2D(x, y + 1)) / 8;
        float center = noise2D(x, y) / 4;
        return corners + sides + center;
    }

    private double compileNoise(double x, double y) {
        int int_X = (int) (x);//целая часть х
        double fractional_X = x - int_X;//дробь от х
//аналогично у
        int int_Y = (int) (y);
        double fractional_Y = y - int_Y;
        //получаем 4 сглаженных значения
        float v1 = smoothedNoise2D(int_X, int_Y);
        float v2 = smoothedNoise2D(int_X + 1, int_Y);
        float v3 = smoothedNoise2D(int_X, int_Y + 1);
        float v4 = smoothedNoise2D(int_X + 1, int_Y + 1);
        //интерполируем значения 1 и 2 пары и производим интерполяцию между ними
        double i1 = lerp(v1, v2, cosinusCurve(fractional_X));
        double i2 = lerp(v3, v4, cosinusCurve(fractional_X));
        //я использовал косинусною интерполяцию ИМХО лучше
        //по параметрам быстрота-//качество
        return lerp(i1, i2, cosinusCurve(fractional_Y));
    }

    private double perlinNoise2D(double x, double y, double factor) {
        double total = 0;
        // это число может иметь и другие значения хоть cosf(sqrtf(2))*3.14f
        // главное чтобы было красиво и результат вас устраивал
        double persistence = defaultPersis;

        // экспериментируйте с этими значениями, попробуйте ставить
        // например sqrtf(3.14f)*0.25f или что-то потяжелее для понимания J)
        double frequency = defaultFreq;
        double amplitude = defaultAmplitude;//амплитуда, в прямой зависимости от значения настойчивости

        // вводим фактор случайности, чтобы облака не были всегда одинаковыми
        // (Мы ведь помним что ф-ция шума когерентна?)
        double tmpX = x + factor;
        double tmpY = y + factor;

        // NUM_OCTAVES - переменная, которая обозначает число октав,
        // чем больше октав, тем лучше получается шум
        for (int i = 0; i < NUM_OCTAVES; i++) {
            total += compileNoise(tmpX * frequency, tmpY * frequency) * amplitude;
            amplitude *= persistence;
            frequency *= Math.E;
        }

        //total += 1;
        //total /= 2;

        return total;
    }

    private void genNoise() {
        // случайное число, которое призвано внести
        // случайность в нашу текстуру
        double fac = (Math.PI * 2 * 10 * (1 + rnd.nextDouble()));

        for (int i = 0; i < cellCount; i++) {
            for (int j = 0; j < cellCount; j++) {
                //проходим по всем элементам массива и заполняем их значениями
                double noise = perlinNoise2D(verticesMatrix[i][j].x, verticesMatrix[i][j].z, fac);
                verticesMatrix[i][j].y = height * noise / scaler;
            }
        }
    }

    private void genRoadBetweenPoints(int x1, int y1, int x2, int y2) {
        int xmin, xmax;
        int ymin, ymax;
        double dh;
        if (x1 > x2) {
            xmin = x2;
            xmax = x1;
            ymin = y2;
            ymax = y1;
        } else {
            xmin = x1;
            xmax = x2;
            ymin = y1;
            ymax = y2;
        }
        double kx = xmax - xmin;
        double ky = ymax - ymin;
        double k = kx / ky;
        for (int i = 0; i < xmax - xmin; i++) {

            if (y1 + k * i < cellCount && y1 + k * i >= 0) {
                dh = verticesMatrix[(xmin + i)][(int) (y1 + k * i)].y + 10;
                verticesMatrix[(xmin + i)][(int) (y1 + k * i)].y = dh;

                if (y1 + k * i + 1 < cellCount && y1 + k * i + 1 >= 0) {
                    verticesMatrix[(xmin + i)][(int) (y1 + k * i) + 1].y = dh;
                }
            }
        }
    }

    private void flatZone(int x, int y, int rad) {
        double height = verticesMatrix[x][y].y;
        double dh;
        int istart = -rad;
        int ifinish = rad;
        int kstart = -rad;
        int kfinish = rad;

        if (x - rad < 0) {
            istart = -x;
        }
        if (y - rad < 0) {
            kstart = -y;
        }
        if (x + rad >= cellCount) {
            ifinish = cellCount - 1 - x;
        }
        if (y + rad >= cellCount) {
            kfinish = cellCount - 1 - y;
        }

        for (int i = istart; i <= ifinish; i++) {
            for (int k = kstart; k <= kfinish; k++) {
                dh = (verticesMatrix[i + x][k + y].y - height) / rad;
                verticesMatrix[i + x][k + y].y = height + dh;
            }
        }
    }

    private double cosinusCurve(double t) {
        return (1 - Math.cos(t * Math.PI)) / 2;
    }

    static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private void fillZerosVerticesMatrix() {
        for (int i = 0; i < cellCount; i++) {
            for (int k = 0; k < cellCount; k++) {
                verticesMatrix[i][k] = new Vector3F((double) (i - cellCount / 2) / scaler, 0, (double) (k - cellCount / 2) / scaler);
            }
        }
    }

    public Vector3F[][] getNoiseMat() {
        return verticesMatrix;
    }

    public int[][] getHeightMap() {
        return heightMap;
    }


    public static Vector3F calcNormal(Vector3F s1, Vector3F s2, Vector3F s3) {
        Vector3F a = new Vector3F(s2.x - s1.x, s2.y - s1.y, s2.z - s1.z);
        Vector3F b = new Vector3F(s3.x - s2.x, s3.y - s2.y, s3.z - s2.z);
        Vector3F normal = new Vector3F(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
        float length = (float) Math.pow(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z, 0.5f);
        if (length == 0) {
            Vector3F result = new Vector3F(0f, 0f, 0f);
            return result;
        }
        Vector3F result = new Vector3F(normal.x / length, normal.y / length, normal.z / length);
        return result;
    }

    public int getSize() {
        return cellCount;
    }
}
