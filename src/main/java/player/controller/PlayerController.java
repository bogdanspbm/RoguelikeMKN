package player.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import command.Command;
import config.Config;
import exceptions.CastException;
import interfaces.Controllable;
import inventory.Inventory;
import inventory.objects.Item;
import inventory.ui.InventoryPanel;
import objects.controller.Controller;

import objects.pawn.Pawn;
import params.ui.HealthBar;
import params.ui.ParamPanel;
import structures.Vector3D;

import java.util.LinkedList;
import java.util.Queue;

import static world.singleton.Controllers.getControllers;

public class PlayerController extends Controller {


    private Queue<Command> commands = new LinkedList<>();

    private Boolean movingLeft = false;
    private Boolean movingRight = false;
    private Boolean movingForward = false;
    private Boolean movingBack = false;


    private InventoryPanel inventoryPanel = null;

    private HealthBar healthBar = null;

    private ParamPanel paramPanel = null;
    private KeyboardListener listener;

    public PlayerController() {
        this.listener = new KeyboardListener(this);
        startAxisEvents();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void tick() {
        super.tick();
        while (commands.size() > 0) {
            commands.poll().execute();
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
                            if (movingLeft && !movingRight) {
                                owner.moveRight(-1);
                            }
                            if (movingRight && !movingLeft) {
                                owner.moveRight(1);
                            }
                            if (movingBack && !movingForward) {
                                owner.moveForward(-1);
                            }
                            if (movingForward && !movingBack) {
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
    public Vector3D getControllerVelocity() {
        int x = 0;
        int y = 0;
        if (movingLeft) {
            x = -1;
        }
        if (movingRight) {
            x = 1;
        }
        if (movingBack) {
            y = 1;
        }
        if (movingForward && !movingBack) {
            y = -1;
        }

        return new Vector3D(x, y, 0);
    }

    @Override
    public void setOwner(Pawn owner) {
        super.setOwner(owner);
        try {
            inventoryPanel = new InventoryPanel(owner.getInventory());
            inventoryPanel.setVisible(false);

            healthBar = new HealthBar(owner.getParamsComponent());
            healthBar.setVisible(true);

            paramPanel = new ParamPanel(owner.getParamsComponent());
            paramPanel.setVisible(false);

            getControllers().notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pawn getOwner() {
        return owner;
    }


    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public ParamPanel getParamPanel() {
        return paramPanel;
    }

    public Boolean getMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(Boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public Boolean getMovingRight() {
        return movingRight;
    }

    public void setMovingRight(Boolean movingRight) {
        this.movingRight = movingRight;
    }

    public Boolean getMovingForward() {
        return movingForward;
    }

    public void setMovingForward(Boolean movingForward) {
        this.movingForward = movingForward;
    }

    public Boolean getMovingBack() {
        return movingBack;
    }

    public void setMovingBack(Boolean movingBack) {
        this.movingBack = movingBack;
    }
}
