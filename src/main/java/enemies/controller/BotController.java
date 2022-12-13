package enemies.controller;

import enums.EPawnStatus;
import objects.controller.Controller;
import objects.pawn.Pawn;

import static world.singleton.Processor.getWorld;

public class BotController extends Controller {

    public String typeOfBotsBehaviour;
    public int turnOffModeCoward = 0;
    public boolean getCloser = false;

    public BotController(String typeOfBotsBehaviour) {
        this.typeOfBotsBehaviour = typeOfBotsBehaviour;
    }

    @Override
    public void tick() {
        owner.getParamsComponent().getBuffList().forEach(i -> i.preTick());
        super.tick();
        Pawn target = getWorld().getPlayerPawn(0);
        int xDirection = target.getLocation().x() - owner.getLocation().x();
        int yDirection = target.getLocation().y() - owner.getLocation().y();
        int xDirectionCatchUp = 0;
        int yDirectionCatchUP = 0;
        //        трусливый бот
        if (typeOfBotsBehaviour == "coward") {
            if (owner.getParamsComponent().checkIsDead()) {
                owner.setStatus(EPawnStatus.WALK);
                owner.setPrevLocation(); // анимация смерти
            }else {
                if (!getCloser) {
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
                        turnOffModeCoward++;
                    }
                    if (xDirection <= 99 && xDirection >= 60) {
                        owner.moveRight(-1);
                        turnOffModeCoward++;
                    }
                    if (xDirection <= -60 && xDirection >= -99) {
                        owner.moveRight(1);
                        turnOffModeCoward++;
                    }
                    if (yDirection <= 99 && yDirection >= 60) {
                        owner.moveForward(1);
                        turnOffModeCoward++;
                    }
                    if (yDirection >= -99 && yDirection <= -60) {
                        owner.moveForward(1);
                        turnOffModeCoward++;
                    }
                    if (xDirection > -60 && xDirection <= 0) {
                        owner.moveRight(1);
                        turnOffModeCoward++;
                    }
                    if (xDirection > 0 && xDirection < 60) {
                        owner.moveRight(-1);
                        turnOffModeCoward++;
                    }
                    if (yDirection > -60 && yDirection <= 0) {
                        owner.moveForward(1);
                        turnOffModeCoward++;
                    }
                    if (yDirection > 0 && yDirection < 60) {
                        owner.moveRight(-1);
                        turnOffModeCoward++;
                    }
                    if (turnOffModeCoward % 300 == 0) {
                        getCloser = true;
                    }
                }
                if (getCloser) {
                    if (xDirection < -20 || xDirection > 20 || yDirection > 20 || yDirection < -20) {
                        if (xDirection != 0) {
                            xDirectionCatchUp = xDirection / Math.abs(xDirection);
                        }
                        if (yDirection != 0) {
                            yDirectionCatchUP = -yDirection / Math.abs(yDirection);
                        }
                        int speed = owner.getParamsComponent().getSpeed();
                        owner.moveRight(xDirectionCatchUp);
                        owner.moveForward(yDirectionCatchUP);
                        turnOffModeCoward++;
                    }
                    if (Math.abs(xDirection) == 20 || Math.abs(yDirection) == 20) {
                        owner.setPrevLocation();
                        turnOffModeCoward++;
                    }
                    if (Math.abs(xDirection) <= 21 && Math.abs(yDirection) <= 21 &&
                            target.getParamsComponent().getHealthPercentage()!= 0) {
                        owner.setStatus(EPawnStatus.ATTACK);
                        target.getParamsComponent().takeAwayHealth();
                        if (target.getStatus() == EPawnStatus.ATTACK) {
                            owner.getParamsComponent().takeAwayHealth();
                            target.setStatus(EPawnStatus.WALK);
                        }
                    }
                    if (Math.abs(xDirection) <= 21 && Math.abs(yDirection) <= 21 &&
                            target.getParamsComponent().getHealthPercentage() == 0){
                        owner.setStatus(EPawnStatus.WALK);
                    }
                    if (turnOffModeCoward % 300 == 0) {
                        getCloser = false;
                    }
                }
            }
        }
//        агресивный бот
        if (typeOfBotsBehaviour == "aggressor") {
            if (owner.getParamsComponent().checkIsDead()) {
                owner.setStatus(EPawnStatus.WALK);
                owner.setPrevLocation(); // анимация смерти
            }else {
                if (xDirection < -20 || xDirection > 20 || yDirection > 20 || yDirection < -20) {
                    if (xDirection != 0) {
                        xDirectionCatchUp = xDirection / Math.abs(xDirection);
                    }
                    if (yDirection != 0) {
                        yDirectionCatchUP = -yDirection / Math.abs(yDirection);
                    }
                    int speed = owner.getParamsComponent().getSpeed();
                    owner.moveRight(xDirectionCatchUp * speed);
                    owner.moveForward(yDirectionCatchUP * speed);
                }
                if (Math.abs(xDirection) == 20 || Math.abs(yDirection) == 20) {
                    owner.setPrevLocation();
                }
                if (Math.abs(xDirection) <= 21 && Math.abs(yDirection) <= 21 &&
                        target.getParamsComponent().getHealthPercentage()!= 0) {
                    owner.setStatus(EPawnStatus.ATTACK);
                    target.getParamsComponent().takeAwayHealth();
                    if (target.getStatus() == EPawnStatus.ATTACK) {
                        owner.getParamsComponent().takeAwayHealth();
                        target.setStatus(EPawnStatus.WALK);
                    }
                }
                if (Math.abs(xDirection) <= 21 && Math.abs(yDirection) <= 21 &&
                        target.getParamsComponent().getHealthPercentage() == 0){
                    owner.setStatus(EPawnStatus.WALK);
                }
                if (Math.abs(xDirection) > 21 || Math.abs(yDirection) > 21) {
                    owner.setStatus(EPawnStatus.WALK);
                }
            }
            }
//        статичный бот
            if (typeOfBotsBehaviour == "calm") {
                if (owner.getParamsComponent().checkIsDead()) {
                    owner.setStatus(EPawnStatus.WALK);
                    owner.setPrevLocation(); // анимация смерти
                } else {
                    owner.setPrevLocation();
                    if (Math.abs(xDirection) <= 20 && Math.abs(yDirection) <= 20 &&
                            target.getParamsComponent().getHealthPercentage()!= 0) {
                        owner.setStatus(EPawnStatus.ATTACK);
                        target.getParamsComponent().takeAwayHealth();
                        if (target.getStatus() == EPawnStatus.ATTACK) {
                            owner.getParamsComponent().takeAwayHealth();
                            target.setStatus(EPawnStatus.WALK);
                        }
                        owner.setStatus(EPawnStatus.WALK);
                    }
                    if (Math.abs(xDirection) <= 21 && Math.abs(yDirection) <= 21 &&
                            target.getParamsComponent().getHealthPercentage() == 0){
                        owner.setStatus(EPawnStatus.WALK);
                    }
                    if (Math.abs(xDirection) > 20 || Math.abs(yDirection) > 20) {
                        owner.setStatus(EPawnStatus.WALK);
                    }
                }
            }
            owner.getParamsComponent().getBuffList().forEach(i -> i.postTick());
            }
        }