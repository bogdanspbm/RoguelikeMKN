package behavior.factory.implementation;

import behavior.Behavior;
import behavior.BehaviorTree;
import behavior.conditions.ConditionClosePawn;
import behavior.conditions.ConditionCanSee;
import behavior.conditions.ConditionShouldFollow;
import behavior.factory.BehaviorTreeFactory;
import behavior.implementation.BehaviorAttack;
import behavior.implementation.BehaviorFollow;
import behavior.implementation.BehaviorIdle;
import behavior.implementation.BehaviorRunAway;
import enemies.controller.BotController;
import enums.EBehaviorState;

public class CowardTreeFactory extends BehaviorTreeFactory {

    public CowardTreeFactory(BotController controller) {
        super(controller);
    }


    @Override
    public BehaviorTree createBehaviorTree() {

        BehaviorTree tree = new BehaviorTree(controller);

        Behavior idle = new BehaviorIdle(controller);
        idle.addState(new ConditionCanSee(200, EBehaviorState.SCARY));
        tree.addBehavior(EBehaviorState.IDLE, idle);

        Behavior runAway = new BehaviorRunAway(controller);
        runAway.addState(new ConditionClosePawn());
        tree.addBehavior(EBehaviorState.SCARY, runAway);

        Behavior follow = new BehaviorFollow(controller);
        follow.addState(new ConditionClosePawn());
        tree.addBehavior(EBehaviorState.FOLLOW, follow);

        Behavior attack = new BehaviorAttack(controller);
        attack.addState(new ConditionShouldFollow());
        tree.addBehavior(EBehaviorState.ATTACK, attack);

        tree.setCurState(EBehaviorState.IDLE);

        return tree;
    }
}
