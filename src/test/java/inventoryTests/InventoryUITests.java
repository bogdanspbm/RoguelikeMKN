package inventoryTests;

import inventory.Inventory;
import inventoryTests.window.InventoryWindow;
import org.junit.Test;

public class InventoryUITests {

    Inventory inventory = new Inventory();

    @Test
    public void openInventoryTest() {
        InventoryWindow inventoryWindow = new InventoryWindow(inventory);
        inventoryWindow.setVisible(true);
    }
}
