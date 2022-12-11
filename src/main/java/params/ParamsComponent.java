package params;

import database.adapter.implementation.ParamsDatabaseAdapter;
import objects.buff.Buff;

import java.util.ArrayList;
import java.util.List;

public class ParamsComponent {


    private ParamsDatabaseAdapter paramsDatabaseAdapter;

    public ParamsComponent() {
        try {
            paramsDatabaseAdapter = new ParamsDatabaseAdapter();
            levels = paramsDatabaseAdapter.getLevelsExperience();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<Buff> buffs = new ArrayList<>();

    List<Integer> levels;
    int speed = 1;

    int curHealth = 100;
    int maxHealth = 100;

    int level = 1;

    int experience = 0;

    public void addBuff(Buff buff) {
        buffs.add(buff);
    }

    public List<Buff> getBuffList() {
        return buffs;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void addExperience(int add) {
        this.experience += add;
    }

    public float getHealthPercentage() {
        return (float) curHealth / (float) maxHealth;
    }

    public void takeAwayHealth() {
        curHealth = curHealth - 10;
    }

    public void addHealth(int add) {
        curHealth += add;
        floorHealth();
    }

    public void floorHealth() {
        curHealth = Math.min(maxHealth, curHealth);
    }

    public boolean checkIsDead() {
        if (curHealth <= 0) {
            return true;
        }

        return false;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {

        int cntExperience = this.experience;
        int i = 0;

        while (cntExperience > levels.get(i)) {
            cntExperience -= levels.get(i);
            i++;
        }

        return i + 1;
    }

    public double getLevelPercentage() {

        int cntExperience = this.experience;
        int i = 0;

        while (cntExperience > levels.get(i)) {
            cntExperience -= levels.get(i);
            i++;
        }

        double res = (double) cntExperience / (double) levels.get(i);

        return res;
    }
}
