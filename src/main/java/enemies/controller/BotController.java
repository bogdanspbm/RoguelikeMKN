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
        System.out.println(yDirection);
//        трусливое поведение
        if (xDirection <-100 || xDirection > 100 || yDirection > 100 || yDirection < -100) {

            if (xDirection != 0) {
                xDirection = xDirection / Math.abs(xDirection);
            }
            if (yDirection != 0) {
                yDirection = -yDirection / Math.abs(yDirection);
            }
            owner.moveRight(xDirection);
            owner.moveForward(yDirection);
        }
        if (xDirection<-10) {
            owner.moveRight(1);

        }
        if (xDirection > 10) {
            owner.moveRight(-1);
        }
        if (yDirection < -10){
            owner.moveForward(-1);
        }
        if (yDirection > 10){
            owner.moveForward(1);
        }
        if(Math.abs(xDirection)< 10 || Math.abs(yDirection)<10){
            owner.moveRight(0);
            owner.moveForward(0);
        }
    }
}
