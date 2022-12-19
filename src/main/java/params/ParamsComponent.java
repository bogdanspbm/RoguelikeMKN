package params;

import database.adapter.implementation.ParamsDatabaseAdapter;
import objects.buff.Buff;
import params.ui.ParamBar;

import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class ParamsComponent {


    private ParamsDatabaseAdapter paramsDatabaseAdapter;
    private Map<String, Integer> paramsMap = new HashMap<>();

    private Queue<Buff> buffs = new SynchronousQueue<>();

    private List<Integer> levels;
    private int speed = 1;

    private int curHealth = 100;
    private int maxHealth = 100;

    private int experience = 0;

    public ParamsComponent() {
        try {
            paramsDatabaseAdapter = new ParamsDatabaseAdapter();
            levels = paramsDatabaseAdapter.getLevelsExperience();
            generateParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getParamsMap() {
        return paramsMap;
    }

    public int getFreePoints() {
        int amount = getLevel() - 1 + paramsMap.size();

        for (String key : paramsMap.keySet()) {
            amount -= paramsMap.get(key);
        }

        return amount;
    }

    public void increaseParam(String name) {
        paramsMap.put(name, paramsMap.get(name) + 1);
    }

    private void generateParams() {
        if (paramsDatabaseAdapter == null) {
            return;
        }

        try {
            for (String name : paramsDatabaseAdapter.getParamsList()) {
                paramsMap.put(name, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBuff(Buff buff) {
        buff.onAdd();
        buffs.add(buff);
    }

    public void removeBuff(Buff buff) {
        buff.onRemove();
        buffs.remove(buff);
    }

    public Queue<Buff> getBuffList() {
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

    public void tick() {
        for (Buff buff : buffs) {
            buff.tick();
        }
    }
}
