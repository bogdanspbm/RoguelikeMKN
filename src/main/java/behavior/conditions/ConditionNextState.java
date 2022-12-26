package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionNextState extends StateCondition {

    private EBehaviorState behaviorState = EBehaviorState.IDLE;

    public ConditionNextState() {

    }


    public ConditionNextState(EBehaviorState behaviorState) {
        this.behaviorState = behaviorState;
    }

    @Override
    public boolean isTrue() {
        return true;
    }

    @Override
    public EBehaviorState getState() {
        return behaviorState;
    }
}
