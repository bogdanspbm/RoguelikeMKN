package player.animation.factory;

import objects.animations.component.AnimationComponent;
import objects.animations.factory.AnimationComponentFactory;
import player.animation.component.PlayerAnimationComponent;

public class PlayerAnimationComponentFactory extends AnimationComponentFactory {
    @Override
    public AnimationComponent createAnimationComponent() {
        return new PlayerAnimationComponent(generateAnimationsFromSources());
    }
}
