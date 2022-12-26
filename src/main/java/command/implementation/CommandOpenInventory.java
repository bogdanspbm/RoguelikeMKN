package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandOpenInventory extends Command {

    protected PlayerController controller;

    public CommandOpenInventory(PlayerController controller) {
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.getInventoryPanel().setVisible(!controller.getInventoryPanel().isVisible());
        return true;
    }
}
