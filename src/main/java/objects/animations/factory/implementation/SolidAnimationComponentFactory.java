package objects.animations.factory.implementation;

import database.adapter.implementation.AnimationDatabaseAdapter;
import exceptions.CreationException;
import objects.animations.component.AnimationComponent;
import objects.animations.component.implementation.SolidAnimationComponent;
import objects.animations.factory.AnimationComponentFactory;
import objects.animations.objects.Animation;
import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;

import java.util.HashMap;

public class SolidAnimationComponentFactory extends AnimationComponentFactory {

    String animName;

    public SolidAnimationComponentFactory(String animName) {
        this.animName = animName;

        try {
            animationDatabaseAdapter = new AnimationDatabaseAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initAnimations();
    }

    @Override
    public AnimationComponent createAnimationComponent(Object owner) throws CreationException {
        HashMap<String, Animation> animations;
        try {
            animations = generateAnimationsFromSources();
        } catch (Exception e) {
            throw new CreationException("Can't create SolidAnimationComponent: \n" + e.toString());
        }

        return new SolidAnimationComponent(animations, this.animName);
    }

    protected void initAnimations() {
        animations = new HashMap<>();

        try {
            if (animName != null) {
                animations.put(this.animName, animationDatabaseAdapter.getAnimationStructureByName(animName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
