package player.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import config.Config;
import exceptions.CastException;
import interfaces.Controllable;
import inventory.Inventory;
import inventory.objects.Item;
import inventory.ui.InventoryPanel;
import objects.controller.Controller;

import objects.pawn.Pawn;
import params.ui.HealthBar;

import static world.singleton.Controllers.getControllers;

public class PlayerController extends Controller implements NativeKeyListener {

    private Boolean leftPressed = false;
    private Boolean rightPressed = false;
    private Boolean forwardPressed = false;
    private Boolean backPressed = false;

    private InventoryPanel inventoryPanel = null;

    private HealthBar healthBar = null;

    public PlayerController() {
        // TODO: Если игра переносится в мультиплеер, то прослушку нужно делать где-то снаружи
        getControllers().addPlayerController(this);
        GlobalScreen.addNativeKeyListener(this);
        startAxisEvents();
    }

    @Override
    public void setOwner(Pawn owner) {
        super.setOwner(owner);
        try {
            inventoryPanel = new InventoryPanel(owner.getInventory());
            inventoryPanel.setVisible(false);

            healthBar = new HealthBar(owner.getParamsComponent());
            healthBar.setVisible(true);

            getControllers().notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAxisEvents() {
        Thread tick = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                try {
                    while (true) {
                        if (owner != null) {
                            owner.setPrevLocation();
                            if (leftPressed && !rightPressed) {
                                owner.moveRight(-1);
                            }
                            if (rightPressed && !leftPressed) {
                                owner.moveRight(1);
                            }
                            if (backPressed && !forwardPressed) {
                                owner.moveForward(-1);
                            }
                            if (forwardPressed && !backPressed) {
                                owner.moveForward(1);
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
            case 23: {
                //owner.getInventory().addItem(new Item(1, 1));
                if (inventoryPanel != null) {
                    inventoryPanel.setVisible(!inventoryPanel.isVisible());
                }
                break;
            }
            case 33: {
                // F
                owner.interact();
                break;
            }
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
            case 57: {
                // SPACE
                owner.action();
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

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }
}
