package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionShouldFollow extends StateCondition {

    private int distance = 150;

    public ConditionShouldFollow() {

    }

    public ConditionShouldFollow(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean isTrue() {
        return parent.getDistanceToPawn() > distance;
    }

    @Override
    public EBehaviorState getState() {
        return EBehaviorState.FOLLOW;
    }
}
