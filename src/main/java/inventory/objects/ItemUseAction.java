package inventory.objects;

import objects.pawn.Pawn;

public abstract class ItemUseAction {
    protected String name = "";
    protected int value = 0;

    abstract public boolean useItem(Pawn instigator);
}
