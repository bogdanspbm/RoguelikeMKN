package objects.collision;

import enums.ECollisionType;
import structures.Vector3D;

import java.util.List;

public class CylinderCollision extends Collision {

    int radius = 0;
    int height = 0;

    public CylinderCollision(int a, int b) {
        radius = a;
        height = b;

        type = ECollisionType.CYLINDER;
    }


    @Override
    public boolean collide(Collision collision, Vector3D position) {
        switch (collision.getType()) {
            case BOX -> {
                BoxCollision box = (BoxCollision) collision;
                Vector3D delta = new Vector3D(Math.abs(position.x() - collision.getLocation().x()), Math.abs(position.y() - collision.getLocation().y()), Math.abs(position.z() - collision.getLocation().z()));

                if (delta.norm() >= Math.sqrt((radius + box.widthA) * (radius + box.widthA) + (radius + box.widthB) * (radius + box.widthB) + (height + box.height) * (height + box.height))) {
                    return false;
                }

                // TODO: Дальше код работает только если поворот возможен вокруг Z
                delta.rotate(getRotation());

                if (delta.x() > (radius + box.widthA)) {
                    return false;
                }

                if (delta.y() > (radius + box.widthB)) {
                    return false;
                }


                if (delta.z() > (height + box.height)) {
                    return false;
                }

                if (delta.x() <= box.widthA) {
                    return true;
                }

                if (delta.y() < box.widthB) {
                    return true;
                }

                double cornerDistance = Math.sqrt((delta.x() - box.widthA) * (delta.x() - box.widthA) + (delta.y() - box.widthB) * (delta.y() - box.widthB));

                return cornerDistance <= radius;
            }

            case CYLINDER -> {
                CylinderCollision cylinder = (CylinderCollision) collision;
                Vector3D delta = new Vector3D(position.x() - collision.getLocation().x(), position.y() - collision.getLocation().y(), position.z() - collision.getLocation().z());
                Vector3D projection = new Vector3D(delta.x(), delta.y(), 0);

                if (projection.norm() > radius + cylinder.radius) {
                    return false;
                }

                if (delta.z() > height + cylinder.height) {
                    return false;
                }

                return true;
            }
        }
        return false;
    }
}
