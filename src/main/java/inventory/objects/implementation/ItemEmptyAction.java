package inventory.objects.implementation;

import inventory.objects.ItemUseAction;
import objects.pawn.Pawn;

public class ItemEmptyAction extends ItemUseAction {
    public ItemEmptyAction(int value) {
        this.name = "empty";
        this.value = 0;
    }

    @Override
    public boolean useItem(Pawn instigator) {
        return false;
    }
}
