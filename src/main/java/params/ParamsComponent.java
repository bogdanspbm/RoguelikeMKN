package params;

public class ParamsComponent {

    public ParamsComponent() {

    }

    int curHealth = 100;
    int maxHealth = 100;

    int level = 1;

    int experience = 0;

    public void addExperience(int add) {
        experience += add;
    }

    public float getHealthPercentage() {
        return (float) curHealth / (float) maxHealth;
    }

    public void addHealth(int add) {
        curHealth += add;
        System.out.println(curHealth);
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
