package inventory.objects;

public class Slot {
    private Item item;
    private int parentIndex;

    private boolean parent;

    public Slot(Item item, int parentIndex, boolean parent){
        this.item = item;
        this.parentIndex = parentIndex;
        this.parent = parent;
    }

    public boolean getIsParent(){
        return parent;
    }

    public void setIsParent(boolean flag){
        this.parent = flag;
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
