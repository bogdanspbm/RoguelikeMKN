package behavior;

import enemies.controller.BotController;
import enums.EBehaviorState;
import objects.pawn.Pawn;
import structures.Vector3D;

import java.util.ArrayList;
import java.util.List;

import static world.singleton.Processor.getWorld;

public abstract class Behavior {

    private List<StateCondition> conditionList = new ArrayList<>();
    protected BotController controller;

    public Behavior(BotController controller) {
        this.controller = controller;
    }

    public BotController getController() {
        return controller;
    }

    public abstract void tick();

    public abstract void start();

    public EBehaviorState getNextState() {
        for (StateCondition condition : conditionList) {
            if (condition.isTrue()) {
                return condition.getState();
            }
        }

        return EBehaviorState.CURRENT;
    }

    public void addState(StateCondition condition) {
        condition.setParent(this);
        this.conditionList.add(condition);
    }

    public Vector3D getDirectionToPawn() {
        Pawn target = getWorld().getPlayerPawn(0);
        int xDirection = target.getLocation().x() - controller.getOwner().getLocation().x();
        int yDirection = target.getLocation().y() - controller.getOwner().getLocation().y();

        int xDirectionCatchUp = 0;
        int yDirectionCatchUP = 0;

        int speed = controller.getOwner().getParamsComponent().getSpeed();

        if (xDirection != 0) {
            xDirectionCatchUp = xDirection / Math.abs(xDirection) * speed;
        }
        if (yDirection != 0) {
            yDirectionCatchUP = -yDirection / Math.abs(yDirection) * speed;
        }

        return new Vector3D(xDirectionCatchUp, yDirectionCatchUP, 0);
    }

    public double getDistanceToPawn() {
        Pawn target = getWorld().getPlayerPawn(0);
        int xDirection = target.getLocation().x() - controller.getOwner().getLocation().x();
        int yDirection = target.getLocation().y() - controller.getOwner().getLocation().y();

        return Math.sqrt(xDirection * xDirection + yDirection * yDirection);
    }
}
