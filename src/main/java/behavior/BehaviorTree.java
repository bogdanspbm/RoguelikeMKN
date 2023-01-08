package behavior;

import enemies.controller.BotController;
import enums.EBehaviorState;

import java.util.HashMap;
import java.util.Map;

public class BehaviorTree {

    private BotController controller;

    private Map<EBehaviorState, Behavior> behaviorMap = new HashMap<>();
    private Behavior curState;

    public BehaviorTree(BotController controller) {
        this.controller = controller;
    }

    public void addBehavior(EBehaviorState state, Behavior behavior) {
        behaviorMap.put(state, behavior);
    }

    public void setCurState(EBehaviorState state) {
        curState = behaviorMap.get(state);
    }

    public void tick() {
        if (curState == null) {
            return;
        }


        curState.tick();
        EBehaviorState nextState = curState.getNextState();

        if (nextState == EBehaviorState.CURRENT) {
            return;
        }

        if (!behaviorMap.containsKey(nextState)) {
            return;
        }

        curState = behaviorMap.get(nextState);
        curState.start();
    }
}
