package params;

public class ParamsComponent {

    public ParamsComponent() {

    }

    int health = 100;

    int level = 1;

    int experience = 0;

    public void addExperience(int add) {
        experience += add;
    }
}
