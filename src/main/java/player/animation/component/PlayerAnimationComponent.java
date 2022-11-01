package player.animation.component;

import objects.animations.component.AnimationComponent;
import objects.animations.objects.Animation;

import java.awt.*;
import java.util.HashMap;

public class PlayerAnimationComponent extends AnimationComponent {

    public PlayerAnimationComponent(HashMap<String, Animation> animations) {
        super(animations);
    }

    @Override
    public Image getImage() {
        return animations.get("idle").getFrame();
    }
}
