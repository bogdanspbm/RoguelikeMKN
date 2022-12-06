package objects.animations.component;

import objects.animations.objects.Animation;

import java.awt.*;
import java.util.HashMap;

public abstract class AnimationComponent {

    public HashMap<String, Animation> animations = new HashMap<>();

    public AnimationComponent(HashMap<String, Animation> animations) {
        this.animations = animations;
    }

    public abstract Image getImage();
}
