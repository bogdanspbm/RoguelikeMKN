package paramsTests;


import enemies.Enemy;
import enums.EBotType;
import enums.EPawnStatus;
import objects.pawn.Pawn;
import org.junit.Assert;
import org.junit.Test;
import params.ParamsComponent;
import params.interfaces.Params;
import structures.Vector3D;

public class paramsTests {

    @Test
    public void testSimpleCheckLevel() {
        ParamsComponent paramsComponent = new ParamsComponent();
        Assert.assertEquals(paramsComponent.getLevel(), 1);
    }

    @Test
    public void testCheckLevelUp() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.addExperience(500);
        Assert.assertEquals(paramsComponent.getLevel() , 2);
    }

    @Test
    public void testSettingHp() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.setMaxHealth(100);
        Assert.assertEquals(paramsComponent.maxHealth , 100);
        Assert.assertEquals(paramsComponent.curHealth , 100);
    }

    @Test
    public void testSimpleAddingFreePoints() {
        ParamsComponent paramsComponent = new ParamsComponent();
        Assert.assertEquals(paramsComponent.getFreePoints() , 0);
    }

    @Test
    public void testSetSpeed() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.setSpeed(70);
        Assert.assertEquals(paramsComponent.getSpeed() , 70);
    }

    @Test
    public void testCheckingIncreseParams() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.getDamageScale();
        paramsComponent.increaseParam("strength");
        Assert.assertEquals(paramsComponent.maxHealth , 150);
    }

    @Test
    public void testAddExperinece() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.addExperience(100);
        Assert.assertEquals(paramsComponent.getExperience() , 200);
    }

    @Test
    public void testCheckDead() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.curHealth = 0;
        Assert.assertEquals(paramsComponent.checkIsDead() , true);
    }

    @Test
    public void testCheckNotDead() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.curHealth = 100;
        Assert.assertEquals(paramsComponent.checkIsDead() , false);
    }

    @Test
    public void testTakeAwayHealth() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.curHealth = 100;
        paramsComponent.takeAwayHealth();
        Assert.assertEquals(paramsComponent.curHealth , 90);
    }

    @Test
    public void testCheckIsDeadAfterTakeAwayHealth() {
        ParamsComponent paramsComponent = new ParamsComponent();
        paramsComponent.curHealth = 10;
        paramsComponent.takeAwayHealth();
        Assert.assertEquals(paramsComponent.checkIsDead() , true);
    }

}
