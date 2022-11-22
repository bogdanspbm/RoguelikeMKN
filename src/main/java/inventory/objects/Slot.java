package inventory.objects;

public class Slot {
    private Item item;
    private int parentIndex;

    public Slot(Item item, int parentIndex){
        this.item = item;
        this.parentIndex = parentIndex;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }
}
