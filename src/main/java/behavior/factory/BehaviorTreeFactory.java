package behavior.factory;

import behavior.BehaviorTree;
import enemies.controller.BotController;

public abstract class BehaviorTreeFactory {

    protected BotController controller;

    public BehaviorTreeFactory(BotController controller) {
        this.controller = controller;
    }

    public abstract BehaviorTree createBehaviorTree();
}
