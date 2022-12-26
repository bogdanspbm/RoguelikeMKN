package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import structures.Vector3D;
import utils.WorldUtils;

public class BehaviorGetTargetPoint extends Behavior {

    public BehaviorGetTargetPoint(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {
        controller.setTargetPoint(WorldUtils.getRandomLocationNearPoint(getController().getOwner(), getController().getOwner().getLocation(), 100));
    }

    @Override
    public void start() {

    }

}
