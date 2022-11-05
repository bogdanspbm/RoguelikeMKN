package player.animation.factory;

import exceptions.CreationException;
import objects.animations.component.AnimationComponent;
import objects.animations.factory.AnimationComponentFactory;
import objects.animations.objects.Animation;
import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;
import structures.AnimationStructure;
import structures.Vector2DTimeline;

import java.awt.*;
import java.util.HashMap;

public class PlayerAnimationComponentFactory extends AnimationComponentFactory {
    @Override
    public AnimationComponent createAnimationComponent(Pawn owner) throws CreationException {
        HashMap<String, Animation> animations;
        try {
            animations = generateAnimationsFromSources();
        } catch (Exception e) {
            throw new CreationException("Can't create PlayerAnimationComponent: \n" + e.toString());
        }

        return new PlayerAnimationComponent(owner,animations);
    }

    protected void initAnimations() {
        animations = new HashMap<>();

        // TODO: Need to be saved in a database
        animations.put("idle", new AnimationStructure("idle", "src/main/resources/animations/player/Character.png", new Dimension(48, 48), new Vector2DTimeline(1, 0, 1, 0),150));
        animations.put("walk_down", new AnimationStructure("walk_down", "src/main/resources/animations/player/Character.png", new Dimension(48, 48), new Vector2DTimeline(0, 0, 2, 0),150));
        animations.put("walk_up", new AnimationStructure("walk_up", "src/main/resources/animations/player/Character.png", new Dimension(48, 48), new Vector2DTimeline(0, 3, 2, 3),150));
        animations.put("walk_left", new AnimationStructure("walk_left", "src/main/resources/animations/player/Character.png", new Dimension(48, 48), new Vector2DTimeline(0, 1, 2, 1),150));
        animations.put("walk_right", new AnimationStructure("walk_right", "src/main/resources/animations/player/Character.png", new Dimension(48, 48), new Vector2DTimeline(0, 2, 2, 2),150));

        // TODO: Needs a preview for animation view
    }
}
