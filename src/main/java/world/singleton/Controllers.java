package world.singleton;

import interfaces.Observable;
import interfaces.Observer;
import player.controller.PlayerController;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

public class Controllers implements Observable {
    private static volatile Controllers singleton;
    private List<Observer> observers = new ArrayList<>();

    private List<PlayerController> playerControllerList = new ArrayList<>();

    private Controllers() {

    }

    public static Controllers getControllers() {
        if (singleton == null) {
            synchronized (Controllers.class) {
                singleton = new Controllers();
            }
        }
        return singleton;
    }

    public void addPlayerController(PlayerController controller) {
        playerControllerList.add(controller);
        notifyObservers();
    }

    public static PlayerController getController(int index) {
        int i = Math.min(index, getControllers().playerControllerList.size() - 1);
        if (i >= 0) {
            return getControllers().playerControllerList.get(i);
        }

        return null;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("Controllers list changed");
        }
    }
}
