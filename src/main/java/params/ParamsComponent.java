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
}
