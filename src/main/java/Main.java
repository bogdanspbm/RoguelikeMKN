import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import engine.processer.GameProcesser;

public class Main {
    public static void main(String[] args) {
        initGlobalScreen();
        GameProcesser game = new GameProcesser();
        game.start();
    }

    private static void initGlobalScreen() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
    }
}