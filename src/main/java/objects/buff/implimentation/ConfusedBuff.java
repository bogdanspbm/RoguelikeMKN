package objects.buff.implimentation;

import objects.buff.Buff;
import params.ParamsComponent;

public class ConfusedBuff extends Buff {

    public ConfusedBuff(ParamsComponent parent) {
        this.parent = parent;
        this.name = "bash";
        this.duration = 300;
    }

    @Override
    public void onAdd() {
        super.onAdd();
        parent.setSpeed(0);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        parent.setSpeed(1);
    }

    @Override
    public Buff clone(ParamsComponent parent) {
        ConfusedBuff buff = new ConfusedBuff(parent);
        buff.duration = duration;
        buff.name = name;
        return buff;
    }
}
