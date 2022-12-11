package params;

import objects.buff.Buff;

import java.util.ArrayList;
import java.util.List;

public class ParamsComponent {

    public ParamsComponent() {

    }

    List<Buff> buffs = new ArrayList<>();
    int speed = 1;

    int curHealth = 100;
    int maxHealth = 100;

    int level = 1;

    int experience = 0;

    public void addBuff(Buff buff){
        buffs.add(buff);
    }
    public List<Buff> getBuffList(){
        return buffs;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getSpeed(){
        return speed;
    }

    public void addExperience(int add) {
        experience += add;
    }

    public float getHealthPercentage() {
        return (float) curHealth / (float) maxHealth;
    }

    public void takeAwayHealth(){
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
}
