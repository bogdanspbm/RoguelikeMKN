package behavior.implementation;

import behavior.Behavior;
import behavior.StateCondition;
import enemies.controller.BotController;
import structures.Vector3D;

public class BehaviorIdle extends Behavior {

    public BehaviorIdle(BotController controller) {
        super(controller);
    }

    @Override
    public void tick() {

    }

    @Override
    public void start() {
        for (StateCondition condition : conditionList) {
            condition.refresh();
        }
    }

}
