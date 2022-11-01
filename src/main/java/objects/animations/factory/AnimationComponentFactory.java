package objects.animations.factory;

import exceptions.CreationException;
import exceptions.SourceException;
import objects.animations.component.AnimationComponent;
import objects.animations.objects.Animation;
import objects.animations.objects.AnimationSource;
import structures.AnimationStructure;

import java.io.File;
import java.util.HashMap;

public abstract class AnimationComponentFactory {
    private HashMap<String, AnimationSource> animationSources = new HashMap<>();
    protected HashMap<String, AnimationStructure> animations;

    public AnimationComponentFactory() {
        initAnimations();
    }

    protected void initAnimations() {
        animations = new HashMap<>();
    }


    protected HashMap<String, Animation> generateAnimationsFromSources() throws SourceException {
        generateAnimationSources();
        HashMap<String, Animation> result = new HashMap<>();
        animations.forEach((s, animationStructure) -> result.put(s, new Animation(animationSources.get(animationStructure.source()), animationStructure.timeline())));
        return result;
    }

    private void generateAnimationSources() throws SourceException {
        if (animationSources != null && animationSources.size() != 0) {
            return;
        }

        for (String key : animations.keySet()) {
            AnimationStructure animationStructure = animations.get(key);
            if (!animationSources.containsKey(animationStructure.source())) {
                try {
                    AnimationSource source = new AnimationSource(new File(animationStructure.source()), animationStructure.frameSize());
                    animationSources.put(animationStructure.source(), source);
                } catch (Exception e) {
                    throw new SourceException("Can't generate source files: \n" + e.toString());
                }
            }
        }
    }

    abstract public AnimationComponent createAnimationComponent() throws CreationException;
}
