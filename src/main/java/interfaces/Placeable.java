package interfaces;

import structures.Vector3D;

public interface Placeable extends Comparable<Placeable> {

    Vector3D getLocation();

    Vector3D getRotation();

    void setLocation(Vector3D location);

    void setRotation(Vector3D rotation);

    @Override
    default int compareTo(Placeable o) {
        /*
        if (getLocation().y() != o.getLocation().y()) {
            return getLocation().y() - o.getLocation().y();
        } else if (getLocation().x() != o.getLocation().x()) {
            return getLocation().x() - o.getLocation().x();
        } else {
            return getLocation().z() - o.getLocation().z();
        }
    */
        if (getLocation().z() + getLocation().y() == o.getLocation().y() + o.getLocation().z()) {
            return getLocation().x() - o.getLocation().x();
        } else {
            return (getLocation().z() + getLocation().y() - o.getLocation().y() - o.getLocation().z());
        }
    }
}
