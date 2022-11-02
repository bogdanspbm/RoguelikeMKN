package objects.collision;

import enums.ECollisionType;

public class BoxCollision extends Collision {

    int widthA = 0;
    int widthB = 0;
    int height = 0;

    ECollisionType type = ECollisionType.BOX;

    public BoxCollision(int a, int b, int c) {
        widthA = a;
        widthB = b;
        height = c;
    }

    @Override
    boolean collide(Collision collision) {
        switch (collision.getType()) {
            case BOX -> {
                // TODO: Может быть должен появиться Rotation и явная проверка
                BoxCollision box = (BoxCollision) collision;
                if ((widthA + box.widthA) > (box.getLocation().x() - getLocation().x())) {
                    return false;
                }
                if ((widthB + box.widthB) > (box.getLocation().y() - getLocation().y())) {
                    return false;
                }
                if ((height + box.height) > (box.getLocation().z() - getLocation().z())) {
                    return false;
                }

                return true;
            }
        }
        return false;
    }
}
