package enemies.controller;

import enums.EPawnStatus;
import objects.Object;
import objects.controller.Controller;
import objects.pawn.Pawn;

import java.util.ArrayDeque;

import static world.singleton.World.getWorld;

public class BotController extends Controller {

    public String typeOfBotsBehaviour;

    public BotController(String typeOfBotsBehaviour) {
        this.typeOfBotsBehaviour = typeOfBotsBehaviour;
    }

    @Override
    public void tick() {
        owner.getParamsComponent().getBuffList().forEach(i->i.preTick());
        super.tick();
        Pawn target = getWorld().getPlayerPawn(0);
        int xDirection = target.getLocation().x() - owner.getLocation().x();
        int yDirection = target.getLocation().y() - owner.getLocation().y();
        int xDirectionCatchUp = 0;
        int yDirectionCatchUP = 0;
//        if (target.getParamsComponent().checkIsDead()){
//            System.out.println("person dead"); // анимация смерти персонажа
//        }
        //        трусливый бот
        if (typeOfBotsBehaviour == "coward") {
            if (xDirection < -100 || xDirection > 100 || yDirection > 100 || yDirection < -100) {
                if (xDirection != 0) {
                    xDirectionCatchUp = xDirection / Math.abs(xDirection);
                }
                if (yDirection != 0) {
                    yDirectionCatchUP = -yDirection / Math.abs(yDirection);
                }
                owner.moveRight(xDirectionCatchUp);
                owner.moveForward(yDirectionCatchUP);
            }
            if (Math.abs(xDirection) == 100 || Math.abs(yDirection) == 100) {
                owner.setPrevLocation();
            }
            if(xDirection <= 99 && xDirection >= 60){
                owner.moveRight(-1);
            }
            if(xDirection <= -60 && xDirection >= -99){
                owner.moveRight(1);
            }
            if(yDirection<= 99 && yDirection >=60){
                owner.moveForward(1);
            }
            if(yDirection>= -99 && yDirection <= -60){
                owner.moveForward(-1);
            }

        }
//        агресивный бот
        if (typeOfBotsBehaviour == "aggressor"){


            if (target.getStatus() == EPawnStatus.ATTACK){
                owner.getParamsComponent().takeAwayHealth();
                System.out.println("bot health"+owner.getParamsComponent().getHealthPercentage());
//                TODO:добавить время после 300 милисекунд

            }
            if (xDirection < -20 || xDirection > 20 || yDirection > 20 || yDirection < -20) {
                if (xDirection != 0) {
                    xDirectionCatchUp = xDirection / Math.abs(xDirection);
                }
                if (yDirection != 0) {
                    yDirectionCatchUP = -yDirection / Math.abs(yDirection);
                }
                int speed = owner.getParamsComponent().getSpeed();
                owner.moveRight(xDirectionCatchUp*speed);
                owner.moveForward(yDirectionCatchUP*speed);
            }
            if (Math.abs(xDirection) == 20 || Math.abs(yDirection) == 20) {
                owner.setPrevLocation();
            }
            if (Math.abs(xDirection) <= 21 && Math.abs(yDirection) <= 21) {
                owner.setStatus(EPawnStatus.ATTACK);
                target.getParamsComponent().takeAwayHealth();
            }
            if (Math.abs(xDirection) > 21 || Math.abs(yDirection) > 21 ){
                owner.setStatus(EPawnStatus.WALK);
            }
            if (owner.getParamsComponent().checkIsDead()){
                owner.setPrevLocation(); // анимация смерти
                System.out.println("bot dead");
            }

        }
//        статичный бот
        if (typeOfBotsBehaviour == "calm"){
            owner.setPrevLocation();
            if(Math.abs(xDirection) <= 20  && Math.abs(yDirection) <= 20){
                owner.setStatus(EPawnStatus.ATTACK);
                target.getParamsComponent().takeAwayHealth();
            }
            if (Math.abs(xDirection) > 20 || Math.abs(yDirection) > 20){
                owner.setStatus(EPawnStatus.WALK);
            }
            if (owner.getParamsComponent().checkIsDead()){
                owner.setPrevLocation(); // анимация смерти
            }
        }
        owner.getParamsComponent().getBuffList().forEach(i->i.postTick());

    }

}
