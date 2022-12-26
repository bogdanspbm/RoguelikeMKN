package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import structures.Vector3D;

public class BehaviorAttack extends Behavior {

    public BehaviorAttack(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {
        controller.getOwner().action();
    }

    @Override
    public void start() {

    }

}
