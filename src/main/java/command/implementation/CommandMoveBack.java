package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandMoveBack extends Command {

    protected PlayerController controller;
    protected boolean value;

    public CommandMoveBack(PlayerController controller, boolean value) {
        this.value = value;
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.setMovingBack(value);
        return true;
    }
}
