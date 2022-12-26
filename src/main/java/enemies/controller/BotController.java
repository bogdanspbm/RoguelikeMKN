package enemies.controller;

import behavior.BehaviorTree;
import behavior.factory.BehaviorTreeFactory;
import behavior.factory.implementation.AggressiveTreeFactory;
import behavior.factory.implementation.CalmTreeFactory;
import behavior.factory.implementation.CowardTreeFactory;
import enums.EBotType;
import objects.controller.Controller;
import objects.pawn.Pawn;

public class BotController extends Controller {

    private BehaviorTree behaviorTree;

    public BotController(EBotType type) {
        createBehaviorTree(type);
    }

    public Pawn getOwner() {
        return owner;
    }

    private void createBehaviorTree(EBotType type) {
        BehaviorTreeFactory factory = null;
        switch (type) {
            case CALM -> {
                factory = new CalmTreeFactory(this);
                break;
            }

            case COWARD -> {
                factory = new CowardTreeFactory(this);
                break;
            }

            case AGGRESSOR -> {
                factory = new AggressiveTreeFactory(this);
                break;
            }
        }

        behaviorTree = factory.createBehaviorTree();
    }

    @Override
    public void tick() {
        if (owner.getParamsComponent().checkIsDead()) {
            return;
        }

        behaviorTree.tick();
    }
}