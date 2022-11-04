package interfaces;

public interface Physical {
    int fallSpeed = 1;
    int jumpSpeed =4;
    int jumpTime = 200;

    public void tryFall();

    public boolean isInAir();
}


