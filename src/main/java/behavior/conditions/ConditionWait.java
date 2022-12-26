package behavior.conditions;

import behavior.StateCondition;
import enums.EBehaviorState;

public class ConditionWait extends StateCondition {

    private int ticks = 500;
    private int curTick = 0;
    private EBehaviorState behaviorState = EBehaviorState.FOLLOW;

    public ConditionWait() {

    }

    public ConditionWait(int ticks) {
        this.ticks = ticks;
    }

    public ConditionWait(int ticks, EBehaviorState behaviorState) {
        this.behaviorState = behaviorState;
        this.ticks = ticks;
    }

    @Override
    public void refresh() {
        super.refresh();
        curTick = 0;
    }

    @Override
    public boolean isTrue() {
        curTick += 1;
        return curTick >= ticks;
    }

    @Override
    public EBehaviorState getState() {
        return behaviorState;
    }
}
