package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandMoveForward extends Command {

    protected PlayerController controller;
    protected boolean value;

    public CommandMoveForward(PlayerController controller, boolean value) {
        this.value = value;
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.setMovingForward(value);
        return true;
    }
}
