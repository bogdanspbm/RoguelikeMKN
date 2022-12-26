package behavior.factory.implementation;

import behavior.Behavior;
import behavior.BehaviorTree;
import behavior.conditions.ConditionCanAttack;
import behavior.conditions.ConditionCanSee;
import behavior.conditions.ConditionDamageReceived;
import behavior.conditions.ConditionShouldFollow;
import behavior.factory.BehaviorTreeFactory;
import behavior.implementation.BehaviorAttack;
import behavior.implementation.BehaviorFollow;
import behavior.implementation.BehaviorIdle;
import enemies.controller.BotController;
import enums.EBehaviorState;

public class CalmTreeFactory extends BehaviorTreeFactory {

    public CalmTreeFactory(BotController controller) {
        super(controller);
    }


    @Override
    public BehaviorTree createBehaviorTree() {

        BehaviorTree tree = new BehaviorTree(controller);

        Behavior idle = new BehaviorIdle(controller);
        idle.addState(new ConditionDamageReceived());
        tree.addBehavior(EBehaviorState.IDLE, idle);

        Behavior runAway = new BehaviorIdle(controller);
        runAway.addState(new ConditionCanAttack());
        tree.addBehavior(EBehaviorState.SCARY, runAway);

        Behavior follow = new BehaviorFollow(controller);
        follow.addState(new ConditionCanAttack());
        tree.addBehavior(EBehaviorState.FOLLOW, follow);

        Behavior attack = new BehaviorAttack(controller);
        attack.addState(new ConditionShouldFollow());
        tree.addBehavior(EBehaviorState.ATTACK, attack);

        tree.setCurState(EBehaviorState.IDLE);

        return tree;
    }
}
