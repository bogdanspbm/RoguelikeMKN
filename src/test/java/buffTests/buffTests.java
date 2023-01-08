package buffTests;

import enemies.Enemy;
import objects.buff.Buff;
import objects.buff.factory.BuffBuilder;
import objects.pawn.Pawn;
import objects.projectile.Projectile;
import objects.projectile.ProjectileAttached;
import objects.projectile.ProjectileMovable;
import org.junit.Test;
import structures.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class buffTests {


    private Projectile createProjectileWithBuff(String name) {
        List<BuffBuilder> builderList = new ArrayList<>();
        BuffBuilder stanBuilder = new BuffBuilder();
        stanBuilder.setName(name);
        builderList.add(stanBuilder);
        Projectile projectile = new ProjectileMovable(null, builderList);
        projectile.setLocation(new Vector3D(0, 0, 0));
        return projectile;
    }

    @Test
    public void testStunBuff() {
        Projectile projectile = createProjectileWithBuff("bash");
        Pawn enemy = new Enemy();

        assert enemy.getParamsComponent().getSpeed() != 0;
        enemy.applyDamage(30, projectile);

        assert enemy.getParamsComponent().getBuffList().size() == 1;
        assert enemy.getParamsComponent().getBuffList().peek().getName().equals("bash");
        assert enemy.getParamsComponent().getSpeed() == 0;
    }

    @Test
    public void testStunBuffMovement() {
        Projectile projectile = createProjectileWithBuff("bash");
        Pawn enemy = new Enemy();

        enemy.applyDamage(30, projectile);

        Vector3D startLocation = enemy.getLocation().copy();



    }

    @Test
    public void testBuffExpire() {
        Projectile projectile = createProjectileWithBuff("bash");
        Pawn enemy = new Enemy();

        enemy.applyDamage(30, projectile);

        assert enemy.getParamsComponent().getBuffList().size() == 1;

        Buff buff = enemy.getParamsComponent().getBuffList().peek();
        int duration = buff.getDuration();

        for (int i = 1; i < duration; i++) {
            enemy.tick();
        }

        assert enemy.getParamsComponent().getBuffList().size() == 1;

        enemy.tick();

        assert enemy.getParamsComponent().getBuffList().size() == 0;
    }
}
