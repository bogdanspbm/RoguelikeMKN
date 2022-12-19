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

        // TODO: добавить default во все case
        switch (builder.name) {
            case "bash": {
                return new ConfusedBuff(parent);
            }

            default: {
                return null;
            }

        }
    }
}
