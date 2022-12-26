package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import enums.EBehaviorState;
import structures.Vector3D;
import utils.WorldUtils;

public class BehaviorGetTargetPoint extends Behavior {

    public BehaviorGetTargetPoint(BotController controller) {
        super(controller);
    }

    private boolean used = false;

    @Override
    public void tick() {
        controller.setTargetPoint(WorldUtils.getRandomLocationNearPoint(getController().getOwner(), getController().getOwner().getLocation(), 500));
        used = true;
    }

    @Override
    public void start() {
        used = false;
    }

    @Override
    public EBehaviorState getNextState() {
        if (used) {
            return super.getNextState();
        }
        return EBehaviorState.CURRENT;
    }
}
