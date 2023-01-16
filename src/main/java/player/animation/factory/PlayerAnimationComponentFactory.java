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
    public AnimationComponent createAnimationComponent(Object owner) throws CreationException {
        HashMap<String, Animation> animations;
        try {
            animations = generateAnimationsFromSources();
            return new PlayerAnimationComponent((Pawn) owner, animations);
        } catch (Exception e) {
            throw new CreationException("Can't create PlayerAnimationComponent: \n" + e.toString());
        }
    }

    protected void initAnimations() {
        animations = new HashMap<>();

        try {
            animations.put("knigt_idle", animationDatabaseAdapter.getAnimationStructureByName("knigt_idle"));
            animations.put("knight_walk_down", animationDatabaseAdapter.getAnimationStructureByName("knight_walk_down"));
            animations.put("knight_walk_up", animationDatabaseAdapter.getAnimationStructureByName("knight_walk_up"));
            animations.put("knight_walk_left", animationDatabaseAdapter.getAnimationStructureByName("knight_walk_left"));
            animations.put("knight_walk_right", animationDatabaseAdapter.getAnimationStructureByName("knight_walk_right"));
            animations.put("knight_attack_down", animationDatabaseAdapter.getAnimationStructureByName("knight_attack_down"));
            animations.put("knight_attack_up", animationDatabaseAdapter.getAnimationStructureByName("knight_attack_up"));
            animations.put("knight_attack_left", animationDatabaseAdapter.getAnimationStructureByName("knight_attack_left"));
            animations.put("knight_attack_right", animationDatabaseAdapter.getAnimationStructureByName("knight_attack_right"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Needs a preview for animation view
    }
}
