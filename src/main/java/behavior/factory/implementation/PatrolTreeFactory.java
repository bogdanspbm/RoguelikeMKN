package behavior.factory.implementation;

import behavior.Behavior;
import behavior.BehaviorTree;
import behavior.conditions.*;
import behavior.factory.BehaviorTreeFactory;
import behavior.implementation.*;
import enemies.controller.BotController;
import enums.EBehaviorState;

public class PatrolTreeFactory extends BehaviorTreeFactory {

    public PatrolTreeFactory(BotController controller) {
        super(controller);
    }


    @Override
    public BehaviorTree createBehaviorTree() {

        BehaviorTree tree = new BehaviorTree(controller);

        Behavior idle = new BehaviorIdle(controller);
        idle.addState(new ConditionCanSee(100, EBehaviorState.FOLLOW));
        idle.addState(new ConditionWait(30, EBehaviorState.LOOK_FOR_TARGET));
        tree.addBehavior(EBehaviorState.IDLE, idle);

        Behavior newPoint = new BehaviorGetTargetPoint(controller);
        newPoint.addState(new ConditionNextState(EBehaviorState.GOTO));
        tree.addBehavior(EBehaviorState.LOOK_FOR_TARGET, newPoint);

        Behavior goToTarget = new BehaviorMoveToTarget(controller);
        goToTarget.addState(new ConditionCanSee(100, EBehaviorState.FOLLOW));
        goToTarget.addState(new ConditionClose(10, EBehaviorState.IDLE));
        tree.addBehavior(EBehaviorState.GOTO, goToTarget);

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
