package objects.buff;

import params.ParamsComponent;

public abstract class Buff {
    protected String name;
    protected int duration;
    protected ParamsComponent parent;

    public abstract void preTick();

    public abstract void postTick();
}
