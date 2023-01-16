package objects.animations.objects;

import config.Config;
import structures.Vector2D;
import structures.Vector2DTimeline;

import java.awt.image.BufferedImage;

public class Animation {

    TextureSource source;
    Vector2DTimeline timeline;

    boolean loop = true;

    int timePerFrame = 1;
    int currentTime = 0;
    int currentFrame = 0;

    public Animation increment() {
        currentTime += (int) (1000 / Config.FRAME_RATE);
        currentFrame = currentTime / timePerFrame;
        return this;
    }

    public Animation(TextureSource source, Vector2DTimeline timeline, int timePerFrame, boolean loop) {
        this.loop = loop;
        this.source = source;
        this.timeline = timeline;
        this.timePerFrame = timePerFrame;
    }

    public BufferedImage getFrame() {
        int curFrameIndex = currentFrame % timeline.getTimeline().size();
        if (!loop && currentFrame >= timeline.getTimeline().size()) {
            curFrameIndex = timeline.getTimeline().size() - 1;
        }

        Vector2D frameIndex = timeline.getTimeline().get(curFrameIndex);
        return source.getFrame(frameIndex.x(), frameIndex.y());
    }
}
