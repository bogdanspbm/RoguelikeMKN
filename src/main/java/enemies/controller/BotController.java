package enemies.controller;

import objects.controller.Controller;
import objects.pawn.Pawn;

import static world.singleton.World.getWorld;

public class BotController extends Controller {

    public BotController() {
    }

    @Override
    public void tick() {
        super.tick();

        Pawn target = getWorld().getPlayerPawn(0);

        int xDirection = target.getLocation().x() - owner.getLocation().x();
        int yDirection = target.getLocation().y() - owner.getLocation().y();

        if (xDirection != 0) {
            xDirection = xDirection / Math.abs(xDirection);
        }
        if (yDirection != 0) {
            yDirection = -yDirection / Math.abs(yDirection);
        }
        owner.moveRight(xDirection);
        owner.moveForward(yDirection);
    }
}
