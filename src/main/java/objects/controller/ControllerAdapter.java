package objects.controller;

import objects.pawn.Pawn;

public class ControllerAdapter {

    Pawn owner;

    Controller controller;

    public ControllerAdapter(Pawn owner) {
        this.owner = owner;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        controller.setOwner(owner);
    }

    public Controller getController() {
        return controller;
    }
}
