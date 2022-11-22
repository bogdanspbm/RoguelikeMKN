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
}
