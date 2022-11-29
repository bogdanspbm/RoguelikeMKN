package engine.render.window;

import config.Config;
import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.panel.RenderPanel;
import interfaces.Observer;
import inventory.ui.InventoryPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static world.singleton.Controllers.getController;
import static world.singleton.Controllers.getControllers;

public class Window extends JFrame implements Observer {

    RenderPanel renderPanel;
    InventoryPanel inventoryPanel;

    JPanel frame;

    public Window() {
        initComponents();
        getControllers().registerObserver(this);
        drawInventoriesFromController();
    }

    private void startRender() {
        Thread render = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                while (true) {
                    frame.updateUI();
                    try {
                        Thread.sleep((int) (1000 / Config.FRAME_RATE));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        render.start();    //Запуск потока
    }

    private void initComponents() {
        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 300));
        setPreferredSize(new java.awt.Dimension(500, 300));

        frame = new JPanel();
        frame.setLayout(new GridBagLayout());

        java.awt.GridBagConstraints gridBagConstraints;

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        add(frame, gridBagConstraints);
    }

    public void createRenderPanel(DrawableProvider provider) {
        java.awt.GridBagConstraints gridBagConstraints;

        if (renderPanel != null) {
            frame.remove(renderPanel);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        renderPanel = new RenderPanel(provider);
        frame.add(renderPanel, gridBagConstraints);

        drawInventoriesFromController();

        startRender();
    }

    // TODO: TMP add inventory from player controllers
    private void drawInventoriesFromController() {
        inventoryPanel = getController(0).getInventoryPanel();
        if (inventoryPanel != null && renderPanel != null) {
            java.awt.GridBagConstraints gridBagConstraints;

            inventoryPanel.setMinimumSize(new Dimension(320, 160));
            inventoryPanel.setPreferredSize(new Dimension(320, 160));

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;


            frame.add(inventoryPanel, gridBagConstraints);

            if (renderPanel != null) {
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;

                frame.remove(renderPanel);
                frame.add(renderPanel, gridBagConstraints);
            }
        }
    }


    @Override
    public void update(String message) {
        drawInventoriesFromController();
    }
}
