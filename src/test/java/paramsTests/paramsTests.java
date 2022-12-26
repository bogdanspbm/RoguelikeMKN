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
    public void checkLevelApp() {
        ParamsComponent paramsComponent = new ParamsComponent();
        Assert.assertEquals(paramsComponent.getLevel(), 1);
        paramsComponent.addExperience(500);
        Assert.assertEquals(paramsComponent.getLevel() , 2);
    }


}
