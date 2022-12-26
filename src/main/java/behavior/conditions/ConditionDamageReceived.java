package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionDamageReceived extends StateCondition {

    private double lastHealth = 0;

    @Override
    public boolean isTrue() {
        double healthDelta = parent.getController().getOwner().getParamsComponent().getHealthPercentage() - lastHealth;
        boolean result = healthDelta < 0;
        lastHealth = parent.getController().getOwner().getParamsComponent().getHealthPercentage();
        return result;
    }

    @Override
    public EBehaviorState getState() {
        return EBehaviorState.SCARY;
    }
}
