package enemies.animations;

import objects.animations.component.AnimationComponent;
import objects.animations.objects.Animation;
import objects.pawn.Pawn;

import java.awt.*;
import java.util.HashMap;

public class SlimeAnimationComponent extends AnimationComponent {

    Pawn owner;
    public SlimeAnimationComponent(Pawn owner, HashMap<String, Animation> animations) {
        super(animations);
        this.owner = owner;
    }

    @Override
    public Image getImage() {
        return animations.get("slime_idle").increment().getFrame();
    }
}
