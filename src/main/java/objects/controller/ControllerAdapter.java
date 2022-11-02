package objects.controller;

import objects.pawn.Pawn;

public class ControllerAdapter {

    Pawn owner;

    public ControllerAdapter(Pawn owner) {
        this.owner = owner;
    }

    public void setController(Controller controller) {
        owner.setController(controller);
        controller.setOwner(owner);
    }
}
