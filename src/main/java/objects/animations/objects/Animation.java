package objects.animations.objects;

import structures.Vector2D;
import structures.Vector2DTimeline;

import java.awt.image.BufferedImage;

public class Animation {

    AnimationSource source;
    Vector2DTimeline timeline;

    int currentFrame = 0;

    public void increment() {
        currentFrame++;
    }

    public Animation(AnimationSource source, Vector2DTimeline timeline) {
        this.source = source;
        this.timeline = timeline;
    }

    public BufferedImage getFrame() {
        Vector2D frameIndex = timeline.getTimeline().get(currentFrame % timeline.getTimeline().size());
        return source.getFrame(frameIndex.x(), frameIndex.y());
    }
}
