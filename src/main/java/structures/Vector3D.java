package structures;

public record Vector3D(int x, int y, int z) {
    public double distanceTo(Vector3D target) {
        return Math.sqrt((x - target.x) * (x - target.x) + (y - target.y) * (y - target.y) + (z - target.z) * (z - target.z));
    }
}
