package objects.controller;

import objects.pawn.Pawn;
import structures.Vector3D;

public abstract class Controller {

    protected Pawn owner;

    public void setOwner(Pawn owner) {
        this.owner = owner;
    }

    public void tick() {

    }

    public Vector3D getControllerVelocity() {
        return owner.getVelocity();
    }
}
