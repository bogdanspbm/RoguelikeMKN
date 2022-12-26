package structures;

public class Vector3D implements Cloneable {
    int x, y, z;

    @Override
    protected Vector3D clone() throws CloneNotSupportedException {
        return new Vector3D(x, y, z);
    }

    public Vector3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distanceTo(Vector3D target) {
        return Math.sqrt((x - target.x) * (x - target.x) + (y - target.y) * (y - target.y) + (z - target.z) * (z - target.z));
    }

    public double norm() {
        return distanceTo(new Vector3D(0, 0, 0));
    }

    public void addX(int x) {
        this.x += x;
    }

    public void addY(int y) {
        this.y += y;
    }

    public void addZ(int z) {
        this.z += z;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public Vector3D rotate(Vector3D rotator) {
        // TODO: Пока реализуем вращение только вдоль оси Z

        x = (int) (x * Math.cos(rotator.z()) + y * Math.sin(rotator.z()));
        y = (int) (y * Math.cos(rotator.z()) - x * Math.sin(rotator.z()));

        return this;
    }

    public static Vector3D sum(Vector3D a, Vector3D b) {
        return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z;
    }

    public Vector3D copy() {
        return new Vector3D(x, y, z);
    }
}
