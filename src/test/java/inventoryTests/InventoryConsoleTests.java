package inventoryTests;


import inventory.Inventory;
import inventory.objects.Item;
import org.junit.Assert;
import org.junit.Test;

public class InventoryConsoleTests {

    Inventory inventory = new Inventory();

    @Test
    public void itemAddTest() {
        inventory.addItem(new Item(1, 5));
        Assert.assertEquals(1, inventory.getItemCount());
    }

    @Test
    public void itemSwapTest() {
        inventory.addItem(new Item(1, 7));

        Assert.assertEquals(5, inventory.getItemByIndex(0).getQuantity());
        Assert.assertEquals(2, inventory.getItemByIndex(1).getQuantity());

        inventory.moveItemToSlot(0, 1);

        Assert.assertEquals(2, inventory.getItemByIndex(0).getQuantity());
        Assert.assertEquals(5, inventory.getItemByIndex(1).getQuantity());
    }

    @Test
    public void itemMoveTest() {
        inventory.addItem(new Item(2, 1));

        Assert.assertEquals(2, inventory.getItemByIndex(0).getId());

        inventory.moveItemToSlot(0, 1);

        Assert.assertEquals(2, inventory.getItemByIndex(1).getId());
    }
}
