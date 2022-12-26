package collisionTests;


import inventory.Inventory;
import inventory.objects.Item;
import objects.collision.BoxCollision;
import org.junit.Assert;
import org.junit.Test;
import structures.Vector3D;

public class CollisionTests {


    @Test
    public void boxCollide() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(0, 0, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(5, 5, 5));

        Assert.assertEquals(boxA.collide(boxB), true);
    }

    @Test
    public void boxDontCollide() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(0, 0, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(50, 50, 50));

        Assert.assertEquals(boxA.collide(boxB), false);
    }


}
