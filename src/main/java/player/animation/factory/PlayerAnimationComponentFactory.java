package player.animation.factory;

import exceptions.CreationException;
import objects.animations.component.AnimationComponent;
import objects.animations.factory.AnimationComponentFactory;
import objects.animations.objects.Animation;
import player.animation.component.PlayerAnimationComponent;
import structures.AnimationStructure;
import structures.Vector2DTimeline;

import java.awt.*;
import java.util.HashMap;

public class PlayerAnimationComponentFactory extends AnimationComponentFactory {
    @Override
    public AnimationComponent createAnimationComponent() throws CreationException {
        HashMap<String, Animation> animations;
        try {
            animations = generateAnimationsFromSources();
        } catch (Exception e) {
            throw new CreationException("Can't create PlayerAnimationComponent: \n" + e.toString());
        }

        return new PlayerAnimationComponent(animations);
    }

    protected void initAnimations() {
        animations = new HashMap<>();

        // TODO: Need to be saved in a database
        animations.put("idle", new AnimationStructure("idle", "src/main/resources/animations/player/Character.png", new Dimension(48, 48), new Vector2DTimeline(0, 0, 2, 0)));

        // TODO: Needs a preview for animation view
    }
}
