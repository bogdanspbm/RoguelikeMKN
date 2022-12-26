package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionClose extends StateCondition {

    private int distance = 10;
    private EBehaviorState state = EBehaviorState.ATTACK;

    public ConditionClose() {

    }

    public ConditionClose(int distance) {
        this.distance = distance;
    }

    public ConditionClose(int distance, EBehaviorState state) {
        this.distance = distance;
        this.state = state;
    }


    @Override
    public boolean isTrue() {
        return parent.getDistanceToPawn() < distance;
    }

    @Override
    public EBehaviorState getState() {
        return state;
    }
}
