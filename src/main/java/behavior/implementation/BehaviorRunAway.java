package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import structures.Vector3D;

public class BehaviorRunAway extends Behavior {

    public BehaviorRunAway(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {
        Vector3D direction = getDirectionToPawn();
        controller.getOwner().moveForward(-direction.y());
        controller.getOwner().moveRight(-direction.x());
    }

    @Override
    public void start() {

    }

}
