package command.implementation;

import command.Command;
import player.controller.PlayerController;

public class CommandOpenParams extends Command {

    protected PlayerController controller;

    public CommandOpenParams(PlayerController controller) {
        this.controller = controller;
    }


    @Override
    public boolean execute() {
        controller.getParamPanel().setVisible(!controller.getParamPanel().isVisible());
        return true;
    }
}
