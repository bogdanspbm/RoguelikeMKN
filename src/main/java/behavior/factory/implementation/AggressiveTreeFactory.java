package behavior.factory.implementation;

import behavior.Behavior;
import behavior.BehaviorTree;
import behavior.conditions.ConditionClose;
import behavior.conditions.ConditionCanSee;
import behavior.conditions.ConditionHealth;
import behavior.conditions.ConditionShouldFollow;
import behavior.factory.BehaviorTreeFactory;
import behavior.implementation.BehaviorAttack;
import behavior.implementation.BehaviorFollow;
import behavior.implementation.BehaviorIdle;
import behavior.implementation.BehaviorRunAway;
import enemies.controller.BotController;
import enums.EBehaviorState;

public class AggressiveTreeFactory extends BehaviorTreeFactory {

    public AggressiveTreeFactory(BotController controller) {
        super(controller);
    }


    @Override
    public BehaviorTree createBehaviorTree() {

        BehaviorTree tree = new BehaviorTree(controller);

        Behavior idle = new BehaviorIdle(controller);
        idle.addState(new ConditionCanSee(300));
        tree.addBehavior(EBehaviorState.IDLE, idle);

        Behavior follow = new BehaviorFollow(controller);
        follow.addState(new ConditionHealth());
        follow.addState(new ConditionClose());
        tree.addBehavior(EBehaviorState.FOLLOW, follow);

        Behavior attack = new BehaviorAttack(controller);
        attack.addState(new ConditionHealth());
        attack.addState(new ConditionShouldFollow());
        tree.addBehavior(EBehaviorState.ATTACK, attack);

        Behavior runAway = new BehaviorRunAway(controller);
        tree.addBehavior(EBehaviorState.SCARY, runAway);

        tree.setCurState(EBehaviorState.IDLE);

        return tree;
    }
}
