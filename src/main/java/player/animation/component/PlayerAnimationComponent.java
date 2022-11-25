package player.animation.component;

import enums.EPawnStatus;
import objects.animations.component.AnimationComponent;
import objects.animations.objects.Animation;
import objects.pawn.Pawn;
import structures.Vector3D;

import java.awt.*;
import java.util.HashMap;

public class PlayerAnimationComponent extends AnimationComponent {

    Pawn owner;

    public PlayerAnimationComponent(Pawn owner, HashMap<String, Animation> animations) {
        super(animations);
        this.owner = owner;
    }

    @Override
    public Image getImage() {
        Vector3D velocity = owner.getVelocity();

        if (owner.getStatus() == EPawnStatus.WALK) {
            if (velocity.x() < -0.5) {
                return animations.get("knight_walk_left").increment().getFrame();
            }
            if (velocity.x() > 0.5) {
                return animations.get("knight_walk_right").increment().getFrame();
            }
            if (velocity.y() < -0.5) {
                return animations.get("knight_walk_up").increment().getFrame();
            }
            if (velocity.y() > 0.5) {
                return animations.get("knight_walk_down").increment().getFrame();
            }
        } else {
            if (velocity.x() < -0.5) {
                return animations.get("knight_attack_left").increment().getFrame();
            }
            if (velocity.x() > 0.5) {
                return animations.get("knight_attack_right").increment().getFrame();
            }
            if (velocity.y() < -0.5) {
                return animations.get("knight_attack_up").increment().getFrame();
            }
            if (velocity.y() > 0.5) {
                return animations.get("knight_attack_down").increment().getFrame();
            }

            return animations.get("knight_attack_down").increment().getFrame();
        }

        return animations.get("knigt_idle").increment().getFrame();
    }
}
