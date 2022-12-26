package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandMoveRight extends Command {

    protected PlayerController controller;
    protected boolean value;

    public CommandMoveRight(PlayerController controller, boolean value) {
        this.value = value;
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.setMovingRight(value);
        return true;
    }
}
