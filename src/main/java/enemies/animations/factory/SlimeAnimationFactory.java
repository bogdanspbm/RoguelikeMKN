package enemies.animations.factory;

import enemies.animations.SlimeAnimationComponent;
import exceptions.CreationException;
import objects.animations.component.AnimationComponent;
import objects.animations.factory.AnimationComponentFactory;
import objects.animations.objects.Animation;
import objects.pawn.Pawn;
import player.animation.component.PlayerAnimationComponent;

import java.util.HashMap;

public class SlimeAnimationFactory extends AnimationComponentFactory {
    @Override
    public AnimationComponent createAnimationComponent(Object owner) throws CreationException {
        HashMap<String, Animation> animations;
        try {
            animations = generateAnimationsFromSources();
            return new SlimeAnimationComponent((Pawn) owner, animations);
        } catch (Exception e) {
            throw new CreationException("Can't create PlayerAnimationComponent: \n" + e.toString());
        }
    }

    protected void initAnimations() {
        animations = new HashMap<>();

        try {
            animations.put("slime_idle", animationDatabaseAdapter.getAnimationStructureByName("slime_idle"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Needs a preview for animation view
    }
}
