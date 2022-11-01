package structures;

import java.awt.*;

public record AnimationStructure(String name, String source, Dimension frameSize, Vector2DTimeline timeline) {
}
