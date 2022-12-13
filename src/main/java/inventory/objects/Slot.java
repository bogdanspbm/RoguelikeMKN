package inventory.objects;

public class Slot {
    private Item item;
    private int parentIndex;

    private boolean isParent;

    public Slot(Item item, int parentIndex, boolean parent){
        this.item = item;
        this.parentIndex = parentIndex;
        this.isParent = parent;
    }

    public boolean getIsParent(){
        return isParent;
    }

    public void setIsParent(boolean flag){
        this.isParent = flag;
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
