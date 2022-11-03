package interfaces;

import structures.Vector3D;

public interface Placeable {

    Vector3D getLocation();

    Vector3D getRotation();

    void setLocation(Vector3D location);

    void setRotation(Vector3D rotation);

}
