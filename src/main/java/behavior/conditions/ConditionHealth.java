package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionHealth extends StateCondition {

    private double value = 0.5;
    private EBehaviorState state = EBehaviorState.SCARY;

    public ConditionHealth() {

    }

    public ConditionHealth(double distance) {
        this.value = distance;
    }

    public ConditionHealth(double value, EBehaviorState state) {
        this.value = value;
        this.state = state;
    }


    @Override
    public boolean isTrue() {
        return parent.getController().getOwner().getParamsComponent().getHealthPercentage() < value;
    }

    @Override
    public EBehaviorState getState() {
        return state;
    }
}
