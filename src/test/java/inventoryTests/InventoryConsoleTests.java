package inventoryTests;


import exceptions.CreationException;
import exceptions.DatabaseException;
import inventory.Inventory;
import inventory.objects.Item;
import objects.pawn.Pawn;
import org.junit.Assert;
import org.junit.Test;
import player.Player;

public class InventoryConsoleTests {

    Inventory inventory = new Inventory(null);

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

    @Test
    public void usePotion() throws CreationException, DatabaseException {
        Pawn pawn = new Player();

        assert pawn.getParamsComponent().getHealthPercentage() == 1;

        pawn.getParamsComponent().addHealth(-10);

        double lastHealth = pawn.getParamsComponent().getHealthPercentage();

        assert lastHealth < 1;

        Inventory inventory = new Inventory(pawn);
        inventory.addItem(new Item(1, 5));
        inventory.useItem(0);

        assert lastHealth < pawn.getParamsComponent().getHealthPercentage();
    }

}
