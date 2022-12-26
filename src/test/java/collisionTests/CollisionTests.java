package collisionTests;


import objects.collision.BoxCollision;
import org.junit.Assert;
import org.junit.Test;
import structures.Vector3D;

public class CollisionTests {


    @Test
    public void testSimpleBoxCollide() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(0, 0, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(5, 5, 5));

        Assert.assertEquals(boxA.collide(boxB), true);
    }

    @Test
    public void testSimpleBoxDontCollide() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(0, 0, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(50, 50, 50));

        Assert.assertEquals(boxA.collide(boxB), false);
    }

    @Test
    public void testBoxCollideOnXInvalid() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(100, 0, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(109, 50, 0));

        Assert.assertEquals(boxA.collide(boxB), false);
    }

    @Test
    public void testBoxCollideOnXValid() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(100, 50, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(109, 50, 0));

        Assert.assertEquals(boxA.collide(boxB), true);
    }

    @Test
    public void testBoxCollideOnYValid() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(20, 50, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(50, 45, 0));

        Assert.assertEquals(boxA.collide(boxB), true);
    }

    @Test
    public void testBoxCollideOnYInvalid() {
        BoxCollision boxA = new BoxCollision(10, 10, 10);
        boxA.setLocation(new Vector3D(-10, 50, 0));

        BoxCollision boxB = new BoxCollision(10, 10, 10);
        boxB.setLocation(new Vector3D(50, 45, 0));

        Assert.assertEquals(boxA.collide(boxB), false);
    }

    @Test
    public void testBoxCollideValid() {
        BoxCollision boxA = new BoxCollision(158, 143, -10);
        boxA.setLocation(new Vector3D(67, 5, 16));

        BoxCollision boxB = new BoxCollision(-234, 89, -670);
        boxB.setLocation(new Vector3D(95, -5, -78));

        Assert.assertEquals(boxA.collide(boxB), true);
    }

}
