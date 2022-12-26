package behavior;

import enums.EBehaviorState;

abstract public class StateCondition {

    protected Behavior parent;

    public void setParent(Behavior behavior) {
        this.parent = behavior;
    }

    public abstract boolean isTrue();

    public abstract EBehaviorState getState();
}
