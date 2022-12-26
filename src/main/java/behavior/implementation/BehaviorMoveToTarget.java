package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import structures.Vector3D;

import static world.singleton.Processor.getWorld;

public class BehaviorMoveToTarget extends Behavior {

    public BehaviorMoveToTarget(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {
        if (controller.getTargetPoint() == null) {
            return;
        }

        Vector3D direction = getDirectionToTarget(controller.getTargetPoint());
        controller.getOwner().moveForward(direction.y());
        controller.getOwner().moveRight(direction.x());
    }

    @Override
    public void start() {

    }

}
