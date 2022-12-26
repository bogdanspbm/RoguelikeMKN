package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionCanSee extends StateCondition {

    private int distance = 500;
    private EBehaviorState behaviorState = EBehaviorState.FOLLOW;

    public ConditionCanSee() {

    }

    public ConditionCanSee(int distance) {
        this.distance = distance;
    }

    public ConditionCanSee(int distance, EBehaviorState behaviorState) {
        this.behaviorState = behaviorState;
        this.distance = distance;
    }

    @Override
    public boolean isTrue() {
        return parent.getDistanceToPawn() < distance;
    }

    @Override
    public EBehaviorState getState() {
        return behaviorState;
    }
}
