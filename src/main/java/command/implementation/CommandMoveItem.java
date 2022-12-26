package command.implementation;

import command.Command;
import inventory.Inventory;

public class CommandMoveItem extends Command {

    private Inventory inventory;
    private int fromIndex, toIndex;

    public CommandMoveItem(Inventory inventory, int fromIndex, int toIndex) {
        this.inventory = inventory;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }


    @Override
    public boolean execute() {
        return inventory.moveItemToSlot(fromIndex, toIndex);
    }
}
