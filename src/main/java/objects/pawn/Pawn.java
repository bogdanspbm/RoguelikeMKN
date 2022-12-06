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
import params.ParamsComponent;
import params.interfaces.Params;
import structures.Vector3D;
import world.singleton.Controllers;

import java.awt.*;
import java.util.HashMap;

import static world.singleton.World.getWorld;

public abstract class Pawn extends Object implements Physical, Damageable, Tickable, Inventory, Controllable, Params {


    protected ParamsComponent paramsComponent = new ParamsComponent();

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
        controllerAdapter.setController(controller);
    }

    public Controller getController() {
        return controllerAdapter.getController();
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
        return getLocation().z() >= 0 && !getWorld().checkCollides(collisionAdapter.getCollision(), new Vector3D(getLocation().x(), getLocation().y(), getLocation().z() - fallSpeed));
    }

    public EPawnStatus getStatus() {
        return status;
    }

    public void setStatus(EPawnStatus status) {
        this.status = status;
    }

    @Override
    public void applyDamage(int value, Projectile instigator) {

        if (paramsComponent.checkIsDead()) {
            return;
        }

        if (paramsComponent.checkIsDead()) {
            int experience = paramsComponent.getExperience();
            paramsComponent.addExperience(-experience);
            instigator.getOwner().paramsComponent.addExperience(experience);
        }
    }

    @Override
    public void jump() {

    }

    @Override
    public ParamsComponent getParamsComponent() {
        return paramsComponent;
    }
}
