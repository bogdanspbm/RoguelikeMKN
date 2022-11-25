package objects.animations.component.implementation;

import objects.animations.component.AnimationComponent;
import objects.animations.objects.Animation;
import objects.pawn.Pawn;

import java.awt.*;
import java.util.HashMap;

public class SolidAnimationComponent extends AnimationComponent {

    String name;

    public SolidAnimationComponent(HashMap<String, Animation> animations, String name) {
        super(animations);
        this.name = name;
    }

    @Override
    public Image getImage() {
        return animations.get(name).increment().getFrame();
    }
}
