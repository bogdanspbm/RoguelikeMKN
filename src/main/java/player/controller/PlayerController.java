package player.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import objects.controller.Controller;

import objects.pawn.Pawn;

public class PlayerController extends Controller implements NativeKeyListener {


    public PlayerController() {
        // TODO: Если игра переносится в мультиплеер, то прослушку нужно делать где-то снаружи
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        System.out.println(nativeEvent);
    }
}
