package objects.buff.factory;

import objects.buff.Buff;
import objects.buff.implimentation.ConfusedBuff;
import params.ParamsComponent;

public class BuffFactory {

    protected ParamsComponent parent;

    public BuffFactory(ParamsComponent parent) {
        this.parent = parent;
    }

    public Buff createBuff(BuffBuilder builder) {

        switch (builder.name) {
            case "confuse": {
                return new ConfusedBuff(parent);
            }
        }
        return null;
    }
}
