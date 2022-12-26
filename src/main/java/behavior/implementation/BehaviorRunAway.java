package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import structures.Vector3D;

import static world.singleton.Processor.getWorld;

public class BehaviorRunAway extends Behavior {

    public BehaviorRunAway(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {
        Vector3D direction = getDirectionToTarget(getWorld().getPlayerPawn(0).getLocation());
        controller.getOwner().moveForward(-direction.y());
        controller.getOwner().moveRight(-direction.x());
    }

    @Override
    public void start() {

    }

}
