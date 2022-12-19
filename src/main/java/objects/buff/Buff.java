package objects.buff;

import params.ParamsComponent;

public abstract class Buff {
    protected String name;
    protected int duration;
    protected ParamsComponent parent;

    public void onAdd() {

    }

    public void onRemove() {

    }

    public void tick() {
        duration -= 1;

        if (duration <= 0) {
            parent.removeBuff(this);
        }
    }
}
