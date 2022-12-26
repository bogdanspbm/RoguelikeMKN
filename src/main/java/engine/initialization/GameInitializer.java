package engine.initialization;

import enemies.Enemy;
import enemies.Slime;
import engine.render.interfaces.Drawable;
import engine.render.interfaces.DrawableProvider;
import engine.render.window.Window;
import enums.EBotType;
import exceptions.CreationException;
import interfaces.Placeable;
import player.Player;
import structures.Vector3D;

import java.util.List;

import static world.singleton.Processor.getWorld;

public class GameInitializer implements DrawableProvider {
    private Window window;


    public void start() {
        try {
            createPlayer();
            createItems();
            createEnemy(EBotType.PATROL);
            //createEnemy(EBotType.AGGRESSOR);//трусливый бот
            //createEnemy("aggressor");//агресивный бот
            //createEnemy("calm");//статичный бот
        } catch (Exception e) {
            e.printStackTrace();
        }
        createWindow();
    }

    public void createItems() {
        getWorld().createItem(1, 3).setLocation(new Vector3D(100, 50, 0));
        getWorld().createItem(2, 1).setLocation(new Vector3D(100, 150, 0));
    }

    private void createPlayer() throws CreationException {
        Player player = new Player();
        getWorld().addPawn(player);
        player.setLocation(getWorld().getMap().getRandomSpawnPosition());
    }

    private void createEnemy(EBotType behaviour) throws CreationException {
        Enemy enemy = new Enemy(behaviour);
        switch (behaviour) {
            case COWARD:
                enemy.setLocation(new Vector3D(400, 100, 100));
                break;
            case AGGRESSOR:
                enemy = new Slime();
                enemy.setLocation(new Vector3D(-400, 10, 150));
                break;
            case CALM:
                enemy.setLocation(new Vector3D(500, 200, 100));
                break;
            case PATROL:
                enemy.setLocation(new Vector3D(500, 200, 100));
                break;
        }

        getWorld().addPawn(enemy);
        //getWorld().addControllers(enemy.getController());

    }

    private void createWindow() {
        window = new Window();
        window.createRenderPanel(this);
        window.setVisible(true);
    }

    @Override
    public Placeable getCamera() {
        return getWorld().getPawns().get(0);
    }

    @Override
    public List<Drawable> getDrawable() {
        return getWorld().getDrawables();
    }
}
