package objects.collision;

import enums.ECollisionType;
import structures.Vector3D;

import java.util.ArrayList;
import java.util.List;

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
    public Vector3D getShape() {
        return new Vector3D(widthA,widthB,height);
    }


    @Override
    public boolean collide(Collision collision, Vector3D position) {
        switch (collision.getType()) {
            case BOX -> {
                /* TODO: Может быть должен появиться Rotation и явная проверка
                    Оставим на потом, тк. пока что нет столкновений между двумя квадратными коллизиями
                 */
                BoxCollision box = (BoxCollision) collision;
                Vector3D delta = new Vector3D(position.x() - collision.getLocation().x(), position.y() - collision.getLocation().y(), position.z() - collision.getLocation().z());

                if (delta.norm() >= Math.sqrt((widthA + box.widthA) * (widthA + box.widthA) + (widthB + box.widthB) * (widthB + box.widthB) + (height + box.height) * (height + box.height))) {
                    return false;
                }


                return true;
            }

            case CYLINDER -> {
                CylinderCollision cylinder = (CylinderCollision) collision;
                Vector3D delta = new Vector3D(Math.abs(position.x() - collision.getLocation().x()), Math.abs(position.y() - collision.getLocation().y()), Math.abs(position.z() - collision.getLocation().z()));

                if (delta.norm() >= Math.sqrt((cylinder.radius + widthA) * (cylinder.radius + widthA) + (cylinder.radius + widthB) * (cylinder.radius + widthB) + (height + cylinder.height) * (height + cylinder.height))) {
                    return false;
                }

                // TODO: Дальше код работает только если поворот возможен вокруг Z
                delta.rotate(getRotation());

                if (delta.x() > (cylinder.radius + widthA)) {
                    return false;
                }

                if (delta.y() > (cylinder.radius + widthB)) {
                    return false;
                }


                if (delta.z() > (height + cylinder.height)) {
                    return false;
                }

                if (delta.x() <= widthA) {
                    return true;
                }

                if (delta.y() < widthB) {
                    return true;
                }

                double cornerDistance = Math.sqrt((delta.x() - widthA) * (delta.x() - widthA) + (delta.y() - widthB) * (delta.y() - widthB));

                return cornerDistance <= cylinder.radius;
            }
        }
        return false;
    }
}
