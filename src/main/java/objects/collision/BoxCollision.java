package objects.collision;

import enums.ECollisionType;
import structures.Vector3D;

public class BoxCollision extends Collision {

    int widthA = 0;
    int widthB = 0;
    int height = 0;

    public BoxCollision(int a, int b, int c) {
        widthA = a;
        widthB = b;
        height = c;

        type = ECollisionType.BOX;
    }


    @Override
    public boolean collide(Collision collision, Vector3D position) {
        switch (collision.getType()) {
            case BOX -> {
                // TODO: Может быть должен появиться Rotation и явная проверка
                BoxCollision box = (BoxCollision) collision;

                Vector3D delta = new Vector3D(position.x() - collision.getLocation().x(), position.y() - collision.getLocation().y(), position.z() - collision.getLocation().z());
                delta.rotate(getRotation());
                if ((widthA + box.widthA) < (Math.abs(delta.x()))) {
                    return false;
                }
                if ((widthB + box.widthB) < (Math.abs(delta.y()))) {
                    return false;
                }
                if ((height + box.height) < (Math.abs(delta.z()))) {
                    return false;
                }
                
                return true;
            }
        }
        return false;
    }
}
