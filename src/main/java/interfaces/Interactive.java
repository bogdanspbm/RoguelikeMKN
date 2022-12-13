package interfaces;

import objects.pawn.Pawn;

public interface Interactive {

    public void interact(Pawn instigator);

    public void startOverlap(Pawn instigator);

    public void stopOverlap(Pawn instigator);
}
