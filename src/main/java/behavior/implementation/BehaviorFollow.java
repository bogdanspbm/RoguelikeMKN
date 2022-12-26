package behavior.implementation;

import behavior.Behavior;
import enemies.controller.BotController;
import enums.EBehaviorState;
import objects.pawn.Pawn;
import structures.Vector3D;

import static world.singleton.Processor.getWorld;

public class BehaviorFollow extends Behavior {

    public BehaviorFollow(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {
        Vector3D direction = getDirectionToPawn();
        controller.getOwner().moveForward(direction.y());
        controller.getOwner().moveRight(direction.x());
    }

    @Override
    public void start() {

    }

}
