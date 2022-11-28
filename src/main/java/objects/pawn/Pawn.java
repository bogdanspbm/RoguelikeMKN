package objects.pawn;

import engine.render.interfaces.Drawable;
import enums.EPawnStatus;
import interfaces.*;
import inventory.interfaces.Inventory;
import objects.Object;
import objects.animations.component.AnimationComponent;
import objects.collision.Collision;
import objects.collision.CollisionAdapter;
import objects.controller.Controller;
import objects.controller.ControllerAdapter;
import objects.projectile.Projectile;
import objects.projectile.factory.ProjectileFactory;
import structures.Vector3D;

import java.awt.*;
import java.util.HashMap;

import static world.singleton.World.getWorld;

public abstract class Pawn extends Object implements Physical, Damageable, Tickable, Inventory, Controllable {


    private Controller controller;

    protected int health = 100;

    private Vector3D prevLocation = new Vector3D(0, 0, 0);

    protected String name = "Pawn";

    protected EPawnStatus status = EPawnStatus.WALK;

    protected boolean inJump = false;

    protected ControllerAdapter controllerAdapter = new ControllerAdapter(this);

    protected inventory.Inventory inventory = new inventory.Inventory();

    protected ProjectileFactory projectileFactory;

    @Override
    public void tryFall() {
        if (!inJump) {
            if (getLocation() != null && getLocation().z() >= 0 && !getWorld().checkCollides(getCollision(), new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() - fallSpeed))) {
                getLocation().addZ(-fallSpeed);
            }
        } else {
            if (getLocation() != null && !getWorld().checkCollides(getCollision(), new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() + jumpSpeed))) {
                getLocation().addZ(jumpSpeed);
            }
        }
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }


    @Override
    public Vector3D getVelocity() {
        return new Vector3D(location.x() - prevLocation.x(), location.y() - prevLocation.y(), location.z() - prevLocation.z());
    }

    @Override
    public void tick() {
    }

    public void setPrevLocation() {
        prevLocation = new Vector3D(location.x(), location.y(), location.z());
    }

    @Override
    public inventory.Inventory getInventory() {
        return inventory;
    }

    @Override
    public void moveRight(int x) {
        if (!getWorld().checkCollides(getCollision(), new Vector3D(location.x() + x, location.y(), location.z()))) {
            location.addX(x);
        }
    }

    @Override
    public void moveForward(int x) {
        if (!getWorld().checkCollides(getCollision(), new Vector3D(location.x(), location.y() - x, location.z()))) {
            location.addY(-x);
        }
    }

    @Override
    public boolean isInAir() {
        return getLocation().z() >= 0 && !getWorld().checkCollides(collision, new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() - fallSpeed));
    }

    public EPawnStatus getStatus() {
        return status;
    }

    public void setStatus(EPawnStatus status) {
        this.status = status;
    }

    @Override
    public void applyDamage(int value, Projectile instigator) {

    }
}
