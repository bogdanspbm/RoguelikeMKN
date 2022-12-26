package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionCanAttack extends StateCondition {
    @Override
    public boolean isTrue() {
        return parent.getDistanceToPawn() < 10;
    }

    @Override
    public EBehaviorState getState() {
        return EBehaviorState.ATTACK;
    }
}
