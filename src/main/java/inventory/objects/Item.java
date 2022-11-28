package inventory.objects;

import interfaces.Interactive;
import inventory.Inventory;
import objects.Object;
import objects.pawn.Pawn;

public class Item extends Object implements Interactive {
    private int id;
    private int quantity;

    public Item(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }


    @Override
    public void interact(Pawn instigator) {
        Inventory inventory = instigator.getInventory();
    }
}
