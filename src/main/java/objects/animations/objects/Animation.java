package objects.animations.objects;

import config.Config;
import structures.Vector2D;
import structures.Vector2DTimeline;

import java.awt.image.BufferedImage;

public class Animation {

    AnimationSource source;
    Vector2DTimeline timeline;

    int timePerFrame = 1;
    int currentTime = 0;
    int currentFrame = 0;

    public Animation increment() {
        currentTime += (int) (1000 / Config.FRAME_RATE);
        currentFrame = currentTime / timePerFrame;
        return this;
    }

    public Animation(AnimationSource source, Vector2DTimeline timeline, int timePerFrame) {
        this.source = source;
        this.timeline = timeline;
        this.timePerFrame = timePerFrame;
    }

    public BufferedImage getFrame() {
        Vector2D frameIndex = timeline.getTimeline().get(currentFrame % timeline.getTimeline().size());
        return source.getFrame(frameIndex.x(), frameIndex.y());
    }
}
