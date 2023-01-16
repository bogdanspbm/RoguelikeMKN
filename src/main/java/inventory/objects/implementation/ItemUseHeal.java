package inventory.objects.implementation;

import inventory.objects.ItemUseAction;
import objects.pawn.Pawn;

public class ItemUseHeal extends ItemUseAction {

    public ItemUseHeal(int value) {
        this.name = "heal";
        this.value = value;
    }


    @Override
    public boolean useItem(Pawn instigator) {
        instigator.getParamsComponent().addHealth(value);
        return true;
    }
}
