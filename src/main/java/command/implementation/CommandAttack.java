package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandAttack extends Command {

    protected PlayerController controller;

    public CommandAttack(PlayerController controller) {
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.getOwner().action();
        return true;
    }
}
