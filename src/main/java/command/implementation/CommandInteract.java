package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandInteract extends Command {

    protected PlayerController controller;

    public CommandInteract(PlayerController controller) {
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.getOwner().interact();
        return true;
    }
}
