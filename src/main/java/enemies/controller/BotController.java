package enemies.controller;

import objects.controller.Controller;
import objects.pawn.Pawn;

import static world.singleton.World.getWorld;

public class BotController extends Controller {

    public int i;
    public BotController(int i) {
        this.i = i;
    }

    @Override
    public void tick() {
        super.tick();
        System.out.println(i);
        Pawn target = getWorld().getPlayerPawn(0);
        int xDirection = target.getLocation().x() - owner.getLocation().x();
        int yDirection = target.getLocation().y() - owner.getLocation().y();
        //        трусливый бот
        if (i == 0) {
            if (xDirection < -100 || xDirection > 100 || yDirection > 100 || yDirection < -100) {

                if (xDirection != 0) {
                    xDirection = xDirection / Math.abs(xDirection);
                }
                if (yDirection != 0) {
                    yDirection = -yDirection / Math.abs(yDirection);
                }
                owner.moveRight(xDirection);
                owner.moveForward(yDirection);
            }
            if (xDirection < -10) {
                owner.moveRight(-1);
            }
            if (xDirection > 10) {
                owner.moveRight(-1);
            }
            if (yDirection < -10) {
                owner.moveForward(-1);
            }
            if (yDirection > 10) {
                owner.moveForward(1);
            }
            if (Math.abs(xDirection) < 10 || Math.abs(yDirection) < 10) {
//            owner.setPrevLocation();
                owner.moveRight(0);
                owner.moveForward(0);
            }
        }
//        агресивный бот
        if (i == 1){
            if (xDirection != 0) {
                xDirection = xDirection / Math.abs(xDirection);
            }
            if (yDirection != 0) {
                yDirection = -yDirection / Math.abs(yDirection);
            }
            owner.moveRight(xDirection);
            owner.moveForward(yDirection);
        }
//        статичный бот
        if (i == 2){
            owner.setPrevLocation();
        }
    }

}
