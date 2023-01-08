package enemies.controller;

import behavior.BehaviorTree;
import behavior.factory.BehaviorTreeFactory;
import behavior.factory.implementation.AggressiveTreeFactory;
import behavior.factory.implementation.CalmTreeFactory;
import behavior.factory.implementation.CowardTreeFactory;
import behavior.factory.implementation.PatrolTreeFactory;
import enums.EBotType;
import objects.controller.Controller;
import objects.pawn.Pawn;
import structures.Vector3D;

import static world.singleton.Processor.getWorld;

public class BotController extends Controller {

    private BehaviorTree behaviorTree;
    protected Vector3D targetPoint;

    public BotController(EBotType type) {
        createBehaviorTree(type);
    }

    public Pawn getOwner() {
        return owner;
    }

    private void createBehaviorTree(EBotType type) {
        BehaviorTreeFactory factory = null;
        switch (type) {
            case CALM -> {
                factory = new CalmTreeFactory(this);
                break;
            }

            case COWARD -> {
                factory = new CowardTreeFactory(this);
                break;
            }

            case AGGRESSOR -> {
                factory = new AggressiveTreeFactory(this);
                break;
            }

            case PATROL -> {
                factory = new PatrolTreeFactory(this);
                break;
            }
        }

        behaviorTree = factory.createBehaviorTree();
    }

    public Vector3D getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(Vector3D targetPoint) {
        this.targetPoint = new Vector3D(targetPoint.x(), targetPoint.y(), getOwner().getLocation().z());
    }

    @Override
    public void tick() {
        owner.setPrevLocation();
        // TODO: не вызывать tick у мертвых
        if (owner.getParamsComponent().checkIsDead()) {
            return;
        }

        behaviorTree.tick();
    }

    @Override
    public Vector3D getControllerVelocity() {
        return getDirectionToTarget(getWorld().getPlayerPawn(0).getLocation());
    }

    private Vector3D getDirectionToTarget(Vector3D targetPoint) {
        int xDirection = targetPoint.x() - getOwner().getLocation().x();
        int yDirection = targetPoint.y() - getOwner().getLocation().y();

        int xDirectionCatchUp = 0;
        int yDirectionCatchUP = 0;

        int speed = getOwner().getParamsComponent().getSpeed();

        if (xDirection != 0) {
            xDirectionCatchUp = xDirection / Math.abs(xDirection) * speed;
        }
        if (yDirection != 0) {
            yDirectionCatchUP = -yDirection / Math.abs(yDirection) * speed;
        }

        return new Vector3D(xDirectionCatchUp, yDirectionCatchUP, 0);
    }
}