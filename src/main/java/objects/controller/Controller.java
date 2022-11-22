package objects.controller;

import objects.pawn.Pawn;

public abstract class Controller {

    protected Pawn owner;

    public void setOwner(Pawn owner) {
        this.owner = owner;
    }

    public void tick() {

    }
}
