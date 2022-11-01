package objects.animations.factory;

import objects.animations.component.AnimationComponent;
import objects.animations.objects.Animation;
import objects.animations.objects.AnimationSource;

import java.util.HashMap;

public abstract class AnimationComponentFactory {
    private HashMap<String, AnimationSource> animationSources;


    protected HashMap<String, Animation> generateAnimationsFromSources() {
        HashMap<String, Animation> result = new HashMap<>();
        animationSources.forEach((s, animationSource) -> result.put(s, new Animation(animationSource)));
        return result;
    }

    abstract public AnimationComponent createAnimationComponent();
}
