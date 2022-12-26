package player.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import command.implementation.*;
import config.Config;

import static world.singleton.Controllers.getControllers;

public class KeyboardListener implements NativeKeyListener {


    private PlayerController controller;

    public KeyboardListener(PlayerController controller) {
        this.controller = controller;
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        // TODO: В будущем нужно подключить загрузку из настроек

        switch (nativeEvent.getKeyCode()) {
            case 23: {
                //owner.getInventory().addItem(new Item(1, 1));
                controller.addCommand(new CommandOpenInventory(controller));
                break;
            }
            case 25: {
                controller.addCommand(new CommandOpenParams(controller));
                break;
            }
            case 33: {
                // F
                controller.addCommand(new CommandInteract(controller));
                break;
            }
            case 34: {
                // G
                controller.addCommand(new CommandAttack(controller));
                break;
            }
            case 30: {
                // A
                controller.addCommand(new CommandMoveLeft(controller, true));
                break;
            }
            case 32: {
                // D
                controller.addCommand(new CommandMoveRight(controller, true));
                break;
            }
            case 31: {
                // S
                controller.addCommand(new CommandMoveBack(controller, true));
                break;
            }
            case 17: {
                // W
                controller.addCommand(new CommandMoveForward(controller, true));
                break;
            }
            case 57: {
                // SPACE
                controller.addCommand(new CommandAttack(controller));
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
                controller.addCommand(new CommandMoveLeft(controller, false));
                break;
            }
            case 32: {
                // D
                controller.addCommand(new CommandMoveRight(controller, false));
                break;
            }
            case 31: {
                // S
                controller.addCommand(new CommandMoveBack(controller, false));
                break;
            }
            case 17: {
                // W
                controller.addCommand(new CommandMoveForward(controller, false));
                break;
            }
        }
    }

}
