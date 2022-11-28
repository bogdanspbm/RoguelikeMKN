package inventory.objects;

import interfaces.Interactive;
import inventory.Inventory;
import objects.Object;
import objects.collision.CylinderCollision;
import objects.pawn.Pawn;

import java.awt.*;
import java.util.HashMap;

public class Item extends Object implements Interactive {
    private int id;
    private int quantity;
    private HashMap<Pawn, Boolean> overlappedMap = new HashMap<>();

    public Item(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;

        createCollision();
    }

    private void createCollision() {
        collisionAdapter.setCollision(new CylinderCollision(8, 100));
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
        inventory.addItem(this);
    }

    @Override
    public void startOverlap(Pawn instigator) {
        if (!hasOverlapped(instigator)) {
            overlappedMap.put(instigator, true);
        }
    }

    @Override
    public void stopOverlap(Pawn instigator) {
        if (hasOverlapped(instigator)) {
            overlappedMap.put(instigator, false);
        }
    }

    @Override
    public void draw(Graphics grphcs) {
        int x = location.x() - source.getImage().getWidth() / 2;
        int y = location.y() - source.getImage().getHeight() / 2;
        if (source != null) {
            grphcs.drawImage(source.getImage(), x, y, null);
        }

        if (isOverlapped()) {
            grphcs.setColor(new Color(200, 200, 200));
            grphcs.fillRect(x, y, 10, 10);

            grphcs.setColor(new Color(20, 20, 20));
            grphcs.drawString("F", x, y + 10);
        }

    }

    private boolean isOverlapped() {
        return overlappedMap.containsValue(true);
    }

    private boolean hasOverlapped(Pawn instigator) {
        if (overlappedMap.containsKey(instigator)) {
            return overlappedMap.get(instigator);
        }

        return false;
    }

}
