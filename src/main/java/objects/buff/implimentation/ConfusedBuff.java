package objects.buff.implimentation;

import objects.buff.Buff;
import params.ParamsComponent;

public class ConfusedBuff extends Buff {

    public ConfusedBuff (ParamsComponent parent){
        this.parent = parent;
    }

    @Override
    public void preTick() {
        parent.setSpeed(0);
    }

    @Override
    public void postTick() {
        parent.setSpeed(1);
    }
}
