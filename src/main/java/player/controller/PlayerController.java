package player.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import config.Config;
import exceptions.CastException;
import interfaces.Controllable;
import objects.controller.Controller;

import objects.pawn.Pawn;

public class PlayerController extends Controller implements NativeKeyListener {

    private Boolean leftPressed = false;
    private Boolean rightPressed = false;
    private Boolean forwardPressed = false;
    private Boolean backPressed = false;

    private Controllable controllableOwner;

    public PlayerController() {
        // TODO: Если игра переносится в мультиплеер, то прослушку нужно делать где-то снаружи
        GlobalScreen.addNativeKeyListener(this);
        startAxisEvents();
    }

    @Override
    public void setOwner(Pawn owner) {
        super.setOwner(owner);

        try {
            tryToSetControllableOwner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryToSetControllableOwner() throws CastException {
        try {
            controllableOwner = (Controllable) owner;
        } catch (Exception e) {
            throw new CastException("Can't cast Pawn to Controllable: \n" + e.toString());
        }
    }

    private void startAxisEvents() {
        Thread tick = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                try {
                    while (true) {
                        if (controllableOwner != null) {
                            if (leftPressed && !rightPressed) {
                                controllableOwner.moveRight(-1);
                            }
                            if (rightPressed && !leftPressed) {
                                controllableOwner.moveRight(1);
                            }
                            if (backPressed && !forwardPressed) {
                                controllableOwner.moveForward(-1);
                            }
                            if (forwardPressed && !backPressed) {
                                controllableOwner.moveForward(1);
                            }
                        }
                        Thread.sleep((int) (1000 / Config.FRAME_RATE));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tick.start();    //Запуск потока
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        // TODO: В будущем нужно подключить загрузку из настроек
        switch (nativeEvent.getKeyCode()) {
            case 30: {
                // A
                leftPressed = true;
                break;
            }
            case 32: {
                // D
                rightPressed = true;
                break;
            }
            case 31: {
                // S
                backPressed = true;
                break;
            }
            case 17: {
                // W
                forwardPressed = true;
                break;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        // TODO: В будущем нужно подключить загрузку из настроек
        switch (nativeEvent.getKeyCode()) {
            case 30: {
                // A
                leftPressed = false;
                break;
            }
            case 32: {
                // D
                rightPressed = false;
                break;
            }
            case 31: {
                // S
                backPressed = false;
                break;
            }
            case 17: {
                // W
                forwardPressed = false;
                break;
            }
        }
    }
}
